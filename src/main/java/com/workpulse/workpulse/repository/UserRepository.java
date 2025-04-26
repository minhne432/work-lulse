package com.workpulse.workpulse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.workpulse.workpulse.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user theo username (dùng cho Spring Security)
    Optional<User> findByUsername(String username);

    // Nếu muốn: tìm tất cả nhân viên theo phòng ban
    // List<User> findByDepartmentId(Integer departmentId);
}