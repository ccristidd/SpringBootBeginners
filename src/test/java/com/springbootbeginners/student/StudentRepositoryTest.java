package com.springbootbeginners.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
        //@Disabled
    void testIfCanFindStudentByEmail() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2022, Month.JANUARY, 1));
        testRepository.save(student);

        //when
//        Boolean exists = underTest.selectExistsEmail(email);

        String existingEmail = testRepository.findStudentByEmail(email).get().getEmail();
        Student existingStudent = testRepository.findStudentByEmail(email).get();


        //then
//        assertThat(exists).isTrue();
        assertThat(existingEmail).isEqualTo("jamila@jamila.com");
        assertThat(student).isEqualTo(existingStudent);
    }

    @Test
    void testIfCannotFindStudentByEmail() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2022, Month.JANUARY, 1));

        //when
        //String existingEmail= testRepository.findStudentByEmail(email).orElse(null).getEmail();
        Optional<Student> existingStudent = testRepository.findStudentByEmail(email);
        Student exist1 = testRepository.findStudentByEmail(email).orElse(null);

        //then
        //assertThat(existingEmail).isEmpty();
        assertThat(existingStudent).isEmpty();
        assertThat(exist1).isNull();
    }


    @Test
    void findStudentByNameTest() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2022, Month.JANUARY, 1));
        testRepository.save(student);

        //when
        Student foundStudent = testRepository.findStudentByName(student.getName()).get();

        //then
        assertThat(student).isEqualTo(foundStudent);
        assertThat(student.getId()).isEqualTo(foundStudent.getId());
    }

    @Test
    void testIfCannotFindStudentByName() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2022, Month.JANUARY, 1));

        //when
        Optional<Student> foundStudent = testRepository.findStudentByName(student.getName());

        //then
        assertThat(foundStudent).isEmpty();
    }


}