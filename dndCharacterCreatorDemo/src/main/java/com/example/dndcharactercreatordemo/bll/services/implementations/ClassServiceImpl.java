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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {
    private static final String NOT_FOUND_MESSAGE="The class is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already class named like that!";
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final ClassMapper mapper;

    public ClassServiceImpl(ClassRepo classRepo, ProficiencyRepo proficiencyRepo, ClassMapper mapper) {
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
    public ResponseEntity<List<ClassDTO>> getClasses() {
        return new ResponseEntity<>( mapper.toDTOs(classRepo.findAll()),
                HttpStatus.OK
                );
    }

    @Override
    public ResponseEntity<Void> addClass(ClassDTO classDTO) {
        if (classRepo.existsByName(classDTO.name())) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        DNDclass dndClass = mapper.fromDto(classDTO);
        proficiencyRepo.saveAll(dndClass.getProficiencies());
        classRepo.save(dndClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> updateClass(Long id, String name, String description, HitDiceEnum hitDice) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        DNDclass foundDNDClass = dndClass.get();
        if (name != null &&
                name.length() > 0 &&
                !name.equals(foundDNDClass.getName())){
            if (classRepo.existsByName(name)) {
                throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
            }
            foundDNDClass.setName(name);
        }
        if (description != null &&
                description.length() > 0 &&
                !description.equals(foundDNDClass.getDescription())) {
            foundDNDClass.setDescription(description);
        }
        if (hitDice != null && !hitDice.equals(foundDNDClass.getHitDice())) {
            foundDNDClass.setHitDice(hitDice);
        }
        classRepo.save(foundDNDClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> softDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {

            DNDclass dndClass = optionalClass.get();
            dndClass.setIsDeleted(true);
            classRepo.save(dndClass);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public ResponseEntity<Void> hardDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {
            DNDclass foundClass = optionalClass.get();
            if (foundClass.getIsDeleted()) {
                classRepo.delete(foundClass);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new NotSoftDeletedException("The class must be soft deleted first!");
            }
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public ResponseEntity<ClassDTO> getClass(Long id) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isPresent())
        {
            return new ResponseEntity<>(
                    mapper.toDto(dndClass.get()),
                    HttpStatus.OK
            );
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}
