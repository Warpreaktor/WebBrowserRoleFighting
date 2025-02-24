package fight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import dto.damage.DamageDto;
import mechanic.interfaces.Attackable;

/**
 * Класс содержит в себе результат атаки.
 * Главным образом это урон и сообщение о том, как был нанесён урон
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttackDto {

    Attackable attacker;

    boolean isFail;

    boolean isCritical;

    @NonNull
    DamageDto damageDto;

    String message;

    /**
     * Конструктор для успешной атаки
     *
     * @param attacker атакующий
     * @param damageDto итоговый
     * @param message описание события
     */
    public AttackDto(Attackable attacker, DamageDto damageDto, String message) {
        this.attacker = attacker;
        this.damageDto = damageDto;
        this.message = message;
    }

    /**
     * Конструктор для провалившейся атаки
     *
     * @param attacker атакующий
     * @param message сообщение о событии
     */
    public AttackDto(Attackable attacker, String message) {
        this.isFail = true;
        this.attacker = attacker;
        this.message = message;
        damageDto = new DamageDto(0.0, 0.0);
    }
}
