package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.spells.SearchSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.SpellMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.SpellService;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SpellServiceImpl implements SpellService {
    private static final String NOT_FOUND_MESSAGE = "The spell is not found!";
    private static final String NAME_TAKEN_MESSAGE = "There is already spell with that name!";
    private final SpellRepo spellRepo;
    private final SpellMapper mapper;
    @PersistenceContext
    private EntityManager em;

    public SpellServiceImpl(@NotNull SpellRepo spellRepo, @NotNull SpellMapper mapper) {
        this.spellRepo = spellRepo;
        this.mapper = mapper;
    }

    @PostConstruct
    private void seedSpells() {
        if (spellRepo.count() > 0)
            return;
        Set<Spell> spells = new LinkedHashSet<>();
        spells.add(
                getSpell(
                        false, "Fire",
                        1, "A",
                        20, "asfd",
                        "V, S, M", 0,
                        "FIreee"
                )
        );
        spells.add(
                getSpell(
                        true, "Cold",
                        1, "A",
                        25, "ads",
                        "V, M", 0,
                        "Cold cold"
                )
        );
        spells.add(
                getSpell(
                        false, "Thunder",
                        2, "BA",
                        20, "asfd",
                        "V, M", 60,
                        "Thunder"
                )
        );
        spellRepo.saveAll(spells);
    }

    private Spell getSpell(Boolean isDeleted, String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description) {
        Spell spell = new Spell();
        spell.setIsDeleted(isDeleted);
        spell.setName(name);
        spell.setDescription(description);
        spell.setLevel(level);
        spell.setCastingRange(castingRange);
        spell.setTarget(target);
        spell.setComponents(components);
        spell.setDuration(duration);
        spell.setCastingTime(castingTime);
        return spell;
    }

    @Override
    public List<SpellDTO> getSpellsUnfiltered() {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Spell> criteriaQuery= cb.createQuery(Spell.class);
        Root<Spell> root= criteriaQuery.from(Spell.class);
        criteriaQuery.select(root)
                .where(cb.equal(root.get("isDeleted"),false));
        TypedQuery<Spell> query = em.createQuery(criteriaQuery);
        List<Spell> spells=query.getResultList();
        return mapper.toDTOs(spells);
    }

    @Override
    public List<SpellDTO> getSpells(boolean isDeleted, SearchSpellDTO searchSpellDTO) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Spell> criteriaQuery= cb.createQuery(Spell.class);
        Root<Spell> root= criteriaQuery.from(Spell.class);
        byte level= searchSpellDTO.filter().level().orElse((byte) -1);
        int range= searchSpellDTO.filter().range().orElse(-1);
        final String castingTimeName="castingTime";
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                                cb.and(
                                        cb.equal(root.get("isDeleted"),isDeleted),
                                        cb.like(root.get("name"),cb.parameter(String.class,"name"))
                                ),
                                cb.like(root.get(castingTimeName),cb.parameter(String.class,castingTimeName))
                        ),
                        cb.and(
                                cb.or(
                                        cb.isTrue(cb.literal(level<0||level>10)),
                                        cb.equal(root.get("level"),level)
                                ),
                                cb.or(
                                        cb.isTrue(cb.literal(range<0)),
                                        cb.equal(root.get("castingRange"),range)
                                )
                        )
                ));
        String sortByParam= searchSpellDTO.sort().sortBy();
        if (sortByParam.isEmpty()){
            sortByParam="id";
        }
        if (searchSpellDTO.sort().ascending()){
            criteriaQuery.orderBy(cb.asc(root.get(sortByParam)));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortByParam)));
        }
        TypedQuery<Spell> query = em.createQuery(criteriaQuery);
        query.setParameter("name","%"+searchSpellDTO.filter().name()+"%");
        query.setParameter(castingTimeName,"%"+searchSpellDTO.filter().castingTime()+"%");
        List<Spell> spells=query.getResultList();
        return mapper.toDTOs(spells);
    }

    @Override
    public SpellDTO getSpell(Long id) {
        Optional<Spell> spell = spellRepo.findById(id);
        if (spell.isEmpty())
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        return mapper.toDto(spell.get());
    }

    @Override
    public SpellDTO addSpell(SpellDTO spellDTO) {
        Optional<Spell> spell = spellRepo.findByName(spellDTO.name());
        if (spell.isPresent()) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        return mapper.toDto(spellRepo.save(mapper.fromDto(spellDTO)));
    }

    @Override
    public SpellDTO editSpell(SpellDTO spellDTO) {
        Optional<Long> optionalId = spellDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id = optionalId.get();
        if (spellRepo.existsById(id)) {
            spellRepo.findByName(spellDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Spell spell = spellRepo.save(mapper.fromDto(spellDTO));
        return mapper.toDto(spell);
    }

    @Override
    public void restoreSpell(Long id) {
        Optional<Spell> optionalSpell = spellRepo.findById(id);
        if (optionalSpell.isPresent()) {
            Spell spell = optionalSpell.get();
            spellRepo.findByName(spell.getName())
                    .ifPresent(x -> {
                        throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                    });
            spell.setIsDeleted(false);
            spellRepo.save(spell);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteSpell(Long id) {
        Optional<Spell> optionalSpell = spellRepo.findById(id);
        if (optionalSpell.isPresent()) {
            Spell spell = optionalSpell.get();
            spell.setIsDeleted(true);
            spellRepo.save(spell);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void hardDeleteSpell(Long id) {
        Optional<Spell> optionalSpell = spellRepo.findById(id);
        if (optionalSpell.isPresent()) {
            Spell spell = optionalSpell.get();
            if (spell.getIsDeleted()) {
                spellRepo.delete(spell);
                return;
            }
            throw new NotSoftDeletedException("The spell must be soft deleted first!");
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}
