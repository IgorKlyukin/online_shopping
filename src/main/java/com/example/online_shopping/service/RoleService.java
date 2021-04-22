package com.example.online_shopping.service;

import com.example.online_shopping.entity.Role;
import com.example.online_shopping.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public static final String ROLE_USER = "ROLE_USER";

    public Role getRoleUser() {
        Role role = roleRepository.findById(1L).orElse(null);
        if (role == null) {
            role = new Role(1L, ROLE_USER);
            roleRepository.save(role);
        }
        return role;
    }
}
