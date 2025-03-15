package hero;

import core.GameMaster;
import lombok.Getter;
import mechanic.interfaces.Effect;
import mechanic.interfaces.Switchable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *  Состояние героя. Все его действующие баффы и дебафы описываются здесь.
 *  Все переключения состояний проходят через этот класс.
 *  Он следит за тем, чтобы при любом переключении своих состояний сообщить об этом GameMaster.
 *  Если это правило будет соблюдаться, то GameMaster вовремя выключит действие того или иного эффекта.
 */
public class EffectStack {

    @Getter
    private final Hero owner;

    public EffectStack(Hero owner) {
        this.owner = owner;
    }

    private List<Effect> effects = new LinkedList<>();

    /**
     * Наложить эффект на героя
     *
     * @param effect накладываемый эффект
     * @param roundCount количество раундов действия эффекта
     */
    public void impose(Effect effect, int roundCount) {
        effects.add(effect);
        GameMaster.getInstance().switchOn(effect, roundCount);
    }

    /**
     * Удалить эффект с героя
     *
     * @param effect деактивируемый эффект
     */
    public void remove(Effect effect) {
        effects.remove(effect);
    }

    public void activate() {
        effects.forEach(Effect::trigger);
    }

    public Optional<Effect> findEffect(String effectName) {

        for (Effect ef : effects) {
            if (Objects.equals(ef.getName(), effectName)) {
                return Optional.of(ef);
            }
        }
        return Optional.empty();
    }
}
