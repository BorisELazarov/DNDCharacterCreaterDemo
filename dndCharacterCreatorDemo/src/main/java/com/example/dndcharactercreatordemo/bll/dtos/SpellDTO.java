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
}
