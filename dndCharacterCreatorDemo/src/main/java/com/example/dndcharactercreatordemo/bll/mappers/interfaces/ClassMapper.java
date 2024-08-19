package com.example.dndcharactercreatordemo.bll.mappers.interfaces;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;

public interface ClassMapper extends ISingleParameterMapper<ClassDTO, DNDclass> {
}
