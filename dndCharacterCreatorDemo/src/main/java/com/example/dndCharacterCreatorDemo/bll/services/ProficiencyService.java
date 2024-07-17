package com.example.dndCharacterCreatorDemo.bll.services;

import com.example.dndCharacterCreatorDemo.bll.dtos.ProficiencyDTO;
import com.example.dndCharacterCreatorDemo.bll.mappers.IMapper;
import com.example.dndCharacterCreatorDemo.bll.mappers.ProficiencyMapper;
import com.example.dndCharacterCreatorDemo.dal.entities.Proficiency;
import com.example.dndCharacterCreatorDemo.dal.repos.ProficiencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyService  {
    private final ProficiencyRepo proficiencyRepo;
    private final IMapper<ProficiencyDTO, Proficiency> mapper;

    @Autowired
    public ProficiencyService(ProficiencyRepo proficiencyRepo)
    {
        this.proficiencyRepo=proficiencyRepo;
        mapper=new ProficiencyMapper();
    }

    public List<ProficiencyDTO> getProficiencies() {
        List<Proficiency> proficiencies=proficiencyRepo.findAll();
        List<ProficiencyDTO> proficiencyDTOS=new ArrayList<>();
        for (Proficiency proficiency:proficiencies) {
            proficiencyDTOS.add(mapper.toDto(proficiency));
        }
        return proficiencyDTOS;
    }

    public void addProficiency(ProficiencyDTO proficiencyDTO) {
        Optional<Proficiency> proficiencyByName=proficiencyRepo.findAll()
                .stream()
                .filter(x->x.getName().equals(proficiencyDTO.getName()))
                .findFirst();
        if (proficiencyByName.isPresent()) {
            throw new IllegalArgumentException("Error: there is already user with such name");
        }
        proficiencyRepo.save(mapper.fromDto(proficiencyDTO));
    }

    public ProficiencyDTO getProficiency(Long id) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (!proficiency.isPresent()) {
            proficiencyNotFound();
        }
        return mapper.toDto(proficiency.get());
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
        proficiency.setIsDeleted(true);
        proficiencyRepo.save(proficiency);
    }
}
