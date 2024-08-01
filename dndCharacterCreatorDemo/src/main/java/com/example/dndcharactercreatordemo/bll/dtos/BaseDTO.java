package com.example.dndcharactercreatordemo.bll.dtos;

public abstract class BaseDTO {
    protected Long id;
    protected boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
