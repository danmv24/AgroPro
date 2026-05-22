package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.User;

public interface UserService {

    User getCurrentUser();

    User getByUsername(String username);

}
