package com.example.online_shopping.service;

import com.example.online_shopping.entity.Role;
import com.example.online_shopping.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

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
