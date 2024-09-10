package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.ProficiencyService;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
    @PersistenceContext
    private EntityManager em;
    public ProficiencyServiceImpl(@NotNull ProficiencyRepo proficiencyRepo, @NotNull ProficiencyMapper mapper) {
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }

    @Override
    public List<ProficiencyDTO> getProficiencies(boolean isDeleted,
                                                 Optional<String> name,
                                                 Optional<String> type,
                                                 Optional<String> sortBy,
                                                 boolean ascending) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Proficiency> criteriaQuery= cb.createQuery(Proficiency.class);
        Root<Proficiency> root= criteriaQuery.from(Proficiency.class);
        String nameParam= name.orElse("");
        String typeParam= type.orElse("");
        criteriaQuery.select(root)
                .where(cb.and(cb.and(
                        cb.equal(root.get("isDeleted"),isDeleted),
                        cb.like(root.get("name"),"%"+nameParam+"%")),
                        cb.like(root.get("type"),"%"+typeParam+"%")
                ));
        if (ascending){
            criteriaQuery.orderBy(cb.asc(root.get(sortBy.orElse("id"))));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy.orElse("id"))));
        }
        Query query = em.createQuery(criteriaQuery);
        List<Proficiency> proficiencies=query.getResultList();

        return mapper.toDTOs(proficiencies);
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
        Optional<Long> optionalId=proficiencyDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id= optionalId.get();
        Optional<Proficiency> proficiency = proficiencyRepo.findById(id);
        if (proficiency.isPresent()) {
            proficiencyRepo.findByName(proficiencyDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            proficiencyRepo.save(proficiency.get());
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
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
