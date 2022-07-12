package com.springbootbeginners.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {

        return studentRepository.findAll();

//        return List.of(
//                new Student(1L, "Mariam",
//                        "marian.jamal@gmail.com",
//                        LocalDate.of(2000, Month.JANUARY, 5),
//                        21)
//        );
    }

    public ResponseEntity<String> /*void*/ addNewStudent(Student student) {
        System.out.println(student);
        Optional<Student> studentOptionalEmail =
                studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptionalEmail.isPresent()) {
            return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST);
            //throw new IllegalStateException("email "+ student.getEmail() + " already exists");
        }
        studentRepository.save(student);
        return new ResponseEntity<>("Student was added" + student.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteStudentById(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        Optional<Student> deletedStudent = studentRepository.findById(studentId);
        if (!studentExists) {
            //throw new IllegalStateException("Student does not exist");
            return new ResponseEntity<>("student does not exist", HttpStatus.BAD_REQUEST);
        } else {

            studentRepository.deleteById(studentId);
            String response = "Student " + deletedStudent.toString() + " was deleted";
            return new ResponseEntity<String>(response, HttpStatus.OK);
        }
    }

    @Transactional
    public void updateStudent(/*String name,*/ long id, String newName, String newEmail) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        //Optional<Student> studentExists = studentRepository.findStudentByName(name);
        if (!existingStudent.isPresent()) {
            //throw new IllegalStateException("student with name " + name + " does not exist");
            throw new IllegalStateException("student with id " + id + " does not exist");
        }
        if (newName == null) {
            if (!existEmail(newEmail)) {
                existingStudent.get().setEmail(newEmail);
            } else {
                System.out.println("the new Email already exist");
            }
        }
        if (newEmail == null && newName != null) {
            existingStudent.get().setName(newName);
        } else if (newName != null && newEmail != null) {
            existingStudent.get().setName(newName);
            //we must not update the email with an existing email
            if (existEmail(newEmail)) {
                System.out.println("the new Email already exist");
            } else {
                existingStudent.get().setEmail(newEmail);
            }
        }
    }

    public boolean existEmail(String email) {
        Optional<Student> studentWithExistingMail = studentRepository.findStudentByEmail(email);
        if (studentWithExistingMail.isPresent()) {
            System.out.println("the new Email already exist");
            return true;
        }
        return false;
    }
}

