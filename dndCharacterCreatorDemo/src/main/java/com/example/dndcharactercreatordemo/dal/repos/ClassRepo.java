package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassRepo extends JpaRepository<DNDclass,Long> {
    public boolean existsByName(String name);

    @Query("Select c from DNDclass c where c.name=:name and c.isDeleted=false")
    Optional<DNDclass> findByName(String name);
}
