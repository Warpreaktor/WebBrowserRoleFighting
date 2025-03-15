package mechanic.interfaces;

import core.GameMaster;
import dto.ability.AbilityDto;
import fight.FightService;
import hero.Hero;
import ability.Ability;
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
     * @param abilityName идентификатор способности
     * @param defensible цель применяемой способности
     */
    default AbilityDto useAbility(String abilityName, Hero defensible) {
        var ability = getAbilities()
                .stream()
                .filter(ab -> Objects.equals(ab.getName(), abilityName))
                .findFirst()
                .orElseThrow();

        if (getEndurance().getValue() < ability.getCost()) {
            FightService.getInstance().getResult().addEventAndLog("Не хватает выносливости");
            throw new RuntimeException("Не хватает выносливости");
        }

        if (!ability.isActive()) {
            FightService.getInstance().getResult().addEventAndLog("Эту способность сейчас нельзя использовать");
            throw new RuntimeException("Эту способность сейчас нельзя использовать");
        }

        ability.apply(defensible);

        return ability.toDto();
    }
}
