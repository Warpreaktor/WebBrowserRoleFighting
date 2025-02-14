document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll(".item");
    const slots = document.querySelectorAll(".slot, .inventory-slot");

    items.forEach(item => {
        item.addEventListener("dragstart", dragStart);
    });

    slots.forEach(slot => {
        slot.addEventListener("dragover", dragOver);
        slot.addEventListener("drop", drop);
    });

    function dragStart(event) {
        event.dataTransfer.setData("text/plain", event.target.dataset.id);
        setTimeout(() => {
            event.target.classList.add("hidden"); // Вместо display: none используем скрытие через класс
        }, 0);
    }

    function dragOver(event) {
        event.preventDefault();
    }

    function drop(event) {
        event.preventDefault();
        const itemId = event.dataTransfer.getData("text/plain");
        const item = document.querySelector(`[data-id='${itemId}']`);
        const slot = event.target;

        // Убедимся, что можно разместить предмет в слот
        if ((slot.classList.contains("slot") || slot.classList.contains("inventory-slot")) && !slot.hasChildNodes()) {
            slot.appendChild(item);
            item.classList.remove("hidden"); // Делаем предмет видимым
        } else {
            console.log("Слот уже занят!");
        }
    }

    const HOST = "http://localhost:4567";
    fetch(`${HOST}/getPlayer/statistic/player1`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("char-name").textContent = data.name;
            document.getElementById("char-class").textContent = data.heroClass;
            document.getElementById("char-hp").textContent = data.health;
            document.getElementById("char-max-hp").textContent = data.maxHealth;
            document.getElementById("char-mage-shield").textContent = data.shield;
            document.getElementById("char-max-mage-shield").textContent = data.maxShield;
            document.getElementById("char-accuracy").textContent = data.accuracy;
            document.getElementById("char-agility").textContent = data.agility;
            document.getElementById("char-evasion").textContent = data.evasion;
            document.getElementById("char-fullDamage").textContent = data.damage.fullDamage;
            document.getElementById("char-physicalDamage").textContent = data.damage.physicalDamage;
            document.getElementById("char-fireDamage").textContent = data.damage.fireDamage;
        })
        .catch(error => {
            console.error("Ошибка загрузки данных персонажа:", error);
        });

});
