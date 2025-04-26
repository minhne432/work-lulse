package com.workpulse.workpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.workpulse.workpulse.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // ví dụ: tìm theo tên phòng ban
    // Optional<Department> findByName(String name);
}