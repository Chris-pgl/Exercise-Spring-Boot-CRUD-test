package com.example.Spring.Boot.CRUD.test.repository;

import com.example.Spring.Boot.CRUD.test.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
