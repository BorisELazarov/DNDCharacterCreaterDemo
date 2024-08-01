package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "spells")
public class Spell extends BaseEntity{
    @Column(name="name", nullable = false)
    @NotBlank(message = "Name must not be empty")
    @Size(max=50, message = "Name must have maximum 50 characters")
    private String name;
    @Column(name="level", nullable = false)
    @Min(value = 0, message = "Level must be at least 0")
    @Max(value = 9, message = "The maximum level is 9")
    private int level;
    @Column(name="casting_time", nullable = false)
    @NotBlank(message = "Casting time must not be empty")
    @Size(max=50, message = "Casting time must have maximum 50 characters")
    private String castingTime;
    @Column(name="casting_range")
    @Min(value = 0, message = "Casting range must be at least 0 feet")
    private int castingRange;
    @Column(name="target")
    @NotBlank(message = "Target must not be empty")
    @Size(max=50, message = "Target must have maximum 50 characters")
    private String target;
    @Column(name="components", nullable = false)
    @NotBlank(message = "The components must not be empty")
    @Size(max=50, message = "The components must have maximum 50 characters")
    private String components;
    @Column(name="duration", nullable = false)
    @Min(value = 0, message = "Duration must be at least 0")
    private int duration;
    @Column(name="description", nullable = false)
    @NotBlank(message = "Description must not be empty")
    @Size(max=65535, message = "The description must have maximum 65 535 characters")
    private String description;


    public Spell() {
    }

    public Spell(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public int getCastingRange() {
        return castingRange;
    }

    public void setCastingRange(int castingRange) {
        this.castingRange = castingRange;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return level == spell.level && castingRange == spell.castingRange && duration == spell.duration
                && Objects.equals(name, spell.name) && Objects.equals(castingTime, spell.castingTime)
                && Objects.equals(target, spell.target) && Objects.equals(components, spell.components)
                && Objects.equals(description, spell.description)
                && Objects.equals(isDeleted, spell.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, castingTime,
                castingRange, target, components,
                duration, description, isDeleted);
    }
}
