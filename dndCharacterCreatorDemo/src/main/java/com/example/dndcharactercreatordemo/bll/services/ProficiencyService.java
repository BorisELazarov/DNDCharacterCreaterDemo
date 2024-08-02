package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.mappers.ProficiencyMapper;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return mapper.toDtos(proficiencyRepo.findAll());
    }

    public void addProficiency(ProficiencyDTO proficiencyDTO) {
        Proficiency proficiencyByName=proficiencyRepo.findByName(proficiencyDTO.getName());
        if (!(proficiencyByName==null || proficiencyByName.getIsDeleted())) {
            throw new IllegalArgumentException("Error: there is already proficiency with such name!");
        }
        proficiencyRepo.save(mapper.fromDto(proficiencyDTO));
    }

    public ProficiencyDTO getProficiency(Long id) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isEmpty()) {
            proficiencyNotFound();
        }
        return mapper.toDto(proficiency.get());
    }
    
    
    
    private void proficiencyNotFound(){
        throw new IllegalArgumentException("Proficiency not found!");
    }

    public void updateProficiency(Long id, String name, String type) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isEmpty()) {
            proficiencyNotFound();
        }
        Proficiency foundProficiency=proficiency.get();
        if (name!=null &&
                name.length()>0 &&
                !name.equals(foundProficiency.getName())) {
            Proficiency proficiencyByUsername=proficiencyRepo.findByName(name);
            if (!(proficiencyByUsername==null || proficiencyByUsername.getIsDeleted()))
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
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isEmpty()){
            proficiencyNotFound();
        }
        else {
            Proficiency proficiency=optionalProficiency.get();
            proficiency.setIsDeleted(true);
            proficiencyRepo.save(proficiency);
        }
    }
}
