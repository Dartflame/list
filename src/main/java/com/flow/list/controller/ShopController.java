package com.flow.list.controller;
import com.flow.list.persist.ShoppingItem;
import com.flow.list.persist.ShoppingItemRepository;
import com.flow.list.persist.User;
import com.flow.list.persist.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final ShoppingItemRepository repository;

    private final UserRepository userRepository;


    @Autowired
    public ShopController(ShoppingItemRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {
        System.out.println("HELLO!!!!!!!_------------------");
        logger.info("User name: {}", principal.getName());
        model.addAttribute("items", repository.findByUserUsername(principal.getName()));
        model.addAttribute("item", new ShoppingItem());
        return "index";
    }

    @PostMapping
    public String newShoppingItem(ShoppingItem item, Principal principal) {
        logger.info("User name: {}", principal.getName());
        User user = userRepository.findByUsername(principal.getName()).get();
        item.setUser(user);
        item.setCount((long) repository.findByUserUsername(principal.getName()).size()+1);
        repository.save(item);
        return "redirect:/";
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public String deleteShoppingItem(@PathVariable("id") Long id, ShoppingItem item, Principal principal){
        item.setCount((long) repository.findByUserUsername(principal.getName()).size()-1);
        repository.deleteById(id);
        return "redirect:/";
    }

}