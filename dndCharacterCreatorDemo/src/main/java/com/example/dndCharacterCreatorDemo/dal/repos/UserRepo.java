package com.example.dndCharacterCreatorDemo.dal.repos;

import com.example.dndCharacterCreatorDemo.dal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
