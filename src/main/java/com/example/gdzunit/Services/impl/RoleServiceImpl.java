package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Repositories.RoleRepository;
import com.example.gdzunit.Services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getUserRole() {
        return roleRepository.findRoleByNameSet("USER");
    }

    @Override
    public Role getAdminRole() {
        return roleRepository.findRoleByName("ADMIN");
    }
}
