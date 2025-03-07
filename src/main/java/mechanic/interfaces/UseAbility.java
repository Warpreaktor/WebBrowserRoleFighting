package mechanic.interfaces;

import core.GameMaster;
import mechanic.Ability;

import java.util.List;
import java.util.Objects;

public interface UseAbility {

    List<Ability> getAbilities();

    void exhaustion(double cost);

    Double getEndurance();

    /**
     * Применения способности к цели
     *
     * @param abilityId идентификатор способности
     * @param defensible цель применяемой способности
     */
    default void useAbility(String abilityId, Defensible defensible) {
        var ability = getAbilities()
                .stream()
                .filter(ab -> Objects.equals(ab.getName(), abilityId))
                .findFirst()
                .orElseThrow();

        if (getEndurance() < ability.getCost() || !ability.isActive()) {
            return;
        }

        ability.apply(defensible);

        GameMaster.getInstance()
                .switchOff(ability, ability.getCoolDown());

        exhaustion(ability.getCost());
    }
}
