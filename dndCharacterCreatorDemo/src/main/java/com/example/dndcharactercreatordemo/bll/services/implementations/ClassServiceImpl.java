package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ClassMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ClassService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassServiceImpl implements ClassService {
    private static final String NOT_FOUND_MESSAGE="The class is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already class named like that!";
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final ClassMapper mapper;

    public ClassServiceImpl(@NotNull ClassRepo classRepo, @NotNull ProficiencyRepo proficiencyRepo,
                            @NotNull ClassMapper mapper) {
        this.classRepo = classRepo;
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }

    @PostConstruct
    private void seedData(){
        if (proficiencyRepo.count()>0 || classRepo.count()>0)
            return;

        seedProficiencies();
        seedClasses(new HashSet<>(proficiencyRepo.findAll()));
    }

    private void seedClasses(Set<Proficiency> proficiencies){
        Set<DNDclass> dnDclasses=new LinkedHashSet<>();
        dnDclasses.add(getDNDclass("Fighter", "Fights", HitDiceEnum.D10, proficiencies));
        dnDclasses.add(getDNDclass("Wizard", "Magic", HitDiceEnum.D6, proficiencies));
        dnDclasses.add(getDNDclass("Rogue", "Sneak", HitDiceEnum.D8, proficiencies));
        dnDclasses.add(getDNDclass("Barbarian", "Tank", HitDiceEnum.D12, proficiencies));
        classRepo.saveAll(dnDclasses);
    }

    private DNDclass getDNDclass(String name, String description, HitDiceEnum hitDiceEnum,
                                 Set<Proficiency> proficiencies){
        DNDclass dnDclass = new DNDclass();
        dnDclass.setName(name);
        dnDclass.setHitDice(hitDiceEnum);
        dnDclass.setProficiencies(proficiencies);
        dnDclass.setDescription(description);
        return dnDclass;
    }

    private void seedProficiencies(){
        if (proficiencyRepo.count()>0)
            return;
        String type="Language";
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(getProficiency("Common",type));
        proficiencies.add(getProficiency("Elven",type));
        proficiencies.add(getProficiency("Dwarvish",type));
        proficiencies.add(getProficiency("Orcish",type));
        proficiencies.add(getProficiency("Celestial", type));
        proficiencies.add(getProficiency("Infernal",type));
        type="Skill";
        proficiencies.add(getProficiency("Athletics",type));
        proficiencies.add(getProficiency("Acrobatics",type));
        proficiencies.add(getProficiency("Arcana",type));
        proficiencies.add(getProficiency("Sleight of hand",type));
        proficiencies.add(getProficiency("Religion", type));
        proficiencies.add(getProficiency("Perception",type));
        proficiencyRepo.saveAll(proficiencies);
    }

    private Proficiency getProficiency(String name, String type){
        Proficiency proficiency=new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }

    @Override
    public List<ClassDTO> getClasses(boolean isDeleted) {
        return mapper.toDTOs(classRepo.findAll(isDeleted));
    }

    @Override
    public ClassDTO addClass(ClassDTO classDTO) {
        if (classRepo.existsByName(classDTO.name())) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        DNDclass dndClass = mapper.fromDto(classDTO);
        proficiencyRepo.saveAll(dndClass.getProficiencies());
        return mapper.toDto(classRepo.save(dndClass));
    }

    @Override
    @Transactional
    public ClassDTO updateClass(ClassDTO classDTO) {
        if (classDTO.id().isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        classDTO.id().ifPresent(id->{
            Optional<DNDclass> dnDclass=classRepo.findById(id);
            if (dnDclass.isPresent()){
                classRepo.findByName(classDTO.name()).ifPresent(
                        x->{
                            if (!x.getId().equals(id)){
                                throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                            }
                        }
                );
                classRepo.save(dnDclass.get());
            }   else {
                throw new NotFoundException(NOT_FOUND_MESSAGE);
            }
        });
        return classDTO;
    }

    @Override
    public void restoreClass(Long id) {
        Optional<DNDclass> optionalDNDclass=classRepo.findById(id);
        if (optionalDNDclass.isPresent()){
            DNDclass dnDclass= optionalDNDclass.get();
            proficiencyRepo.findByName(dnDclass.getName())
                    .ifPresent(x->{ throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);});
            dnDclass.setIsDeleted(false);
            classRepo.save(dnDclass);
        }
        else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {

            DNDclass dndClass = optionalClass.get();
            dndClass.setIsDeleted(true);
            classRepo.save(dndClass);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void hardDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {
            DNDclass foundClass = optionalClass.get();
            if (foundClass.getIsDeleted()) {
                classRepo.delete(foundClass);
            } else {
                throw new NotSoftDeletedException("The class must be soft deleted first!");
            }
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public ClassDTO getClassById(Long id) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isPresent())
        {
            return mapper.toDto(dndClass.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}
