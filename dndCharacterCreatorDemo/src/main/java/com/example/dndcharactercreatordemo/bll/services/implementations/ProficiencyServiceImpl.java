package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyServiceImpl implements ProficiencyService {
    private static final String NOT_FOUND_MESSAGE="The proficiency is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already proficiency named like that!";
    private final ProficiencyRepo proficiencyRepo;
    private final ProficiencyMapper mapper;

    public ProficiencyServiceImpl(@NotNull ProficiencyRepo proficiencyRepo, @NotNull ProficiencyMapper mapper) {
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }


    @Override
    public List<ProficiencyDTO> getProficiencies(boolean isDeleted) {
        return mapper.toDTOs(proficiencyRepo.findAll(isDeleted));
    }

    @Override
    public ProficiencyDTO addProficiency(ProficiencyDTO proficiencyDTO) {
        Optional<Proficiency> proficiencyByName=proficiencyRepo.findByName(proficiencyDTO.name());
        if (proficiencyByName.isPresent()) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        return mapper.toDto(proficiencyRepo.save(mapper.fromDto(proficiencyDTO)));
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
    public ProficiencyDTO updateProficiency(ProficiencyDTO proficiencyDTO) {
        if (proficiencyDTO.id().isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        proficiencyDTO.id().ifPresent(id->{
            Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
            if (proficiency.isPresent()){
                proficiencyRepo.findByName(proficiencyDTO.name()).ifPresent(
                        x->{
                            if (!x.getId().equals(id)){
                                throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                            }
                        }
                );
                proficiencyRepo.save(proficiency.get());
            }   else {
                throw new NotFoundException(NOT_FOUND_MESSAGE);
            }
        });
        return proficiencyDTO;
    }

    @Override
    public void restoreProficiency(Long id) {
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isPresent()){
            Proficiency proficiency= optionalProficiency.get();
            proficiencyRepo.findByName(proficiency.getName())
                    .ifPresent(x->{ throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);});
            proficiency.setIsDeleted(false);
            proficiencyRepo.save(proficiency);
        }
        else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
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
