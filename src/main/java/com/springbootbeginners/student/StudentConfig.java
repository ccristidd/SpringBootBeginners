package com.springbootbeginners.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(1L, "Mariam",
                    "marian.jamal@gmail.com",
                    LocalDate.of(2000, JANUARY, 5));
            Student george = new Student(2L, "George", "george@george.com",
                    LocalDate.of(2002, FEBRUARY, 5));

            studentRepository.saveAll(List.of(mariam, george));


        };
    }
}
