package com.example.dndCharacterCreatorDemo.dal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
@Table(name = "characters")
public class Character extends BaseEntity{
    @Column(name="name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="class_id")
    private DNDclass dndClass;
    @Column(name="level", nullable = false)
    private int level;
    @Column(name = "base_str", nullable = false)
    private int baseStr=10;
    @Column(name = "base_dex", nullable = false)
    private int baseDex=10;
    @Column(name = "base_con", nullable = false)
    private int baseCon=10;
    @Column(name = "base_int", nullable = false)
    private int baseInt=10;
    @Column(name = "base_wis", nullable = false)
    private int baseWis=10;
    @Column(name = "base_cha", nullable = false)
    private int baseCha=10;

    @OneToMany(mappedBy = "character")
    private Set<ProficiencyCharacter> proficiencyCharacters;

    @ManyToMany(mappedBy = "characters")
    private Set<Spell> spells;

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