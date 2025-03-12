package ru.javamentor.spring_boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javamentor.spring_boot_security.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id IN :id")
    List<Role> findAllById(@Param("id") List<Long> id);
    Role findByName(String name);

    //Role findByNameOfRole(String name);

    Optional<Role> findById(Long id);
}
