package com.example.dndCharacterCreatorDemo.repos;

import com.example.dndCharacterCreatorDemo.entities.DNDclass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<DNDclass,Long> {
}
