package com.example.dndcharactercreatordemo.bll.dtos;

public record UserDTO(Long id, Boolean isDeleted,
                      String username, String password,
                      String role){
    public UserDTO{
        if (isDeleted==null)
        {
            isDeleted=false;
        }
    }
}
