package com.pspkp.kpmain.custom;

import com.pspkp.kpmain.models.User;

public class UpdateUser {
    public static User update (User user, String username, String password, String status){
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(status);
        return user;
    }
}
