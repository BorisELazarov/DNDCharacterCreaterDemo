package com.example.dndCharacterCreatorDemo.bll.services;

import com.example.dndCharacterCreatorDemo.bll.dtos.ClassDTO;
import com.example.dndCharacterCreatorDemo.bll.mappers.ClassMapper;
import com.example.dndCharacterCreatorDemo.bll.mappers.IMapper;
import com.example.dndCharacterCreatorDemo.dal.entities.DNDclass;
import com.example.dndCharacterCreatorDemo.dal.repos.ClassRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    private final ClassRepo classRepo;
    private final IMapper<ClassDTO,DNDclass> mapper;

    @Autowired
    public ClassService(ClassRepo classRepo) {
        this.classRepo = classRepo;
        mapper=new ClassMapper();
    }

    public List<ClassDTO> getClasses() {
        return mapper.toDtos(classRepo.findAll());
    }

    public void addClass(ClassDTO classDTO) {
        Optional<DNDclass> dndClassByUsername= classRepo.findAll()
                .stream()
                .filter(x->x.getName().equals(classDTO.getName()))
                .findFirst();
        if (dndClassByUsername.isPresent()) {
            throw new IllegalArgumentException("Error: there is already dndClass with such name");
        }
        classRepo.save(mapper.fromDto(classDTO));
    }

    @Transactional
    public void updateClass(Long id, String username, String description, Integer hitDice) {
        Optional<DNDclass> dndClass= classRepo.findById(id);
        if (!dndClass.isPresent()) {
            dndClassNotFound();
        }
        DNDclass foundDNDClass=dndClass.get();
        if (username!=null &&
                username.length()>0 &&
                !username.equals(foundDNDClass.getName())) {
            Optional<DNDclass> userByUsername= classRepo.findAll()
                    .stream()
                    .filter(x->x.getName().equals(username))
                    .findFirst();
            if(userByUsername.isPresent())
            {
                throw new IllegalArgumentException("Error: there is already dndClass with such name");
            }
            foundDNDClass.setName(username);
        }
        if (description!=null &&
                description.length()>0 &&
                !description.equals(foundDNDClass.getDescription())) {
            foundDNDClass.setDescription(description);
        }
        if (hitDice!=null && hitDice>0)
        {
            foundDNDClass.setHitDice(hitDice);
        }
        classRepo.save(foundDNDClass);
    }

    public void deleteClass(Long id) {
        if (!classRepo.existsById(id)){
            dndClassNotFound();
        }
        DNDclass dndClass= classRepo.findById(id).get();
        dndClass.setIsDeleted(true);
        classRepo.save(dndClass);
    }

    private void dndClassNotFound(){
        throw new IllegalArgumentException("DND class not found!");
    }

    public ClassDTO getClass(Long id) {
        Optional<DNDclass> dndClass= classRepo.findById(id);
        if (!dndClass.isPresent()) {
            dndClassNotFound();
        }
        return mapper.toDto(dndClass.get());
    }
}
