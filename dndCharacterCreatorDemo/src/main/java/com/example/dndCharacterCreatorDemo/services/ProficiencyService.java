package com.example.dndCharacterCreatorDemo.services;

import com.example.dndCharacterCreatorDemo.entities.BaseEntity;
import com.example.dndCharacterCreatorDemo.entities.Proficiency;
import com.example.dndCharacterCreatorDemo.repos.ProficiencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyService extends BaseEntity {
    private final ProficiencyRepo proficiencyRepo;

    @Autowired
    public ProficiencyService(ProficiencyRepo proficiencyRepo)
    {
        this.proficiencyRepo=proficiencyRepo;
    }

    public List<Proficiency> getProficiencies() {
        return proficiencyRepo.findAll();
    }

    public void addProficiency(Proficiency proficiency) {
        Optional<Proficiency> proficiencyByName=proficiencyRepo.findAll()
                .stream()
                .filter(x->x.getName().equals(proficiency.getName()))
                .findFirst();
        if (proficiencyByName.isPresent()) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        proficiencyRepo.save(proficiency);
    }

    public Proficiency getProficiency(Long id) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (!proficiency.isPresent()) {
            proficiencyNotFound();
        }
        return proficiency.get();
    }
    
    
    
    private void proficiencyNotFound(){
        throw new IllegalArgumentException("Proficiency not found!");
    }

    public void updateProficiency(Long id, String name, String type) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (!proficiency.isPresent()) {
            proficiencyNotFound();
        }
        Proficiency foundProficiency=proficiency.get();
        if (name!=null &&
                name.length()>0 &&
                !name.equals(foundProficiency.getName())) {
            Optional<Proficiency> proficiencyByUsername=proficiencyRepo.findAll()
                    .stream()
                    .filter(x->x.getName().equals(name))
                    .findFirst();
            if(proficiencyByUsername.isPresent())
            {
                throw new IllegalArgumentException("Error: there is already proficiency with such name");
            }
            foundProficiency.setName(name);
        }
        if (type!=null &&
                type.length()>0 &&
                !type.equals(foundProficiency.getType())) {
            foundProficiency.setType(type);
        }
        proficiencyRepo.save(foundProficiency);
    }

    public void deleteProficiency(Long id) {
        if (!proficiencyRepo.existsById(id))
        {
            proficiencyNotFound();
        }
        Proficiency proficiency=proficiencyRepo.findById(id).get();
        proficiency.setDeleted(true);
        proficiencyRepo.save(proficiency);
    }
}
