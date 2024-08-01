package com.example.dndcharactercreatordemo.dal.entities;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DNDclassTests {
    @Test
    void equalsOthers(){
        DNDclass dndClass1=new DNDclass();
        dndClass1.setName("Name");
        dndClass1.setDescription("Description");
        dndClass1.setHitDice(HitDiceEnum.D6);
        dndClass1.setIsDeleted(true);
        DNDclass dndClass2=new DNDclass();
        dndClass2.setName("Name");
        dndClass2.setDescription("Description");
        dndClass1.setHitDice(HitDiceEnum.D6);
        dndClass2.setIsDeleted(true);
        assertEquals(dndClass1,dndClass2);
    }
    @Test
    void hash(){
        DNDclass dndClass1=new DNDclass();
        dndClass1.setName("Name");
        dndClass1.setDescription("Description");
        dndClass1.setHitDice(HitDiceEnum.D6);
        dndClass1.setIsDeleted(true);
        assertEquals(dndClass1.hashCode(),
                Objects.hash(dndClass1.getId(), dndClass1.getName(),dndClass1.getDescription(),
                        dndClass1.getHitDice(), dndClass1.getProficiencies(),
                        dndClass1.getClassSpells(), dndClass1.getIsDeleted()
                )
        );
    }
}
