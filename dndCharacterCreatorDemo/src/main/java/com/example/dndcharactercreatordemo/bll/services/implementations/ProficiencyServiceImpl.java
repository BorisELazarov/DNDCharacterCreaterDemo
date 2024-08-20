package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyServiceImpl implements ProficiencyService {
    private static final String NOT_FOUND_MESSAGE="The proficiency is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already proficiency named like that!";
    private final ProficiencyRepo proficiencyRepo;
    private final ProficiencyMapper mapper;

    public ProficiencyServiceImpl(ProficiencyRepo proficiencyRepo, ProficiencyMapper mapper) {
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
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        proficiencyRepo.save(mapper.fromDto(proficiencyDTO));
    }

    @Override
    public ProficiencyDTO getProficiency(Long id) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isPresent()){
            return mapper.toDto(proficiency.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }

    @Override
    public void updateProficiency(Long id, String name, String type) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Proficiency foundProficiency=proficiency.get();
        if (name!=null &&
                name.length()>0 &&
                !name.equals(foundProficiency.getName())) {
            Optional<Proficiency> proficiencyByUsername=proficiencyRepo.findByName(name);
            if (proficiencyByUsername.isPresent() && !proficiencyByUsername.get().getIsDeleted())
            {
                throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
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
            throw new NotFoundException(NOT_FOUND_MESSAGE);
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
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        else {
            Proficiency proficiency=optionalProficiency.get();
            if (proficiency.getIsDeleted()){
                proficiencyRepo.delete(proficiency);
            } else {
                throw new NotSoftDeletedException("The proficiency must be soft deleted first!");
            }
        }
    }
}
