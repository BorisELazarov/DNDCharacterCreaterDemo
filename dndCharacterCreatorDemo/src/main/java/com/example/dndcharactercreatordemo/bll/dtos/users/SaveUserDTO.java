package com.example.dndcharactercreatordemo.bll.dtos.users;

public record SaveUserDTO(Boolean isDeleted,
                          String username, String password,
                          String role){
    public SaveUserDTO{
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
