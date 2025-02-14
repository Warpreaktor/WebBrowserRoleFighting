package fight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Информация о действиях, которые произошли на фазе защиты.
 * Какой урон понёс персонаж и сообщение о том, какие действия были предприняты.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefenseDto {

    double pain;

    String message;
}
