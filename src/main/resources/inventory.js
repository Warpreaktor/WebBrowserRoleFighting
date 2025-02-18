const HOST = "http://localhost:4568";

document.addEventListener("DOMContentLoaded", () => {

    loadInventory();

    document.querySelectorAll(".inventory-slot, .slot").forEach(slot => {
        slot.addEventListener("dragover", dragOver);
        slot.addEventListener("drop", drop);
    });

    fetch(`${HOST}/getPlayer/statistic/player1`)
        .then(response => response.json())
        .then(data => updateCharacterStats(data))
        .catch(error => console.error("Ошибка загрузки данных персонажа:", error));
});

// Загрузка инвентаря
function loadInventory() {
    fetch(`${HOST}/inventory`)
        .then(response => response.json())
        .then(data => {
            console.log("📦 Инвентарь и экипировка обновлены:", data);
            renderInventory(data.inventory.cells);
            renderEquipment(data.equipment);
            updateCharacterStatsFromServer(); // 🔄 Обновляем статистику
        })
        .catch(error => console.error("❌ Ошибка загрузки инвентаря:", error));
}


// Отрисовка инвентаря
function renderInventory(cells) {
    const inventoryGrid = document.querySelector(".inventory-grid");
    inventoryGrid.innerHTML = "";
    console.log("🔄 Обновление инвентаря...");

    cells.forEach((item, index) => {
        const inventorySlot = document.createElement("div");
        inventorySlot.classList.add("inventory-slot");
        inventorySlot.dataset.index = index;

        inventorySlot.addEventListener("dragover", dragOver);
        inventorySlot.addEventListener("drop", drop);

        if (item !== null) {
            const itemElement = document.createElement("img");
            itemElement.src = item.picture;
            itemElement.alt = item.name;
            itemElement.classList.add("item");
            itemElement.setAttribute("draggable", "true");
            itemElement.dataset.id = item.id;

            itemElement.addEventListener("dragstart", event => {
                dragStart(event, item);
            });

            inventorySlot.appendChild(itemElement);
        }

        inventoryGrid.appendChild(inventorySlot);
    });
}

// Отрисовка экипировки
function renderEquipment(equipment) {
    document.querySelector(".right-hand").innerHTML = "";
    if (equipment.rightHand) {
        const weapon = document.createElement("img");
        weapon.src = equipment.rightHand.picture;
        weapon.alt = equipment.rightHand.name;
        weapon.classList.add("item");
        weapon.setAttribute("draggable", "true");
        weapon.dataset.id = equipment.rightHand.id;

        weapon.addEventListener("dragstart", event => {
            dragStart(event, equipment.rightHand);
        });

        document.querySelector(".right-hand").appendChild(weapon);
    }
}

function updateCharacterStatsFromServer() {
    fetch(`${HOST}/getPlayer/statistic/player1`)
        .then(response => response.json())
        .then(data => {
            console.log("📊 Обновление статистики:", data);
            updateCharacterStats(data);
        })
        .catch(error => console.error("❌ Ошибка обновления статистики персонажа:", error));
}

function updateCharacterStats(data) {
    document.getElementById("char-name").textContent = data.name;
    document.getElementById("char-class").textContent = data.heroClass;
    document.getElementById("char-hp").textContent = Math.floor(data.health);
    document.getElementById("char-max-hp").textContent = Math.floor(data.maxHealth);
    document.getElementById("char-mage-shield").textContent = Math.floor(data.shield);
    document.getElementById("char-max-mage-shield").textContent = Math.floor(data.maxShield);
    document.getElementById("char-accuracy").textContent = data.accuracy.toFixed(1);
    document.getElementById("char-agility").textContent = data.agility.toFixed(1);
    document.getElementById("char-evasion").textContent = data.evasion.toFixed(1);
    document.getElementById("char-fullDamage").textContent = Math.floor(data.damage.fullDamage);
    document.getElementById("char-physicalDamage").textContent = Math.floor(data.damage.physicalDamage);
    document.getElementById("char-fireDamage").textContent = Math.floor(data.damage.fireDamage);
}

// Разрешаем перетаскивание
function dragOver(event) {
    event.preventDefault();
}


// Сохранение данных при начале перетаскивания
function dragStart(event, itemData) {
    if (!itemData || !itemData.id) {
        console.error("Ошибка: itemData пуст или не содержит ID!");
        return;
    }
    event.dataTransfer.setData("application/json", JSON.stringify(itemData));
    console.log("Предмет схвачен:", itemData);
}

// Отпускание предмета
function drop(event) {
    event.preventDefault();
    console.log("🟢 Событие drop сработало!");

    const jsonData = event.dataTransfer.getData("application/json");

    if (!jsonData || jsonData.trim() === "") {
        console.error("Ошибка: event.dataTransfer пуст или не содержит JSON.");
        return;
    }

    let itemData;
    try {
        itemData = JSON.parse(jsonData);
    } catch (error) {
        console.error("Ошибка парсинга JSON:", error);
        return;
    }

    const objectId = itemData.id;
    const targetSlot = event.target;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");

    let oldSlot = previousSlot ?
        (previousSlot.dataset.slot || `INVENTORY_${previousSlot.dataset.index}`)
        : null;
    let newSlot = null;

    // Проверяем, является ли целевой слот ячейкой инвентаря
    if (targetSlot.classList.contains("inventory-slot") && targetSlot.dataset.index) {
        newSlot = `INVENTORY_${targetSlot.dataset.index}`;
    }
    // Проверяем, является ли целевой слот экипировочным слотом
    else if (targetSlot.classList.contains("slot") && targetSlot.dataset.slot) {
        newSlot = targetSlot.dataset.slot;
    }
    // Проверяем, если предмет попадает на вложенные элементы (например, картинка в `right-hand`)
    else if (targetSlot.parentElement) {
        if (targetSlot.parentElement.classList.contains("slot") && targetSlot.parentElement.dataset.slot) {
            newSlot = targetSlot.parentElement.dataset.slot;
        } else if (targetSlot.parentElement.classList.contains("inventory-slot") && targetSlot.parentElement.dataset.index) {
            newSlot = `INVENTORY_${targetSlot.parentElement.dataset.index}`;
        }
    }

    // Проверяем, корректно ли определён новый слот
    if (!newSlot) {
        console.error(`Ошибка: Не удалось определить новый слот! oldSlot: ${oldSlot}, newSlot: ${newSlot}`);
        returnItemToPreviousSlot(event);
        return;
    }

    if (oldSlot === newSlot) {
        console.log(`Предмет остался в том же месте: ${newSlot}, запрос не нужен.`);
        return;
    }

    console.log(`Отправка запроса /hero/dropItem: oldSlot ${oldSlot}, newSlot ${newSlot}`);

    fetch(`${HOST}/hero/dropItem/`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerId: "player1",
            objectId: objectId,
            oldSlot: oldSlot,
            newSlot: newSlot
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Ошибка при перемещении предмета!");
        }
        return response.json();
    })
    .then(data => {
        console.log("✅ Ответ сервера (dropItem):", data);
        loadInventory();
    })
    .catch(error => {
        console.error("Ошибка при перемещении предмета:", error);
        returnItemToPreviousSlot(event);
        loadInventory();
    });
}

// Возвращение предмета на предыдущее место при ошибке
function returnItemToPreviousSlot(event) {
    const jsonData = event.dataTransfer.getData("application/json");

    if (!jsonData || jsonData.trim() === "") {
        console.error("Ошибка: event.dataTransfer пуст, невозможно вернуть предмет.");
        return;
    }

    let itemData;
    try {
        itemData = JSON.parse(jsonData);
    } catch (error) {
        console.error("Ошибка парсинга JSON при возврате предмета:", error);
        return;
    }

    const objectId = itemData.id;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");

    if (previousSlot) {
        previousSlot.appendChild(document.querySelector(`[data-id="${objectId}"]`));
    } else {
        console.error("Ошибка: невозможно вернуть предмет в предыдущее место!");
    }
}
