package item.shield;

import config.ApplicationProperties;
import item.shield.abstracts.Shield;

import java.util.UUID;

/**
 * Класс MagicShield - ваш персональный отпор всем колдунам и фокусникам...
 * <p>
 * Забудьте о магических проклятьях и заклинаниях! (Но не полностью)
 * Этот щит не просто блокирует физический урон, он еще умеет отражать часть магического!
 * </p>
 *
 * <p><b>История создания</b></p>
 * <p>
 * Однажды группа инженеров-алхимиков собралась в таверне и решила: "А давайте сделаем щит, да такой, который
 * будет работать против магии!". После трех бочек эля или четырех и одной неудачной попытки создать портал в другой мир,
 * они все-таки изобрели этот чудо-щит!
 * </p>
 *
 * @author Группа "Эльфийские алхимики, которые не любят магов"
 * @version 1.0 (первый выпуск после трёх или четырех неудачных попыток)
 */
public class MagicShield extends Shield {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/shield/magic_shield.png";

    public MagicShield() {
        super(UUID.randomUUID().toString(), "Магический щит", picturePath);

        setPhysicalBlock(0.2);
        setMagicBlock(0.5);
        setEvasionBonus(0.1);  // +10% к уклонению (магический щит легче)
    }
}
