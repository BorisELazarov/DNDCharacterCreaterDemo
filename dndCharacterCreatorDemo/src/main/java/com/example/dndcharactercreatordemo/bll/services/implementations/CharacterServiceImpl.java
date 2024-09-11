package com.example.dndcharactercreatordemo.bll.services.implementations;

import com.example.dndcharactercreatordemo.bll.dtos.characters.CharacterDTO;
import com.example.dndcharactercreatordemo.bll.mappers.interfaces.CharacterMapper;
import com.example.dndcharactercreatordemo.bll.services.interfaces.CharacterService;
import com.example.dndcharactercreatordemo.dal.entities.Character;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.CharacterRepo;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.exceptions.customs.NameAlreadyTakenException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotFoundException;
import com.example.dndcharactercreatordemo.exceptions.customs.NotSoftDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String NOT_FOUND_MESSAGE = "The character is not found!";
    private static final String NAME_TAKEN_MESSAGE = "There is already character named like that!";
    private final RoleRepo roleRepo;
    private final CharacterRepo characterRepo;
    private final CharacterMapper mapper;
    @PersistenceContext
    private EntityManager em;

    public CharacterServiceImpl(@NotNull CharacterRepo characterRepo,
                                @NotNull CharacterMapper mapper,
                                @NotNull RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterDTO> getCharacters(Long userId,
                                            boolean isDeleted,
                                            Optional<String> name,
                                            Optional<Byte> level,
                                            Optional<String> className,
                                            Optional<String> sortBy,
                                            boolean ascending) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Character> criteriaQuery= cb.createQuery(Character.class);
        Root<Character> root= criteriaQuery.from(Character.class);
        String nameParam= name.orElse("");
        String classNameParam= className.orElse("");
        Byte levelParam= level.orElse((byte)0);
        Join<Character, DNDclass> joinClasses= root.join("dndClass", JoinType.INNER);
        Join<Character, User> joinUsers= root.join("user", JoinType.INNER);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                        cb.like(root.get("name"),"%"+nameParam+"%"),
                        cb.like(joinClasses.get("name"),"%"+classNameParam+"%")
                        ),
                        cb.and(
                                cb.or(
                                        cb.isTrue(cb.literal(levelParam==0)),
                                        cb.equal(root.get("level"),levelParam)
                                ),
                                cb.equal(joinUsers.get("id"),userId)
                        )
                ));
        String sortByParam= sortBy.orElse("id");
        if (sortByParam.isEmpty()){
            sortByParam="id";
        }
        if (ascending){
            criteriaQuery.orderBy(cb.asc(root.get(sortByParam)));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortByParam)));
        }
        Query query = em.createQuery(criteriaQuery);
        List<Character> characters=query.getResultList();
        return mapper.toDTOs(characters);
    }

    @Override
    public CharacterDTO addCharacter(CharacterDTO dcharacterDTO) {
        if (characterRepo.findByName(dcharacterDTO.name()).isPresent())
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        Character character = characterRepo.save(mapper.fromDto(
                dcharacterDTO,
                roleRepo.findByTitle(dcharacterDTO.user().role())
        ));
        return mapper.toDto(character);
    }

    @Override
    @Transactional
    public CharacterDTO editCharacter(CharacterDTO characterDTO) {
        Optional<Long> optionalId = characterDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id = optionalId.get();
        Optional<Character> character = characterRepo.findById(id);
        if (character.isPresent()) {
            characterRepo.findByName(characterDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            characterRepo.save(
                    mapper.fromDto(characterDTO,
                            Optional.of(character.get().getUser().getRole())
                    ));
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return characterDTO;
    }

    @Override
    public void restoreCharacter(Long id) {
        Optional<Character> optionalCharacter = characterRepo.findById(id);
        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            characterRepo.findByName(character.getName())
                    .ifPresent(x -> {
                        throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                    });
            character.setIsDeleted(false);
            characterRepo.save(character);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        } else {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
        }
    }

    @Override
    public void hardDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);

        } else {
            Character foundCharacter = character.get();
            if (foundCharacter.getIsDeleted()) {
                characterRepo.delete(foundCharacter);
            } else {
                throw new NotSoftDeletedException("The character must be soft deleted first!");
            }
        }
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isPresent()) {
            return mapper.toDto(character.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }


}
