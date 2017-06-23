package com.service;

import com.dao.UserDAO;

import com.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
@Transactional
public class UserServiceJPAImpl implements UserService {
    public UserServiceJPAImpl() {
    }

    @Autowired
    private UserDAO userDAO;


    @Override
    public int create(User user) {
        Map map = getValidationErrors(user, false);
        if (!map.isEmpty()) {
            throw new RuntimeException(map.toString());
        }

        return userDAO.createUser(user);
    }

    @Override
    public User edit(User user) {
        Map map = getValidationErrors(user, false);
        if (!map.isEmpty()) {
            throw new RuntimeException(map.toString());
        }
        return userDAO.updateUser(user);
    }


    @Override
    public User findById(int id) {
        return userDAO.getUser(id);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAllUsers();
    }


   @Override
    public List<User> findAll(String userName) {
        return userDAO.getAllUsers(userName);
    }

    private Map<String, String> getValidationErrors(User user, boolean isUserIdRequired) {
        Map<String, String> map = new HashMap<>();
        if (user.getName() == null || user.getName().length() == 0) {
            map.put("firstname", "Null");
        }
        if (user.getName().length() > 50) {
            map.put("firstname", "Length > 50");
        }
        if (user.getAge() > 100 || user.getAge() < 17) {
            map.put("age", "Age <17 || Age > 100");
        }
        return map;
    }

}
