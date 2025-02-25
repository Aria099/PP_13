package ru.javamentor.spring_boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javamentor.spring_boot_security.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id IN :id")
    List<Role> findAllById(@Param("id") List<Long> id);
    Role findByName(String name);
}
