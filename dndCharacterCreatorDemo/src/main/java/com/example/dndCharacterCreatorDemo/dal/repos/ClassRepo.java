package com.example.dndCharacterCreatorDemo.dal.repos;

import com.example.dndCharacterCreatorDemo.dal.entities.DNDclass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<DNDclass,Long> {
}
