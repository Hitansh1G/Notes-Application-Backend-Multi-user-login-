package com.secure.notes.services.impl;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.models.AppRole;
import com.secure.notes.models.Role;
import com.secure.notes.models.User;
import com.secure.notes.repositories.RoleRepository;
import com.secure.notes.repositories.UserRepository;
import com.secure.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    private User user;


    //    @Override
//    public void updateUserRole(Long userId , String roleName){
//        User user = userRepository.findById(userId).orElseThrow(()
//            ->new RuntimeException("user not found"));
//        AppRole appRole = AppRole.valueOf(roleName);
//        Role role = roleRepository.findByRoleName(appRole)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//        userRepository.save(user);
//    }
    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AppRole appRole;
        try {
            appRole = AppRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role name");
        }
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
}
    @Override
    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getTwoFactorSecret(),
                user.isTwoFactorEnabled(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName().name()));
//    }

}
