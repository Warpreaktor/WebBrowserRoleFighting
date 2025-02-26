const HOST = "http://localhost:4568";

let createdPlayer = null;

function enableStartButton() {
    if (createdPlayer) {
        document.getElementById("startButton").style.display = "block";
    }
}

function redirectToFight() {
    if (!createdPlayer) {
        alert("Сначала создайте персонажа!");
        return;
    }
    window.location.href = "inventory.html";
}

document.getElementById("startButton").addEventListener("click", redirectToFight);

document.getElementById('createForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Останавливаем стандартное поведение формы чтобы не слал get запрос

    const name = document.getElementById('name').value;
    const heroClass = document.getElementById('heroClass').value;

    const requestBody = JSON.stringify({ name: name, heroClass: heroClass });

    console.log("Отправка запроса:", requestBody);

    fetch(`${HOST}/createCharacter`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: requestBody
    })
    .then(response => response.json())
    .then(data => {
        console.log("Ответ сервера:", data);
        document.getElementById('creationResult').textContent = `✅ Создан герой: ${data.name} (${data.heroClass}) HP: ${data.hitpoint}`;
        createdPlayer = data;
        enableStartButton();
    })
    .catch(err => {
        console.error("Ошибка при создании персонажа:", err);
        document.getElementById('creationResult').textContent = "❌ Ошибка при создании!";
    });
});

// Кнопка случайного героя
document.getElementById('randomButton').addEventListener('click', function(e) {
    e.preventDefault();

    fetch(`${HOST}/createRandomCharacter`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('creationResult').textContent = `✅ Создан герой: ${data.name} (${data.heroClass}) HP: ${data.hitpoint}`;
            createdPlayer = data;
            enableStartButton();
        })
        .catch(err => {
            console.error("Ошибка fetch:", err);
            document.getElementById('creationResult').textContent = "❌ Ошибка при создании случайного героя!";
        });
});
