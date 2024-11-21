package com.uzay.securitygeneltekrarr.controller;

import com.uzay.securitygeneltekrarr.entity.User;
import com.uzay.securitygeneltekrarr.service.UserService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/security")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

        @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add-user")
    public ResponseEntity<User>addUser(@RequestBody User user) {
        User user1 = userService.addUser(user);
        if (user1 == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id) {
        boolean b = userService.deleteUserById(id);
        if (!b) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body("başarı ile silindi");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User>updateUser(@PathVariable Long id, @RequestBody User user) {
        User user1 = userService.updateUser(id, user);
        if (user1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user1);
    }



}
