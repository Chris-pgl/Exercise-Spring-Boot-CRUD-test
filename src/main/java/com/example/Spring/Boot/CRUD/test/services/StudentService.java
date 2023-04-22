package com.example.Spring.Boot.CRUD.test.services;

import com.example.Spring.Boot.CRUD.test.entities.Student;
import com.example.Spring.Boot.CRUD.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student setStudentIsWoriking(long studentId, boolean isWorking){
        Optional<Student> student = studentRepository.findById(studentId);
        if(!student.isPresent()) return null;
        student.get().setWorking(isWorking);
        return studentRepository.save(student.get());
    }


}
