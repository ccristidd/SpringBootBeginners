package com.springbootbeginners.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {
    //select * from student where email = 'email'
    //@Query("SELECT s FROM Student s WHERE s.email=?1")
    Optional<Student> findStudentByEmail(String email);

    Optional<Student> findStudentByName(String name);

//    @Query("" +
//            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
//            "TRUE ELSE FALSE END " +
//            "FROM Student s " +
//            "WHERE s.email = ?1"
//    )
//    Boolean selectExistsEmail(String email);


}
