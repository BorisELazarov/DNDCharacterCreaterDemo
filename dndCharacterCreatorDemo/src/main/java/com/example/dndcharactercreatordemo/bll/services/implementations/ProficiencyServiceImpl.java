package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyServiceImpl implements ProficiencyService {
    private final ProficiencyRepo proficiencyRepo;
    private final IMapper<ProficiencyDTO, Proficiency> mapper;

    public ProficiencyServiceImpl(ProficiencyRepo proficiencyRepo, IMapper<ProficiencyDTO, Proficiency> mapper) {
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }


    @Override
    public List<ProficiencyDTO> getProficiencies() {
        return mapper.toDTOs(proficiencyRepo.findAll());
    }

    @Override
    public void addProficiency(ProficiencyDTO proficiencyDTO) {
        Optional<Proficiency> proficiencyByName=proficiencyRepo.findByName(proficiencyDTO.name());
        if (proficiencyByName.isPresent()) {
            throw new IllegalArgumentException("Error: there is already proficiency with such name!");
        }
        proficiencyRepo.save(mapper.fromDto(proficiencyDTO));
    }

    @Override
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

    @Override
    public void updateProficiency(Long id, String name, String type) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isEmpty()) {
            proficiencyNotFound();
        }
        Proficiency foundProficiency=proficiency.get();
        if (name!=null &&
                name.length()>0 &&
                !name.equals(foundProficiency.getName())) {
            Optional<Proficiency> proficiencyByUsername=proficiencyRepo.findByName(name);
            if (proficiencyByUsername.isPresent() && !proficiencyByUsername.get().getIsDeleted())
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

    @Override
    public void softDeleteProficiency(Long id) {
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

    @Override
    public void hardDeleteProficiency(Long id) {
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isEmpty()){
            proficiencyNotFound();
        }
        else {
            Proficiency proficiency=optionalProficiency.get();
            if (proficiency.getIsDeleted()){
                proficiencyRepo.delete(proficiency);
            } else {
                throw new IllegalArgumentException("The proficiency must be soft deleted before being hard deleted");
            }
        }
    }
}
