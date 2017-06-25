package com.controller;

import com.model.Filter;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private int currentPages;
    private static final int ROWS_IN_TABLE = 16;
    private static Filter lastFilter = new Filter();

    @RequestMapping("/")
    public String start() {
        return "redirect:/0";
    }
    @RequestMapping("/{currentPage}")
    public String index(@PathVariable("currentPage") int currentPage, Model model) {
        List<User> listUsers;
        if (lastFilter.getName() == null || lastFilter.getName().length() == 0) {
            listUsers = userService.findAll();
        } else {
            listUsers = userService.findAll(lastFilter.getName());
        }
        int pages = listUsers.size() / ROWS_IN_TABLE;
        if (currentPage > pages) {
            currentPage = pages;
        } else if (currentPage < 0) {
            currentPage = 0;
        }
        this.currentPages = currentPage;
        listUsers = getPages(listUsers, currentPage);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", pages);
        model.addAttribute("filter", lastFilter);
        return "index";
    }

    @RequestMapping("/setFilter")
    public String setFilter(@ModelAttribute Filter filter, Model model) {
        lastFilter = filter;
        return "redirect:/" + currentPages;
    }

    private List<User> getPages(List<User> listUsers, int page) {
        int size = listUsers.size();
        List<User> result = new ArrayList<>();
        for (int i = 0; i < ROWS_IN_TABLE && size > page * ROWS_IN_TABLE + i; i++) {
            result.add(listUsers.get(page * ROWS_IN_TABLE + i));
        }
        return result;
    }

    @RequestMapping("/edit/{id}")
    public String view(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", new isAdmin());
        return "edit";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/" + currentPages;
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute(value = "user") User user, @ModelAttribute(value = "isAdmin") isAdmin admin) {
        System.out.println(admin.getIsAdmin());
        if(admin.getIsAdmin().equalsIgnoreCase("yes"))
            user.setAdmin(true);
        else
            user.setAdmin(false);
        if (user.getId() == 0) { // if user id is null then creating user other updating user
            user.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

//            user.setAdmin(true);
            userService.create(user);
        } else {
            if (!userService.findById(user.getId()).equals(user)) {
//                user.setAdmin(userService.findById(user.getId()).isAdmin());
                userService.edit(user);
            }
        }
        return "redirect:/" + currentPages;
    }

    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isAdmin", new isAdmin());
        return "create";
    }
    public static class isAdmin{
        private String isAdmin = "";

        public isAdmin() {
        }

        public String getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(String isAdmin) {
            this.isAdmin = isAdmin;
        }
    }
}