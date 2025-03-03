package ru.javamentor.spring_boot_security.service;

import ru.javamentor.spring_boot_security.model.Role;

import java.util.List;


public interface RoleService {

    List<Role> getAllRoles();

    List<Role> getRoleById(List<Long> roleId);

}
