package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.services.interfaces.ClassService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ClassDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService {
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final IMapper<ClassDTO, DNDclass> mapper;

    public ClassServiceImpl(ClassRepo classRepo, ProficiencyRepo proficiencyRepo, IMapper<ClassDTO, DNDclass> mapper) {
        this.classRepo = classRepo;
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }

    @PostConstruct
    private void seedData(){
        seedProficiencies();
        seedClasses();
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

    private void seedClasses(){
        if(classRepo.count()>0)
            return;
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
    public List<ClassDTO> getClasses() {
        return mapper.toDTOs(classRepo.findAll());
    }

    @Override
    public void addClass(ClassDTO classDTO) {
        if (classRepo.existsByName(classDTO.name())) {
            throw new IllegalArgumentException("Error: there is already dndClass with such name");
        }
        DNDclass dndClass = mapper.fromDto(classDTO);
        proficiencyRepo.saveAll(dndClass.getProficiencies());
        classRepo.save(dndClass);
    }

    @Override
    @Transactional
    public void updateClass(Long id, String name, String description, HitDiceEnum hitDice) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isEmpty()) {
            dndClassNotFound();
        }
        DNDclass foundDNDClass = dndClass.get();
        if (name != null &&
                name.length() > 0 &&
                !name.equals(foundDNDClass.getName())){
            if (classRepo.existsByName(name)) {
                throw new IllegalArgumentException("Error: there is already dndClass with such name");
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
    }

    @Override
    public void softDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {

            DNDclass dndClass = optionalClass.get();
            dndClass.setIsDeleted(true);
            classRepo.save(dndClass);
        } else {

            dndClassNotFound();
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
                throw new IllegalArgumentException("The class must be soft deleted before being hard deleted");
            }
        } else {

            dndClassNotFound();
        }
    }

    private void dndClassNotFound() {
        throw new IllegalArgumentException("DND class not found!");
    }

    @Override
    public ClassDTO getClass(Long id) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isEmpty()) {
            dndClassNotFound();
        }
        return mapper.toDto(dndClass.get());
    }
}
