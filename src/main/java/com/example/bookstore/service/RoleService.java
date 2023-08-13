package com.example.bookstore.service;

import com.example.bookstore.domain.Role;
import com.example.bookstore.repo.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String roleUser) {
        return roleRepository.findByName(roleUser);
    }

    public Role save(Role r){
        return roleRepository.save(r);
    }
}
