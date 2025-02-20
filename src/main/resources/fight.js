let player1 = null;
let player2 = null;

let oldPlayer1Hp = null;
let oldPlayer2Hp = null;
let oldPlayer1Shield = null;
let oldPlayer2Shield = null;

window.onload = loadPlayers;

function loadPlayers() {
    fetch(`${HOST}/getPlayer/player1`)
        .then(response => response.json())
        .then(data => {
            let player1Box = document.getElementById("player1");

            // Проверка снижения HP (мигаем красным)
            if (oldPlayer1Hp !== null && data.hitpoint < oldPlayer1Hp) {
                player1Box.classList.add("damageDto");
                setTimeout(() => player1Box.classList.remove("damageDto"), 2000);
            }
            oldPlayer1Hp = data.hitpoint;

            // Проверка снижения магического щита (мигаем голубым)
            if (oldPlayer1Shield !== null && data.mageShield < oldPlayer1Shield) {
                player1Box.classList.add("shield");
                setTimeout(() => player1Box.classList.remove("shield"), 2000);
            }
            oldPlayer1Shield = data.mageShield;

            player1 = data;
            player1Box.innerHTML = ""; // Очищаем, чтобы не отображались лишние символы

            // Устанавливаем имя персонажа
            document.getElementById("player1Name").textContent = data.name;

            updateHpBars();
            updateReloadBars();

            // Установка фонового изображения
            document.getElementById("player1").style.backgroundImage =
                `url('${HOST}/images/${player1.heroClass}.jpg')`;

        })
        .catch(err => console.error("Ошибка загрузки Player1", err));

    fetch(`${HOST}/getPlayer/player2`)
        .then(response => response.json())
        .then(data => {
            let player2Box = document.getElementById("player2");

            // Проверка снижения HP (мигаем красным)
            if (oldPlayer2Hp !== null && data.hitpoint < oldPlayer2Hp) {
                player2Box.classList.add("damageDto");
                setTimeout(() => player2Box.classList.remove("damageDto"), 2000);
            }
            oldPlayer2Hp = data.hitpoint;

            // Проверка снижения магического щита (мигаем голубым, только если HP не уменьшилось)
            if (oldPlayer2Shield !== null && data.mageShield < oldPlayer2Shield && data.hitpoint >= oldPlayer2Hp) {
                player2Box.classList.add("shield");
                setTimeout(() => player2Box.classList.remove("shield"), 2000);
            }
            oldPlayer2Shield = data.mageShield;

            player2 = data;
            player2Box.innerHTML = ""; // Очищаем, чтобы не отображались лишние символы

            // Устанавливаем имя персонажа
            document.getElementById("player2Name").textContent = data.name;

            updateReloadBars();
            updateHpBars();

            // Установка фонового изображения
            document.getElementById("player2").style.backgroundImage =
                `url('${HOST}/images/${player2.heroClass}.jpg')`;
        })
        .catch(err => console.error("Ошибка загрузки Player2", err));
}

document.getElementById('fightForm').addEventListener('submit', function (e) {
    e.preventDefault();

    if (!player1 || !player2) {
        alert("Оба игрока должны быть загружены!");
        return;
    }

    fetch(`${HOST}/fight`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('roundResult').innerHTML = data.log.map(line => `<p>${line}</p>`).join("");
            setTimeout(loadPlayers, 80);
        })
        .catch(err => {
            console.error(err);
            document.getElementById('roundResult').textContent = "Ошибка во время боя!";
        });
});

function updateHpBars() {
    if (player1 && player1.maxHitpoint) {
        let hpPercent1 = Math.max((player1.hitpoint / player1.maxHitpoint) * 100, 0);
        document.getElementById("hpBar1").style.height = hpPercent1 + "%";
        document.getElementById("hpValue1").textContent = `${Math.floor(player1.hitpoint)}`;
    }
    if (player2 && player2.maxHitpoint) {
        let hpPercent2 = Math.max((player2.hitpoint / player2.maxHitpoint) * 100, 0);
        document.getElementById("hpBar2").style.height = hpPercent2 + "%";
        document.getElementById("hpValue2").textContent = `${Math.floor(player2.hitpoint)}`;
    }

    if (player1 && player1.maxMageShield) {
        let shieldPercent1 = Math.max((player1.mageShield / player1.maxMageShield) * 100, 0);
        document.getElementById("shieldBar1").style.height = shieldPercent1 + "%";
        document.getElementById("shieldValue1").textContent = `${player1.mageShield.toFixed(1)}`;
    }
    if (player2 && player2.maxMageShield) {
        let shieldPercent2 = Math.max((player2.mageShield / player2.maxMageShield) * 100, 0);
        document.getElementById("shieldBar2").style.height = shieldPercent2 + "%";
        document.getElementById("shieldValue2").textContent = `${player2.mageShield.toFixed(1)}`;
    }
}

function updateReloadBars() {
    if (player1) {
        let reloadPercent1 = Math.min(player1.reloader * 100, 100);
        document.getElementById("reloadBar1").style.width = reloadPercent1 + "%";
    }
    if (player2) {
        let reloadPercent2 = Math.min(player2.reloader * 100, 100);
        document.getElementById("reloadBar2").style.width = reloadPercent2 + "%";
    }
}

document.getElementById('fightForm').addEventListener('submit', function (e) {
    e.preventDefault();

    fetch(`${HOST}/fight`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('roundResult').textContent = data.message;

            if (data.isOver) {
                showRewardModal(data.winner);
            }

            setTimeout(loadPlayers, 80);
        })
        .catch(err => {
            console.error(err);
            document.getElementById('roundResult').textContent = "Ошибка во время боя!";
        });
});

// Функция отображения модального окна с наградами
function showRewardModal(winner) {
    if (winner.name !== player1.name) return; // Если победил не player1, награды не показываем

    fetch(`http://localhost:4567/get-rewards?winner=${winner.name}`)
        .then(response => response.json())
        .then(rewards => {
            let rewardContainer = document.getElementById("rewardOptions");
            rewardContainer.innerHTML = ""; // Очищаем старые кнопки

            rewards.forEach(reward => {
                let button = document.createElement("button");
                button.classList.add("reward-button");
                button.textContent = reward.name;
                button.onclick = () => selectReward(reward);
                rewardContainer.appendChild(button);
            });

            document.getElementById("rewardModal").style.display = "flex"; // Показываем окно
        })
        .catch(err => console.error("Ошибка загрузки наград:", err));
}

// Функция отправки выбранной награды на сервер
function selectReward(reward) {
    fetch(`http://localhost:4567/claim-reward`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ player: player1.name, reward: reward.name })
    })
    .then(response => response.json())
    .then(data => {
        console.log("Награда получена:", data);
        updateInventoryUI(); // Обновляем инвентарь
        document.getElementById("rewardModal").style.display = "none"; // Закрываем окно
    })
    .catch(err => console.error("Ошибка получения награды:", err));
}

// Закрытие модалки без выбора награды
document.getElementById("closeRewardModal").addEventListener("click", function () {
    document.getElementById("rewardModal").style.display = "none";
});

function updateFightLog(logs) {
    let logContainer = document.getElementById("roundResult");

    // Объединяем массив строк в единый HTML-блок
    logContainer.innerHTML = logs.map(line => `<p>${line}</p>`).join("");
}
