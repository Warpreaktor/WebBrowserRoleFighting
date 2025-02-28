let player1 = null;
let player2 = null;

let oldPlayer1Hp = null;
let oldPlayer2Hp = null;
let oldPlayer1Shield = null;
let oldPlayer2Shield = null;

window.onload = loadPlayers;

document.addEventListener("DOMContentLoaded", function () {
    document.body.style.backgroundImage = `url('${HOST}/images/location/Dead_Forest.png')`;
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
            if (oldPlayer1Hp !== null && data.hitpoint < oldPlayer1Hp) {
                let hpBar = document.getElementById("hpBar1");
                hpBar.classList.add("damage-blink");
                setTimeout(() => hpBar.classList.remove("damage-blink"), 1000);
            }
            oldPlayer1Hp = data.hitpoint;

            // Проверка снижения магического щита (мигаем голубым)
            if (oldPlayer1Shield !== null && data.mageShield < oldPlayer1Shield) {
                let shieldBar = document.getElementById("shieldBar1");
                shieldBar.classList.add("shield-blink");
                setTimeout(() => shieldBar.classList.remove("shield-blink"), 1000);
            }
            oldPlayer1Shield = data.mageShield;

            player1 = data;
            player1Box.innerHTML = "";
            player1Box.style.backgroundImage = `url('${HOST}/images/hero/${data.heroClass}_PORT.png')`;
            document.getElementById("player1Name").textContent = data.name;
            updateHpBars();
            updateEnduranceBars();

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
            if (oldPlayer2Hp !== null && data.hitpoint < oldPlayer2Hp) {
                let hpBar = document.getElementById("hpBar2");
                hpBar.classList.add("damage-blink");
                setTimeout(() => hpBar.classList.remove("damage-blink"), 1000);
            }
            oldPlayer2Hp = data.hitpoint;

            // Проверка снижения магического щита (мигаем голубым)
            if (oldPlayer2Shield !== null && data.mageShield < oldPlayer2Shield) {
                let shieldBar = document.getElementById("shieldBar2");
                shieldBar.classList.add("shield-blink");
                setTimeout(() => shieldBar.classList.remove("shield-blink"), 1000);
            }
            oldPlayer2Shield = data.mageShield;

            player2 = data;
            player2Box.innerHTML = "";
            player2Box.style.backgroundImage = `url('${HOST}/images/hero/${data.heroClass}_PORT.png')`;
            document.getElementById("player2Name").textContent = data.name;
            updateHpBars();
            updateEnduranceBars();

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

            setTimeout(loadPlayers, 80);
        })
        .catch(err => {
            console.error(err);
            document.getElementById('roundResult').textContent = "Ошибка во время боя!";
        });
});

function updateHpBars() {
    if (player1 && player1.maxHitpoint !== undefined) {
        let hpPercent1 = Math.floor((player1.hitpoint / player1.maxHitpoint) * 100);
        document.getElementById("hpBar1").style.height = hpPercent1 + "%";
        document.getElementById("hpValue1").textContent = Math.floor(player1.hitpoint);
    }
    if (player2 && player2.maxHitpoint !== undefined) {
        let hpPercent2 = Math.floor((player2.hitpoint / player2.maxHitpoint) * 100);
        document.getElementById("hpBar2").style.height = hpPercent2 + "%";
        document.getElementById("hpValue2").textContent = Math.floor(player2.hitpoint);
    }

    if (player1 && player1.maxMageShield !== undefined) {
        let shieldPercent1 = Math.floor((player1.mageShield / player1.maxMageShield) * 100);
        document.getElementById("shieldBar1").style.height = shieldPercent1 + "%";
        document.getElementById("shieldValue1").textContent = Math.floor(player1.mageShield);
    }
    if (player2 && player2.maxMageShield !== undefined) {
        let shieldPercent2 = Math.floor((player2.mageShield / player2.maxMageShield) * 100);
        document.getElementById("shieldBar2").style.height = shieldPercent2 + "%";
        document.getElementById("shieldValue2").textContent = Math.floor(player2.mageShield);
    }
}

function updateEnduranceBars() {
    if (player1) {
        let restPercent1 = Math.min(player1.endurance * 100, 100);
        document.getElementById("enduranceP1").style.width = restPercent1 + "%";
    }
    if (player2) {
        let restPercent2 = Math.min(player2.endurance * 100, 100);
        document.getElementById("enduranceP2").style.width = restPercent2 + "%";
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





