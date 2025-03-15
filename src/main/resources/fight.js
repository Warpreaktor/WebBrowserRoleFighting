let player1 = null;
let player2 = null;

let oldPlayer1Hp = null;
let oldPlayer2Hp = null;
let oldPlayer1MagicScreen = null;
let oldPlayer2MagicScreen = null;

//Применение способностей
let selectedAbility = null; // Хранит выбранную способность, если требуется цель
let originalCursor = document.body.style.cursor; // Сохраняем оригинальный курсор

// Загрузка данных при открытии экрана
window.onload = function() {
    refreshAll();
};

function refreshAll() {
    loadPlayers();
    loadPlayerAbilities();
}


document.addEventListener("DOMContentLoaded", function () {
    document.body.style.backgroundImage = `url('${HOST}/images/location/Dead_Forest.png')`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundPosition = "center";
    document.body.style.backgroundRepeat = "no-repeat";
});

function loadPlayers() {
    fetch(`${HOST}/getPlayer/player1`)
        .then(response => response.json())
        .then(data => {
            console.log("загрузка данных персонажа:", data);

            let player1Box = document.getElementById("player1");

            // Проверка снижения HP (мигаем красным)
            if (oldPlayer1Hp !== null && data.healthValue < oldPlayer1Hp) {
                let hpBar = document.getElementById("hpBar1");
                hpBar.classList.add("damage-blink");
                setTimeout(() => hpBar.classList.remove("damage-blink"), 1000);
            }
            oldPlayer1Hp = data.healthValue;

            // Проверка снижения магического щита (мигаем голубым)
            if (oldPlayer1MagicScreen !== null && data.magicScreenValue < oldPlayer1MagicScreen) {
                let magicScreenBar = document.getElementById("magicScreenBar1");
                magicScreenBar.classList.add("shield-blink");
                setTimeout(() => magicScreenBar.classList.remove("shield-blink"), 1000);
            }
            oldPlayer1MagicScreen = data.magicScreenValue;

            player1 = data;
            player1Box.innerHTML = "";
            player1Box.style.backgroundImage = `url('${HOST}/images/hero/${data.heroClass}_PORT.png')`;
            document.getElementById("player1Name").textContent = data.name;
            updateHpBars();
            updateEndurance(data, "enduranceP1"); // Обновляем выносливость

            let frameOverlay = document.createElement("div");
            frameOverlay.classList.add("frame-overlay");
            frameOverlay.style.backgroundImage = `url('${HOST}/images/hero/frame_02.png')`;
            player1Box.appendChild(frameOverlay);
        })
        .catch(err => console.error("Ошибка загрузки Player1", err));

    fetch(`${HOST}/getPlayer/player2`)
        .then(response => response.json())
        .then(data => {
            let player2Box = document.getElementById("player2");

            // Проверка снижения HP (мигаем красным)
            if (oldPlayer2Hp !== null && data.healthValue < oldPlayer2Hp) {
                let hpBar = document.getElementById("hpBar2");
                hpBar.classList.add("damage-blink");
                setTimeout(() => hpBar.classList.remove("damage-blink"), 1000);
            }
            oldPlayer2Hp = data.healthValue;

            // Проверка снижения магического щита (мигаем голубым)
            if (oldPlayer2MagicScreen !== null && data.magicScreenValue < oldPlayer2MagicScreen) {
                let magicScreenBar = document.getElementById("magicScreenBar2");
                magicScreenBar2.classList.add("shield-blink");
                setTimeout(() => magicScreenBar2.classList.remove("shield-blink"), 1000);
            }
            oldPlayer2MagicScreen = data.magicScreenValue;

            player2 = data;
            player2Box.innerHTML = "";
            player2Box.style.backgroundImage = `url('${HOST}/images/hero/${data.heroClass}_PORT.png')`;
            document.getElementById("player2Name").textContent = data.name;
            updateHpBars();
            updateEndurance(data, "enduranceP2"); // Обновляем выносливость

            let frameOverlay = document.createElement("div");
            frameOverlay.classList.add("frame-overlay");
            frameOverlay.style.backgroundImage = `url('${HOST}/images/hero/frame_02.png')`;
            player2Box.appendChild(frameOverlay);
        })
        .catch(err => console.error("Ошибка загрузки Player2", err));
}

//Классы для анимации мигания
const style = document.createElement('style');
style.innerHTML = `
  .damage-blink {
      animation: damageEffect 1s ease-in-out 3;
  }
  .shield-blink {
      animation: shieldEffect 1s ease-in-out 3;
  }
  @keyframes damageEffect {
      0% { background-color: red; }
      50% { background-color: white; }
      100% { background-color: red; }
  }
  @keyframes shieldEffect {
      0% { background-color: blue; }
      50% { background-color: white; }
      100% { background-color: blue; }
  }
`;
document.head.appendChild(style);

//Счетчик раундов
function updateRoundNumber(round) {
    document.getElementById("roundNumber").textContent = `Раунд ${round}`;
}

//Бой
document.getElementById('fightForm').addEventListener('submit', function (e) {
    e.preventDefault();

    if (!player1 || !player2) {
        alert("Оба игрока должны быть загружены!");
        return;
    }

    fetch(`${HOST}/fight`)
        .then(response => response.json())
        .then(data => {
            console.log("Ответ от сервера:", data);

            updateRoundNumber(data.roundCount);

            let messageHtml = data.message
                .map(line => `<p>${line}</p>`)
                .join("");
            document.getElementById('roundResult').innerHTML = messageHtml;

            if (data.isOver) {
                console.log("Открываем модальное окно...");
                document.querySelector(".modalBackground").style.display = "flex";
                openLootBox();
            }

            setTimeout(refreshAll, 80);
        })
        .catch(err => {
            console.error(err);
            document.getElementById('roundResult').textContent = "Ошибка во время боя!";
        });
});

function updateHpBars() {
    if (player1 && player1.healthMaxValue !== undefined) {
        let hpPercent1 = Math.floor((player1.healthValue / player1.healthMaxValue) * 100);
        document.getElementById("hpBar1").style.height = hpPercent1 + "%";
        document.getElementById("hpValue1").textContent = `${Math.floor(player1.healthValue)}/${Math.floor(player1.healthMaxValue)}`;
    }
    if (player2 && player2.healthMaxValue !== undefined) {
        let hpPercent2 = Math.floor((player2.healthValue / player2.healthMaxValue) * 100);
        document.getElementById("hpBar2").style.height = hpPercent2 + "%";
        document.getElementById("hpValue2").textContent = `${Math.floor(player2.healthValue)}/${Math.floor(player2.healthMaxValue)}`;
    }

    if (player1 && player1.magicScreenMaxValue !== undefined) {
        let shieldPercent1 = Math.floor((player1.magicScreenValue / player1.magicScreenMaxValue) * 100);
        document.getElementById("magicScreenBar1").style.height = shieldPercent1 + "%";
        document.getElementById("magicScreenValue1").textContent = Math.floor(player1.magicScreenValue);
    }
    if (player2 && player2.magicScreenMaxValue !== undefined) {
        let shieldPercent2 = Math.floor((player2.magicScreenValue / player2.magicScreenMaxValue) * 100);
        document.getElementById("magicScreenBar2").style.height = shieldPercent2 + "%";
        document.getElementById("magicScreenValue2").textContent = Math.floor(player2.magicScreenValue);
    }
}

function updateEndurance(player, containerId) {
    let container = document.getElementById(containerId);
    if (!container) return; // Если контейнера нет, ничего не делаем

    container.innerHTML = ""; // Очищаем контейнер перед обновлением

    let enduranceCount = Math.floor(player.enduranceMaxValue || 0); // Количество кружков
    let filledCount = Math.floor(player.enduranceValue || 0); // Сколько кружков синие

    for (let i = 0; i < enduranceCount; i++) {
        let dot = document.createElement("div");
        dot.classList.add("endurance-dot");

        if (i < filledCount) {
            dot.classList.add("filled"); // Заполненный кружочек
        }

        container.appendChild(dot);
    }
}

// Функция для открытия модального окна
const modalTrigger = document.getElementsByClassName("trigger")[0];

// получаем ширину отображенного содержимого и толщину ползунка прокрутки
const windowInnerWidth = document.documentElement.clientWidth;
const scrollbarWidth = parseInt(window.innerWidth) - parseInt(windowInnerWidth);

// привязываем необходимые элементы
const bodyElementHTML = document.getElementsByTagName("body")[0];
const modalBackground = document.getElementsByClassName("modalBackground")[0];
const modalClose = document.getElementsByClassName("modalClose")[0];
const modalActive = document.getElementsByClassName("modalActive")[0];

// функция для корректировки положения body при появлении ползунка прокрутки
function bodyMargin() {
    bodyElementHTML.style.marginRight = "-" + scrollbarWidth + "px";
}

// при длинной странице - корректируем сразу
bodyMargin();

// нажатие на крестик закрытия модального окна
document.querySelector(".modalClose").addEventListener("click", function () {
    document.querySelector(".modalBackground").style.display = "none";
    replaceFightButton() // Показываем кнопку Отдохнуть
});

// закрытие модального окна на зону вне окна, т.е. на фон
document.querySelector(".modalBackground").addEventListener("click", function (event) {
    if (event.target.classList.contains("modalBackground")) {
        document.querySelector(".modalBackground").style.display = "none";
    }
});

document.addEventListener("DOMContentLoaded", function () {
    let closeButton = document.querySelector(".modalClose");

    if (closeButton) {
        let closeImg = document.createElement("img");
        closeImg.src = `${HOST}/images/x.jpg`;
        closeImg.alt = "Закрыть";
        closeButton.appendChild(closeImg);
    } else {
        console.error("Ошибка: .modalClose не найден в DOM!");
    }
});

function openLootBox() {
    console.log("Функция openLootBox вызвана!");

    fetch(`${HOST}/lootbox`)
        .then(response => response.json())
        .then(data => {
            console.log("Полученные данные от сервера:", data);

            if (!data || !data.lootbox) {
                console.error("Ошибка: сервер не вернул lootbox!");
                return;
            }

            displayLootBox(data.lootbox);
        })
        .catch(err => console.error("Ошибка загрузки лутбокса:", err));
}

// Функция отрисовки наград
function displayLootBox(items) {
    console.log("Вызвана displayLootBox с предметами:", items);

    if (!items || !Array.isArray(items)) {
        console.error("Ошибка: items не является массивом!");
        return;
    }

    let container = document.getElementById("lootBoxContainer");
    container.innerHTML = ""; // Очищаем контейнер

    items.forEach(item => {
        let itemDiv = document.createElement("div");
        itemDiv.classList.add("loot-item");

        let img = document.createElement("img");
        img.src = item.picture;
        img.alt = item.name;

        let name = document.createElement("p");
        name.textContent = item.name;

        itemDiv.appendChild(img);
        itemDiv.appendChild(name);

        itemDiv.addEventListener("click", function () {
            claimLoot(item.id);
        });

        container.appendChild(itemDiv);
    });
}

// Функция отправки выбранной награды на сервер
function claimLoot(itemId) {
    fetch(`${HOST}/claim-reward`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ itemId: itemId })
    })
    .then(response => response.json())
    .then(data => {
        console.log("Награда получена:", data);
        document.querySelector(".modalBackground").style.display = "none"; // Закрываем окно после выбора
        replaceFightButton()
    })
    .catch(err => console.error("Ошибка получения награды:", err));
}

document.getElementById("restButton").addEventListener("click", function () {
    window.location.href = "inventory.html";
});

// Функция для замены кнопки "Бой!" на "Отдохнуть"
function replaceFightButton() {
    let fightButton = document.querySelector(".fight-button");
    let restButton = document.getElementById("restButton");

    if (!fightButton || !restButton) {
        console.error("Ошибка: кнопка 'Бой' или 'Отдохнуть' не найдена!");
        return;
    }

    restButton.style.display = "inline-block"; // Показываем кнопку
    restButton.style.width = fightButton.offsetWidth + "px"; // Наследуем ширину кнопки "Бой!"
    restButton.style.height = fightButton.offsetHeight + "px"; // Наследуем высоту кнопки "Бой!"
    restButton.style.backgroundColor = "#28a745"; //Принудительно ставим зелёный цвет
    restButton.style.color = "white"; //Делаем текст белым
    restButton.style.fontSize = "22px"; //Делаем шрифт крупнее
    restButton.style.fontWeight = "bold"; // Делаем шрифт жирным

    fightButton.replaceWith(restButton); // Заменяем кнопку "Бой!"
}

function loadPlayerAbilities() {
    // Загрузка способностей игрока 1
    fetch(`${HOST}/getPlayer/abilities/player1`)
        .then(response => response.json())
        .then(abilities => {
            console.log("Способности игрока 1:", abilities);

            // Используем уже загруженные данные о player1
            const heroEndurance = player1.enduranceValue; // Смотрим, сколько у героя выносливости

            abilities.forEach((ability, index) => {
                let slotId = `ability${index + 1}`;
                let abilitySlot = document.getElementById(slotId);

                ability.slotId = slotId;

                if (abilitySlot) {
                    // Устанавливаем иконку и описание
                    abilitySlot.style.backgroundImage = `url('${ability.picturePath}')`;
                    abilitySlot.setAttribute("data-tooltip", `${ability.name}: ${ability.description}`);

                    // Логика блокировки
                    if (!ability.isActive || ability.cost > heroEndurance) {
                        // Блокируем
                        abilitySlot.classList.add("disabled");
                        abilitySlot.onclick = null;
                        console.log(`Блокируем ${ability.name} (cost:${ability.cost} / heroEndurance:${heroEndurance})`);
                    } else {
                        // Разблокируем
                        abilitySlot.classList.remove("disabled");
                        abilitySlot.onclick = () => {
                            if (ability.type === "ENEMY_TARGET") {
                                startTargetingMode(ability);
                            } else {
                                // ...
                            }
                        };
                    }
                }
            });
        })
        .catch(err => console.error("Ошибка загрузки способностей игрока1", err));

    // Аналогично для игрока 2
    fetch(`${HOST}/getPlayer/abilities/player2`)
        .then(response => response.json())
        .then(abilities => {
            console.log("Способности игрока 2:", abilities);

            // Используем уже загруженные данные о player2
            const heroEndurance = player2.enduranceValue;

            abilities.forEach((ability, index) => {
                let slotId = `ability${index + 1 + 4}`;
                let abilitySlot = document.getElementById(slotId);

                ability.slotId = slotId;

                if (abilitySlot) {
                    abilitySlot.style.backgroundImage = `url('${ability.picturePath}')`;
                    abilitySlot.setAttribute("data-tooltip", `${ability.name}: ${ability.description}`);

                    if (!ability.isActive || ability.cost > heroEndurance) {
                        abilitySlot.classList.add("disabled");
                        abilitySlot.onclick = null;
                        console.log(`Блокируем ${ability.name} (cost:${ability.cost} / heroEndurance:${heroEndurance})`);
                    } else {
                        abilitySlot.classList.remove("disabled");
                        abilitySlot.onclick = () => {
                            if (ability.type === "ENEMY_TARGET") {
                                startTargetingMode(ability);
                            } else {
                                // ...
                            }
                        };
                    }
                }
            });
        })
        .catch(err => console.error("Ошибка загрузки способностей игрока2", err));
}

function startTargetingMode(ability) {
    console.log(`Выбрана способность ${ability.name} - Ожидание цели`);
    selectedAbility = ability;

    // Проверяем, доступен ли указатель
    let targetCursor = `${HOST}/images/target.png`;

    let img = new Image();
    img.src = targetCursor;
    img.onload = () => {
        document.body.style.cursor = `url('${targetCursor}') 16 16, pointer`; // Указываем размер
    };

    // Добавляем слушатель клика для выбора цели
    document.getElementById("player2").addEventListener("click", onEnemyTargetClick);
}

function onEnemyTargetClick() {
    if (selectedAbility) {
        console.log(`Применение способности ${selectedAbility.name} на врага`);

        // Сохраняем ID слота ДО обнуления selectedAbility
        let abilitySlotId = selectedAbility.slotId;

        useAbility(selectedAbility, "player2")
            .then((data) => {
                if (!abilitySlotId) {
                    console.error("Ошибка: abilitySlotId равен null");
                    return;
                }

                // Находим слот
                let abilitySlot = document.getElementById(abilitySlotId);

                // Если способность деактивировалась
                if (!data.isActive) {
                    if (abilitySlot) {
                        abilitySlot.classList.add("disabled");
                        abilitySlot.onclick = null;
                        console.log(`Выключение способности в слоте ${abilitySlotId}`);
                    } else {
                        console.error(`Ошибка: не найден слот ${abilitySlotId}`);
                    }
                }
            })
            .catch(err => console.error("Ошибка при использовании способности", err));

        // Сбрасываем выбор и восстанавливаем курсор
        selectedAbility = null;
        setTimeout(() => {
            document.body.style.cursor = "auto";
        }, 100);

        // Удаляем слушатель
        document.getElementById("player2").removeEventListener("click", onEnemyTargetClick);
    }
}

function useAbility(ability, target) {
    let requestBody = { ability: ability.name };
    if (target) {
        requestBody.target = target;
    }

    console.log(`Отправка запроса на сервер для способности: ${ability.name}`);

    return fetch(`${HOST}/hero/useAbility`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(requestBody)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Ошибка сервера: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log("Ответ сервера на способность:", data);

        refreshAll();

        loadJournal();

        return data;
    })
    .catch(err => {
        console.error("Ошибка при использовании способности:", err);
        throw err;
    });
}

function loadJournal() {
    fetch(`${HOST}/journal/getMessage`)
        .then(response => response.json())
        .then(data => {
            // data — это массив строк
            console.log("Журнал боя:", data);

            let roundResult = document.getElementById("roundResult");
            if (!roundResult) {
                console.error("Ошибка: контейнер #roundResult не найден!");
                return;
            }

            // Очищаем старое содержимое
            roundResult.innerHTML = "";

            // Выводим каждую строку журнала
            data.forEach(line => {
                let p = document.createElement("p");
                p.textContent = line;
                roundResult.appendChild(p);
            });
        })
        .catch(err => console.error("Ошибка при загрузке журнала боя:", err));
}
