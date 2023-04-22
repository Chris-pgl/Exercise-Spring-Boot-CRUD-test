package com.example.Spring.Boot.CRUD.test.controllers;

import com.example.Spring.Boot.CRUD.test.entities.Student;
import com.example.Spring.Boot.CRUD.test.repository.StudentRepository;
import com.example.Spring.Boot.CRUD.test.services.StudentService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @PostMapping
    public @ResponseBody Student create(@RequestBody Student student){
        Student studentSaved = studentRepository.saveAndFlush(student);
        return studentSaved;
    }

    @GetMapping("/")
    public List<Student> getList(){
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getSingle(@PathVariable long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            return student.get();
        }else {
            return null;
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody Student update(@PathVariable long id, @RequestBody @NotNull Student student){
        student.setId(id);
        return studentRepository.save(student);
    }

    @PutMapping("/{id}/working")
    public @ResponseBody Student setStudentWorking(@PathVariable long id, @RequestParam("working") boolean working){
        return studentService.setStudentIsWoriking(id,working);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") long id){
        studentRepository.deleteById(id);
    }





}
