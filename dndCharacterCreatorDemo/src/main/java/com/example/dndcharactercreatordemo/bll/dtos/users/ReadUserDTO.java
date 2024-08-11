package com.example.dndcharactercreatordemo.bll.dtos.users;

public record ReadUserDTO(Long id, Boolean isDeleted,
                          String username, String password,
                          String role){
    public ReadUserDTO {
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
