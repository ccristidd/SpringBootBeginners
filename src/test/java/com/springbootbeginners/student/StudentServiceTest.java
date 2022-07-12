package com.springbootbeginners.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private Student studentMock;
    @Mock
    private StudentRepository testStudentRepository;
    private StudentService testStudentService;
    //private AutoCloseable autoCloseable;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        //autoCloseable = MockitoAnnotations.openMocks(/*StudentServiceTest.class*/this);
        testStudentService = new StudentService(testStudentRepository);
    }

//    @AfterEach
//    void tearDown() throws Exception{
//    //    autoCloseable.close();
//    }

    @Test
    void canGetAllStudents() {
        //when
        testStudentService.getStudents();
        //then
        verify(testStudentRepository).findAll();
    }

    @Test
        //@Disabled
    void CanAddNewStudent() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        //when
        testStudentService.addNewStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(testStudentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void CannotAddNewStudentBecauseEmailIsTakenReturnBadRequest() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        /* se foloseste pentru mockuirea valorilor true sau false.
           in cazul nostru un Optional se mockuieste ca mai jos la when
        given(testRepository.findStudentByEmail(email).isPresent())
                .willReturn(true);*/
        //when
        when(testStudentRepository.findStudentByEmail(email))
                .thenReturn(Optional.of(student));
        //then
        assertThat(testStudentService.addNewStudent(student)
                .getBody()).isEqualTo("email already exists");
        assertThat(testStudentService.addNewStudent(student)
                .getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        //verifica faptul ca testStudentRepository nu a salvat nimic
        verify(testStudentRepository, never()).save(any());
    }

    @Test
    @Disabled
        //se ruleaza cand in StudentService aruncam exceptie
    void CannotAddNewStudentBecauseEmailIsTakenThrowException() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        /* se foloseste pentru mockuirea valorilor true sau false.
           in cazul nostru un Optional se mockuieste ca mai jos la when
        given(testRepository.findStudentByEmail(email).isPresent())
                .willReturn(true);*/
        //when
        when(testStudentRepository.findStudentByEmail(email))
                .thenReturn(Optional.of(student));
        //then
        assertThatThrownBy(() -> testStudentService.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email " + student.getEmail() + " already exists");
        //verifica faptul ca testStudentRepository nu a salvat nimic
        verify(testStudentRepository, never()).save(any());
    }

    @Test
    void deleteStudentByIdWhenStudentExists() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        testStudentRepository.save(student);
        given(testStudentRepository.existsById(student.getId()))
                .willReturn(true);
        testStudentService.deleteStudentById(student.getId());
        verify(testStudentRepository).deleteById(idCaptor.capture());
        //when
        //then
        assertThat(student.getId()).isEqualTo(idCaptor.getValue());
        assertThat(testStudentService.deleteStudentById(student.getId()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteStudentByIdWhenStudentDoesNotExist() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student("Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        testStudentRepository.save(student);
        given(testStudentRepository.existsById(student.getId()))
                .willReturn(false);
        //when
        //then
        assertThat(testStudentService.deleteStudentById(student.getId()).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateStudentTestWhenStudentDoesNotExist() {
        //given
        String email = "jamila@jamila.com";
        Student student = new Student(1L, "Jamila",
                email,
                LocalDate.of(2020, Month.JANUARY, 1));
        //testStudentRepository.save(student);

        //when

        when(testStudentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());
        //then
        //assertThat(tesStudentService.updateStudent(student.getId(), "newName", "newEmail")

        assertThatThrownBy(() -> testStudentService.updateStudent(student.getId(), "newName", "newEmail"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("student with id " + student.getId() + " does not exist");
    }

    @Test
    void updateStudentTestWhenStudentExistsNameIsNull() {
        //given
        //using the studentMock defined at the beginning of the class

        //when
        when(testStudentRepository.findById(studentMock.getId()))
                .thenReturn(Optional.of(studentMock));
        testStudentService.updateStudent(studentMock.getId(), null, "newEmail@ing.com");
        //then
        verify(studentMock, times(1)).setEmail("newEmail@ing.com");
        verify(studentMock, times(0)).setName(any());
    }

    @Test
    void updateStudentTestWhenStudentExistsEmailIsNull() {
        //given
        //using the studentMock defined at the beginning of the class

        //when
        when(testStudentRepository.findById(studentMock.getId()))
                .thenReturn(Optional.of(studentMock));
        testStudentService.updateStudent(studentMock.getId(), "newName", null);
        //then
        verify(studentMock, times(1)).setName("newName");
        verify(studentMock, times(0)).setEmail(any());

    }

    @Test
    void updateStudentTestWhenStudentExistsEmailNotNullNameNotNull() {
        //given
        //using the studentMock defined at the beginning of the class

        //when
        when(testStudentRepository.findById(studentMock.getId()))
                .thenReturn(Optional.of(studentMock));
        testStudentService.updateStudent(studentMock.getId(), "newName", "newEmail");
        //then
        verify(studentMock, times(1)).setName("newName");
        verify(studentMock, times(1)).setEmail("newEmail");

    }

    @Test
    @Disabled
    void newTestToUpdate() {

    }
}