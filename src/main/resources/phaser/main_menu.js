class MainMenu extends Phaser.Scene {
    constructor() {
        super({ key: "MainMenu" });
        this.playerName = "Герой"; // Имя персонажа
        this.selectedClass = "Barbarian"; // Класс по умолчанию
    }

    preload() {
        this.load.image("background", "main_menu_background.png"); // Загружаем фон
        this.load.image("barbarian", "hero/barbarian/BARBARIAN_SEL.png"); // Загружаем портреты персонажей
        this.load.image("archer", "hero/archer.png");
        this.load.image("mage", "hero/mage.png");
        this.load.image("skeleton", "hero/skeleton.png");
        this.load.image("halfling", "hero/halfling.png");
        this.load.image("samodiva", "hero/samodiva.png");

        this.load.image("startButton", "button/start_button.png");
    }

    create() {
        // Фон
        this.add.image(this.scale.width / 2, this.scale.height / 2, "background")
            .setDisplaySize(this.scale.width, this.scale.height);

        // Заголовок
        this.add.text(this.scale.width / 2, 80, "Герои Слёз и Ужаса", {
            fontSize: "48px",
            color: "#ffffff",
            fontFamily: "Arial",
            stroke: "#000000",
            strokeThickness: 6
        }).setOrigin(0.5);

        // Поле ввода имени (DOM)
//        this.createInputField();

        // Выбранный класс (отображение)
        this.classText = this.add.text(this.scale.width / 2, 260, `Класс: ${this.selectedClass}`, {
            fontSize: "28px",
            color: "#ffffff",
            backgroundColor: "#000000",
            padding: { x: 10, y: 5 },
            fontFamily: "Arial",
        }).setOrigin(0.5)
          .setInteractive({ useHandCursor: true }) // 👈 Добавляем, чтобы было понятно, что элемент кликабельный
          .setDepth(15); // 👈 Поднимаем выше остальных кнопок

        this.classText.on("pointerdown", () => {
            this.showClassSelection();
        });

        // Кнопка "Создать персонажа"
        this.createButton = this.createButtonElement("Создать персонажа", this.scale.height / 2, () => {
            console.log("Клик прошёл!");
            this.createCharacter();
        });

        // Кнопка "Случайный герой"
        this.randomButton = this.createButtonElement("Случайный герой", this.scale.height / 1 + 60, "#ff3300", () => {
            this.createRandomCharacter();
        });

        this.startButton = this.createButtonElement("🚀 Старт", this.scale.height / 2 + 120, "#33cc33", () => {
            this.scene.start("LoadoutScene");
        });
        this.startButton.setVisible(false);

        this.resultText = this.add.text(this.scale.width / 2, this.scale.height - 100, "Ожидание...", {
            fontSize: "24px",
            color: "#00ff00"
        }).setOrigin(0.5);
    }

    createInputField() {
        let existingInput = document.getElementById("nameField");
        if (existingInput) existingInput.remove();

        let input = document.createElement("input");
        input.type = "text";
        input.id = "nameField";
        input.placeholder = "Введите имя героя";
        input.style.position = "absolute";
        input.style.width = "300px";
        input.style.height = "40px";
        input.style.fontSize = "20px";
        input.style.textAlign = "center";
        input.style.border = "2px solid white";
        input.style.borderRadius = "10px";
        input.style.background = "rgba(0, 0, 0, 0.7)";
        input.style.color = "white";
        input.style.outline = "none";
        input.style.padding = "5px";
        input.style.left = `${window.innerWidth / 2 - 150}px`;
        input.style.top = `180px`;
        input.style.zIndex = "1";
        input.style.pointerEvents = "auto";

        document.body.appendChild(input);
    }

    showClassSelection() {
        let classes = [
            { name: "Barbarian", img: "barbarian" },
            { name: "Archer", img: "archer" },
            { name: "Mage", img: "mage" },
            { name: "Skeleton", img: "skeleton" },
            { name: "Halfling", img: "halfling" },
            { name: "Samodiva", img: "samodiva" }
        ];

        // Удаляем предыдущее меню, если оно есть
        if (this.classSelectionMenu) {
            this.classSelectionMenu.destroy();
        }

        let menu = this.add.container(this.scale.width / 2, this.scale.height / 2).setDepth(20); // 👈 Поднимаем выше кнопок

        let background = this.add.graphics();
        background.fillStyle(0x000000, 0.8);
        background.fillRect(-220, -160, 440, 320);
        menu.add(background);

        let columns = 4;
        let spacingX = 110;
        let spacingY = 90;

        classes.forEach((cls, index) => {
            let row = Math.floor(index / columns);
            let col = index % columns;

            let posX = -160 + col * spacingX;
            let posY = -110 + row * spacingY;

            let portrait = this.add.image(posX, posY, cls.img)
                .setDisplaySize(36, 64)
                .setInteractive({ useHandCursor: true }); // 👈 Добавляем интерактивность

            let nameText = this.add.text(posX, posY + 40, cls.name, {
                fontSize: "18px",
                color: "#ffffff",
                fontFamily: "Arial"
            }).setOrigin(0.5);

            portrait.on("pointerdown", () => {
                this.selectedClass = cls.name;
                this.classText.setText(`Класс: ${this.selectedClass}`);
                this.classSelectionMenu.destroy();
                this.classSelectionMenu = null;
            });

            menu.add(portrait);
            menu.add(nameText);
        });

        this.classSelectionMenu = menu;
        this.add.existing(menu);
    }

    createButtonElement(text, posY, callback, scale = 0.3) {
        // Создаём изображение кнопки
        const buttonImage = this.add.image(0, 0, "startButton").setScale(scale);

        // Создаём текст и центрируем его
        const buttonText = this.add.text(0, 0, text, {
            fontSize: "16px",
            color: "#ffffff",
            fontFamily: "Arial",
            fontStyle: "bold"
        }).setOrigin(0.5);

        // Создаём контейнер, объединяя картинку и текст
        const button = this.add.container(this.scale.width / 2, posY, [buttonImage, buttonText]);

        // Устанавливаем размер контейнера по кнопке
        button.setSize(buttonImage.width * scale, buttonImage.height * scale);

        // Делаем кнопку кликабельной
        button.setInteractive();


        // Эффекты при наведении
        button.on("pointerover", () => {
            buttonImage.setTint(0x44ff44);
        });

        button.on("pointerout", () => {
            buttonImage.clearTint();
        });

        return button;
    }

    createCharacter() {
        this.playerName = document.getElementById("nameField").value || "Герой";

        fetch(`${HOST}/createCharacter`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name: this.playerName, heroClass: this.selectedClass })
        })
        .then(response => response.json())
        .then(data => {
            this.resultText.setText(`✅ Создан герой: ${data.name} (${data.heroClass}) HP: ${data.healthValue}`);
            this.startButton.setVisible(true);
        })
        .catch(err => {
            console.error("Ошибка при создании персонажа:", err);
            this.resultText.setText("❌ Ошибка при создании!");
        });
    }

    createRandomCharacter() {
        fetch(`${HOST}/createRandomCharacter`)
        .then(response => response.json())
        .then(data => {
            this.resultText.setText(`✅ Создан герой: ${data.name} (${data.heroClass}) HP: ${data.hitpoint}`);
            this.startButton.setVisible(true);
        })
        .catch(err => {
            console.error("Ошибка fetch:", err);
            this.resultText.setText("❌ Ошибка при создании случайного героя!");
        });
    }
}

// Конфигурируем Phaser
const config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    scene: [MainMenu, LoadoutScene], // Подключаем обе сцены
};

// Запускаем игру
new Phaser.Game(config);
