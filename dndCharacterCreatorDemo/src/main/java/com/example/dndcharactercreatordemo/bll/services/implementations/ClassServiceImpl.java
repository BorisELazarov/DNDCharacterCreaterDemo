package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ClassMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ClassService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService {
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
        seedProficiencies();
        if (classRepo.count()==0){
            DNDclass dnDclass=new DNDclass();
            dnDclass.setName("Fighter");
            dnDclass.setHitDice(HitDiceEnum.D10);
            dnDclass.setDescription("Likes to fight a lot");
            Optional<Proficiency> proficiency=proficiencyRepo.findByName("Athletics");
            if (proficiency.isPresent()) {
                dnDclass.setProficiencies(Set.of(proficiency.get()));
            }
            else {
                dnDclass.setProficiencies(
                        Set.of(
                                getProficiency("Athletics","Skill")
                        )
                );
            }
        }
    }

    private void seedProficiencies(){
        if (proficiencyRepo.count()>0)
            return;
        String language="Language";
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(getProficiency("Common",language));
        proficiencies.add(getProficiency("Elven",language));
        proficiencies.add(getProficiency("Dwarvish",language));
        proficiencies.add(getProficiency("Orcish",language));
        proficiencies.add(getProficiency("Celestial", language));
        proficiencies.add(getProficiency("Infernal",language));
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DNDclass foundDNDClass = dndClass.get();
        if (name != null &&
                name.length() > 0 &&
                !name.equals(foundDNDClass.getName())){
            if (classRepo.existsByName(name)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ClassDTO> getClass(Long id) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        return dndClass.map(dnDclass -> new ResponseEntity<>(mapper.toDto(dnDclass),
                HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        ));
    }
}
