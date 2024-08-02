package com.example.dndcharactercreatordemo.bll.dtos;

public class SpellDTO extends BaseDTO{
    private String name;
    private int level;
    private String castingTime;
    private int castingRange;
    private String target;
    private String components;
    private int duration;
    private String description;

    public SpellDTO(Long id, Boolean isDeleted,
                    String name, int level, String castingTime,
                    int castingRange, String target, String components,
                    int duration, String description) {
        super(id, isDeleted);
        this.name = name;
        this.level = level;
        this.castingTime = castingTime;
        this.castingRange = castingRange;
        this.target = target;
        this.components = components;
        this.duration = duration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public int getCastingRange() {
        return castingRange;
    }

    public String getTarget() {
        return target;
    }

    public String getComponents() {
        return components;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
}
