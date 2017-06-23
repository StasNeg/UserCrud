package com.service;


import com.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(int id);
    List<User> findAll(String userName);
    int create(User User);
    User edit(User User);
    void deleteById(int id);


}
