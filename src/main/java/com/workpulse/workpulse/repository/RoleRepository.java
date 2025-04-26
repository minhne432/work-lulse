package com.workpulse.workpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.workpulse.workpulse.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // ví dụ: tìm theo tên role
    // Optional<Role> findByName(RoleName name);
}