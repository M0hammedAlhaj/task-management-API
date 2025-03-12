package com.spring.taskmanagment.service;

import com.spring.taskmanagment.dao.RoleDao;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.model.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    public Role findRoleById(int id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
    }

    @Cacheable(value = "role", key = "#name")
    public Role findRoleByName(String name) {
        return roleDao.findByRoleName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
    }
}
