package com.example.dndcharactercreatordemo.bll.dtos.characters;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;

public record ProficiencyCharacterDTO(ProficiencyDTO proficiency, Boolean expertise) {
}
