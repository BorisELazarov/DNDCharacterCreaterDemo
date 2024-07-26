package com.example.dndCharacterCreatorDemo.dal.repos;

import com.example.dndCharacterCreatorDemo.dal.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

}
