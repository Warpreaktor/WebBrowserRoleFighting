const HOST = "http://localhost:4568";

document.addEventListener("DOMContentLoaded", () => {

    loadInventory(); // Загружаем инвентарь при старте

    // Загружаем статистику персонажа при загрузке страницы
    fetch(`${HOST}/getPlayer/statistic/player1`)
        .then(response => response.json())
        .then(data => updateCharacterStats(data))
        .catch(error => console.error("Ошибка загрузки данных персонажа:", error));
});

//Загрузка инвентаря
function loadInventory() {
    fetch(`${HOST}/inventory`)
        .then(response => response.json())
        .then(data => {
            console.log("Инвентарь получен:", data);
            renderInventory(data.cells); // Отправляем `cells`, а не весь объект
        })
        .catch(error => console.error("Ошибка загрузки инвентаря:", error));
}

//Отрисовка инвентаря и добавление обработчика drop
function renderInventory(cells) {
    const inventoryGrid = document.querySelector(".inventory-grid");
    inventoryGrid.innerHTML = ""; // Очищаем перед загрузкой

    cells.forEach((item, index) => {
        const inventorySlot = document.createElement("div");
        inventorySlot.classList.add("inventory-slot");
        inventorySlot.dataset.index = index; // Привязываем индекс слота

        if (item !== null) { // Если в ячейке есть предмет
            const itemElement = document.createElement("img");
            itemElement.src = item.picture;
            itemElement.alt = item.name;
            itemElement.classList.add("item");
            itemElement.setAttribute("draggable", "true");
            itemElement.dataset.id = item.id;

            // Добавляем `dragstart`
            itemElement.addEventListener("dragstart", event => {
                dragStart(event, item);
            });

            inventorySlot.appendChild(itemElement);
        }

        inventoryGrid.appendChild(inventorySlot);
    });

    console.log("Инвентарь отрисован:", inventoryGrid.innerHTML);

        document.querySelectorAll(".inventory-slot, .slot").forEach(slot => {
            slot.addEventListener("dragover", dragOver);
            slot.addEventListener("drop", drop);
        });
    }

function dragOver(event) {
    event.preventDefault(); // Разрешаем сброс предмета в ячейку
}

// Функция обновления характеристик персонажа
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

// Функция схватить предмет
function dragStart(event, itemData) {
    event.dataTransfer.setData("application/json", JSON.stringify(itemData));

    const objectId = itemData.id;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");
    const oldSlot = previousSlot ?
        (previousSlot.dataset.slot || `INVENTORY_${previousSlot.dataset.index}`)
        : null;

    console.log("Предмет схвачен:", itemData);
    console.log(`Отправка запроса /hero/takeItem: itemId ${objectId}, oldSlot ${oldSlot}`);

    takeItem("player1", objectId, oldSlot);
}

// Обработка при отпускании предмета
function drop(event) {
    event.preventDefault();

    const itemData = JSON.parse(event.dataTransfer.getData("application/json"));
    const objectId = itemData.id;
    const targetSlot = event.target;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");

    let oldSlot = previousSlot ?
        (previousSlot.dataset.slot || `INVENTORY_${previousSlot.dataset.index}`)
        : null;
    let newSlot = null;

    if (targetSlot.classList.contains("inventory-slot") && targetSlot.dataset.index) {
        newSlot = `INVENTORY_${targetSlot.dataset.index}`;
    } else if (targetSlot.classList.contains("slot") && targetSlot.dataset.slot) {
        newSlot = targetSlot.dataset.slot;
    }

    if (!oldSlot || !newSlot) {
        console.error("Ошибка: oldSlot или newSlot не определён!");
        returnItemToPreviousSlot(event);
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
        console.log("Ответ сервера (dropItem):", data);
        targetSlot.appendChild(document.querySelector(`[data-id="${objectId}"]`));
    })
    .catch(error => {
        console.error("Ошибка при перемещении предмета:", error);
        returnItemToPreviousSlot(event);
    });
}

// Отправка экипировки предмета
function equipped(playerId, item, newSlot, oldSlot) {
    console.log(`Отправка запроса на экипировку: ${item.id} в ${newSlot}`);

    fetch(`${HOST}/hero/equipped`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerId: playerId,
            objectId: item.id,
            oldSlot: oldSlot,
            newSlot: newSlot
        })
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 400) {
                throw new Error("Этот предмет нельзя экипировать сюда!");
            }
            throw new Error("Ошибка при экипировке предмета!");
        }
        return response.json();
    })
    .then(data => console.log("Предмет экипирован:", data))
    .catch(error => {
        console.error("Ошибка при экипировке:", error);
        returnItemToPreviousSlot(item.id, oldSlot);
    });
}

// Отправка снятия предмета
function unequipped(objectId, slot) {
    console.log(`Снятие предмета: ${objectId} из слота ${slot}`);

    fetch(`${HOST}/hero/unequipped`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerId: "player1",
            objectId: objectId,
            slot: slot
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Ошибка при снятии предмета!");
        }
        return response.json();
    })
    .then(data => {
        console.log("Предмет успешно снят:", data);
        loadInventory(); // Перезагружаем инвентарь после снятия
    })
    .catch(error => console.error("Ошибка при снятии предмета:", error));
    }

function moveItemInInventory(playerId, item, newSlot, oldSlot) {
    console.log(`Отправка запроса на перемещение: ${item.id} из ${oldSlot} в ${newSlot}`);

    fetch(`${HOST}/hero/inventory/move`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerId: playerId,
            objectId: item.id,
            oldSlot: oldSlot,
            newSlot: newSlot
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Ошибка при перемещении предмета в инвентарь!");
        }
        return response.json();
    })
    .then(data => console.log("Предмет успешно перемещен:", data))
    .catch(error => {
        console.error("Ошибка при перемещении предмета:", error);
        returnItemToPreviousSlot(item.id, oldSlot); // Вернуть предмет назад при ошибке
    });
}

function returnItemToPreviousSlot(event) {
    const itemData = JSON.parse(event.dataTransfer.getData("application/json"));
    const objectId = itemData.id;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");

    if (previousSlot) {
        previousSlot.appendChild(document.querySelector(`[data-id="${objectId}"]`));
    } else {
        console.error("Ошибка: невозможно вернуть предмет в предыдущее место!");
    }
}

function takeItem(playerId, objectId, oldSlot) {
    if (!oldSlot) {
        console.error("Ошибка: oldSlot не определён!");
        return;
    }

    console.log(`Отправка запроса /hero/takeItem: itemId ${objectId}, oldSlot ${oldSlot}`);

    fetch(`${HOST}/hero/takeItem/`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            playerId: playerId,
            objectId: objectId,
            oldSlot: oldSlot
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Ошибка при захвате предмета!");
        }
        return response.json();
    })
    .then(data => console.log("Ответ сервера (takeItem):", data))
    .catch(error => {
        console.error("Ошибка запроса takeItem:", error);
    });
}

function drop(event) {
    event.preventDefault();

    const itemData = JSON.parse(event.dataTransfer.getData("application/json"));
    const objectId = itemData.id;
    const targetSlot = event.target;
    const previousSlot = document.querySelector(`[data-id="${objectId}"]`)?.closest(".slot, .inventory-slot");

    // Определяем `oldSlot`
    let oldSlot = previousSlot ? previousSlot.dataset.slot || `INVENTORY_${previousSlot.dataset.index}` : null;
    let newSlot = null;

    // Определяем `newSlot`
    if (targetSlot.classList.contains("inventory-slot") && targetSlot.dataset.index) {
        newSlot = `INVENTORY_${targetSlot.dataset.index}`;
    } else if (targetSlot.classList.contains("slot") && targetSlot.dataset.slot) {
        newSlot = targetSlot.dataset.slot;
    }

    if (!oldSlot || !newSlot) {
        console.error("Ошибка: oldSlot или newSlot не определён!");
        returnItemToPreviousSlot(event);
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
        console.log("Ответ сервера (dropItem):", data);

        // Если сервер подтвердил перемещение, визуально переносим предмет
        targetSlot.appendChild(document.querySelector(`[data-id="${objectId}"]`));
    })
    .catch(error => {
        console.error("Ошибка при перемещении предмета:", error);
        returnItemToPreviousSlot(event);
    });
}


