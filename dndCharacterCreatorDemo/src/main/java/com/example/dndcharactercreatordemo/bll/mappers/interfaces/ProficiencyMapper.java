package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

public interface ProficiencyMapper extends ISingleParameterMapper<ProficiencyDTO, Proficiency> {
}
