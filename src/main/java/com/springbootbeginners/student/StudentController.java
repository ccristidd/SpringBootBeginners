package com.springbootbeginners.student;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> /*void*/ registerNewStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    @ResponseBody
    public ResponseEntity<String> /*String*/ deleteStudent(@PathVariable("studentId") Long studentId) {
        return studentService.deleteStudentById(studentId);
    }

    @PutMapping(path = "{name}")
    public void updateStudent(@PathVariable("name") Long id,
                              @RequestBody ObjectNode data) {
        String newName;
        String newEmail;
        if (data.get("newName") == null) {
            newName = null;
        } else {
            newName = data.get("newName").asText();
        }

        if (data.get("newEmail") == null) {
            newEmail = null;
        } else {
            newEmail = data.get("newEmail").asText();
        }

        studentService.updateStudent(id, newName, newEmail);
    }


}

