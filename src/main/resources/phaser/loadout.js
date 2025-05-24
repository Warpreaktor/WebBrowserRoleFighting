class LoadoutScene extends Phaser.Scene {
    constructor() {
        super({ key: "LoadoutScene" });
    }

    preload() {
        this.load.image("background", `${HOST}/images/background_edit_hero.jpg`);
        this.load.image("slot", `${HOST}/images/ui/slot.png`);
        this.load.image("inventorySlot", `${HOST}/images/ui/inventory_slot.png`);
        this.load.image("startButton", `${HOST}/images/ui/start_button.png`);
        this.load.image("continueButton", `${HOST}/images/ui/continue_button.png`);
    }

    create() {
        // Устанавливаем фон
        this.add.image(400, 300, "background").setScale(1.2);

        // Загружаем данные персонажа
        this.loadCharacterData();

        // Добавляем кнопку "START"
        this.startButton = this.add.image(400, 550, "startButton").setInteractive();
        this.startButton.on("pointerdown", () => {
            this.startNewGame();
        });

        // Кнопка "ПРОДОЛЖИТЬ"
        this.continueButton = this.add.image(400, 550, "continueButton").setInteractive().setVisible(false);
        this.continueButton.on("pointerdown", () => {
            this.continueGame();
        });

        // Загружаем инвентарь и экипировку
        this.loadInventory();
    }

    // Функция загрузки данных персонажа
    loadCharacterData() {
        fetch(`${HOST}/getPlayer/statistic/player1`)
            .then(response => response.json())
            .then(data => {
                this.displayCharacterStats(data);
                if (data.statistic.wins > 0) {
                    this.startButton.setVisible(false);
                    this.continueButton.setVisible(true);
                }
            })
            .catch(error => console.error("Ошибка загрузки данных персонажа:", error));
    }

    // Функция отображения статистики персонажа
    displayCharacterStats(data) {
        const characteristic = data.characteristic;
        const statistic = data.statistic;

        this.add.text(50, 50, `Имя: ${characteristic.name}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 80, `Класс: ${characteristic.heroClass}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 110, `Уровень: ${statistic.level}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 140, `HP: ${Math.floor(characteristic.healthValue)} / ${Math.floor(characteristic.healthMaxValue)}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 170, `Маг. Щит: ${Math.floor(characteristic.magicScreenValue)} / ${Math.floor(characteristic.magicScreenMaxValue)}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 200, `Выносливость: ${characteristic.enduranceValue.toFixed(1)} / ${characteristic.enduranceMaxValue.toFixed(1)}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 230, `Меткость: ${characteristic.accuracy.toFixed(1)}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 260, `Уклонение: ${characteristic.evasion.toFixed(1)}`, { fontSize: "20px", fill: "#fff" });
        this.add.text(50, 290, `Урон: ${Math.floor(characteristic.damage.sumDamage)}`, { fontSize: "20px", fill: "#fff" });
    }

    // Функция загрузки инвентаря
    loadInventory() {
        fetch(`${HOST}/inventory`)
            .then(response => response.json())
            .then(data => {
                this.renderInventory(data.inventory.cells);
            })
            .catch(error => console.error("Ошибка загрузки инвентаря:", error));
    }

    // Отображение инвентаря
    renderInventory(items) {
        const startX = 500;
        const startY = 100;
        const slotSize = 70;
        let index = 0;

        items.forEach((item, i) => {
            let x = startX + (i % 4) * slotSize;
            let y = startY + Math.floor(i / 4) * slotSize;

            this.add.image(x, y, "inventorySlot").setScale(1);

            if (item !== null) {
                let itemSprite = this.add.image(x, y, item.picture).setInteractive();
                itemSprite.setScale(0.5);
                this.input.setDraggable(itemSprite);
                itemSprite.itemId = item.id;
            }
        });

        // Настраиваем перетаскивание
        this.input.on("drag", (pointer, gameObject, dragX, dragY) => {
            gameObject.x = dragX;
            gameObject.y = dragY;
        });

        this.input.on("dragend", (pointer, gameObject) => {
            console.log(`Перемещение предмета ${gameObject.itemId}`);
        });
    }

    // Функция старта новой игры
    startNewGame() {
        fetch(`${HOST}/startNewGame`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(() => {
            this.scene.start("FightScene"); // Переход на экран боя
        })
        .catch(error => console.error("Ошибка при старте игры:", error));
    }

    // Функция продолжения игры
    continueGame() {
        fetch(`${HOST}/continueGame`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(() => {
            this.scene.start("FightScene"); // Переход на экран боя
        })
        .catch(error => console.error("Ошибка при продолжении игры:", error));
    }
}
