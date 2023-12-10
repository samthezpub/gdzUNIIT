package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getUserRole();
    Role getAdminRole();
}
