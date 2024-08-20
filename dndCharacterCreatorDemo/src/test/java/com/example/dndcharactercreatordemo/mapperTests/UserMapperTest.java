package com.example.dndcharactercreatordemo.mapperTests;
import com.example.dndcharactercreatordemo.bll.dtos.users.UserDTO;
import com.example.dndcharactercreatordemo.bll.mappers.implementations.UserMapperImpl;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.UserMapper;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper mapper=new UserMapperImpl();

    @Test
    void fromDtoAreEquals() {
        UserDTO dto=new UserDTO(Optional.of(1L),false,
                "username", "password",
                "user");
        Role role=new Role();
        role.setTitle("user");
        User entity= mapper.fromDto(dto, Optional.of(role));
        dto.id().ifPresent(id->assertEquals(id,entity.getId()));
        assertEquals(dto.isDeleted(),entity.getIsDeleted());
        assertEquals(dto.username(),entity.getUsername());
        assertEquals(dto.password(),entity.getPassword());
        assertEquals(dto.role(),role.getTitle());
    }

    @Test
    void toDtoAreEquals() {
        User entity=new User();
        entity.setId(4L);
        entity.setIsDeleted(true);
        entity.setUsername("Gosho");
        entity.setPassword("Ot edno do osem");
        Role role=new Role();
        role.setTitle("admin");
        entity.setRole(role);
        UserDTO dto=mapper.toDto(entity);
        dto.id().ifPresent(id->assertEquals(entity.getId(),id));
        assertEquals(entity.getIsDeleted(),dto.isDeleted());
        assertEquals(entity.getUsername(),dto.username());
        assertEquals(entity.getPassword(),dto.password());
        assertEquals(role.getTitle(),dto.role());
    }
}