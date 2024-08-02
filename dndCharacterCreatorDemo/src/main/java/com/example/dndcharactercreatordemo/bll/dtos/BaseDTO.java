package com.example.dndcharactercreatordemo.bll.dtos;

public abstract class BaseDTO {
    protected Long id;
    protected Boolean isDeleted;

    protected BaseDTO() {
    }

    protected BaseDTO(Long id, Boolean isDeleted) {
        this.id = id;
        if (isDeleted!=null)
            this.isDeleted = isDeleted;
        else
            this.isDeleted=false;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }
}
