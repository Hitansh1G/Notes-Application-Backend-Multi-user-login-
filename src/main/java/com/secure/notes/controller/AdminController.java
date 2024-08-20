package com.secure.notes.controller;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.models.User;
import com.secure.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/update-role")
    public ResponseEntity<String> updateUserRole(@RequestParam Long userId,
                                                 @RequestParam String roleName){
        userService.updateUserRole(userId,roleName);
        return ResponseEntity.ok("User role updated");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
