package com.dao;

import com.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface UserDAO  {
    int createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
    List<User> getAllUsers();
    User getUser(int id);
    List<User> getAllUsers(String userName);

}
