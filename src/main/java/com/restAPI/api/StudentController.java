package com.restAPI.api;

import com.restAPI.model.Students;
import com.restAPI.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/student")
    public Iterable<Students> getAllStudents() {
        return studentService.allStudent();
    }

    @GetMapping("/student/{id}")
    public Optional<Students> getStudentById(@PathVariable(name = "id") Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudentById(@PathVariable(name = "id") Long id) {
        studentService.deleteById(id);
    }

    @PostMapping("/student")
    public Students addStudent(@RequestBody Students students) {
        return studentService.addStudent(students);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable(name = "id") long id, @RequestBody Students students) {
        Optional<Students> studentData = studentService.getStudentById(id);
        if (studentData.isPresent()) {
            Students student = studentData.get();
            student.setName(students.getName());
            student.setAge(students.getAge());
            student.setNationality(students.getNationality());
            student.setCity(students.getCity());
            student.setGender(students.getGender());
            student.setEnglish_grade(students.getEnglish_grade());
            student.setMath_grade(students.getMath_grade());
            student.setSciences_grade(students.getSciences_grade());
            student.setLanguage_grade(students.getLanguage_grade());
            return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
