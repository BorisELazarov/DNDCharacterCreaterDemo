package com.example.dndCharacterCreatorDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
@Table
public class Character extends BaseEntity{
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne
    @JoinColumn(name="classId")
    private DNDclass dndClass;
    @Column
    private int level;
    @Column
    private int baseStr;
    @Column
    private int baseDex;
    @Column
    private int baseCon;
    @Column
    private int baseInt;
    @Column
    private int baseWis;
    @Column
    private int baseCha;

    @OneToMany(mappedBy = "character")
    private Set<ProficiencyCharacter> proficiencyCharacters;

    @OneToMany(mappedBy = "character")
    private Set<SpellCharacter> spellCharacters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DNDclass getDNDclassId() {
        return dndClass;
    }

    public void setDNDclassId(DNDclass dndClass) {
        this.dndClass = dndClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBaseStr() {
        return baseStr;
    }

    public void setBaseStr(int baseStr) {
        this.baseStr = baseStr;
    }

    public int getBaseDex() {
        return baseDex;
    }

    public void setBaseDex(int baseDex) {
        this.baseDex = baseDex;
    }

    public int getBaseCon() {
        return baseCon;
    }

    public void setBaseCon(int baseCon) {
        this.baseCon = baseCon;
    }

    public int getBaseInt() {
        return baseInt;
    }

    public void setBaseInt(int baseInt) {
        this.baseInt = baseInt;
    }

    public int getBaseWis() {
        return baseWis;
    }

    public void setBaseWis(int baseWis) {
        this.baseWis = baseWis;
    }

    public int getBaseCha() {
        return baseCha;
    }

    public void setBaseCha(int baseCha) {
        this.baseCha = baseCha;
    }
}