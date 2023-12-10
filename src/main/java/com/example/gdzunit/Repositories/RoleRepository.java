package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findRoleByName(@Param("name") String name);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    HashSet<Role> findRoleByNameSet(@Param("name") String name);

}