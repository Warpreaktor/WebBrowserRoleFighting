package item.weapon;

import config.ApplicationProperties;
import item.offhand.Offhand;

import java.util.UUID;

/**
 * Факел для выжигания всякой нечисти, включая пауков!
 */
public class Torch extends Offhand {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/offhand/torch.png";

    /**
     * Создает факел, который можно держать в левой руке
     */
    public Torch() {
        super(UUID.randomUUID().toString(), "Факел", picturePath);

        // Факел дает небольшой бонус к урону огнем
        setMagicDamageBonus(2.0);

        // Может слегка замедлять атаку, так как занимает руку
        setAttackSpeed(0.95);
    }
}
