package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.common.Sort;
import com.example.dndcharactercreatordemo.filters.DndClassFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchClassDTO(DndClassFilter filter, Sort sort){
}
