package mechanic.interfaces;

import core.GameMaster;
import dto.ability.AbilityDto;
import mechanic.Ability;
import mechanic.Endurance;

import java.util.List;
import java.util.Objects;

public interface UseAbility {

    List<Ability> getAbilities();

    void exhaustion(double cost);

    Endurance getEndurance();

    /**
     * Применения способности к цели
     *
     * @param abilityId идентификатор способности
     * @param defensible цель применяемой способности
     */
    default AbilityDto useAbility(String abilityId, Defensible defensible) {
        var ability = getAbilities()
                .stream()
                .filter(ab -> Objects.equals(ab.getName(), abilityId))
                .findFirst()
                .orElseThrow();

        if (getEndurance().getValue() < ability.getCost()) {
            throw new RuntimeException("Не хватает выносливости");
        }

        if (!ability.isActive()) {
            throw new RuntimeException("Эту способность сейчас нельзя использовать");
        }

        ability.apply(defensible);

        GameMaster.getInstance()
                .switchOff(ability, ability.getCoolDown());

        exhaustion(ability.getCost());

        return ability.toDto();
    }
}
