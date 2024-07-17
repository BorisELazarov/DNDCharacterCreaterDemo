package com.example.dndCharacterCreatorDemo.bll.dtos;

public abstract class BaseDTO {
    protected final Long id;
    protected boolean isDeleted;

    public BaseDTO() {
        this.id = null;
    }

    public BaseDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
