package com.example.dndcharactercreatordemo.bll.dtos.users;

import com.example.dndcharactercreatordemo.common.Sort;
import com.example.dndcharactercreatordemo.filters.UserFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchUserDTO(UserFilter filter, Sort sort) {
}
