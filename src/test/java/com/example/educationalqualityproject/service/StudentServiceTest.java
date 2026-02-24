package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Student;
import com.example.educationalqualityproject.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Deve buscar estudante por ID usando Mock")
    void deveBuscarEstudantePorId() {
        // GIVEN
        String id = "123";
        Student mockStudent = new Student();
        mockStudent.setId(id);
        mockStudent.setName("Aluno de Teste");
        
        when(studentRepository.findById(id)).thenReturn(Optional.of(mockStudent));

        // WHEN
        Optional<Student> result = studentService.getStudentById(id);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Aluno de Teste", result.get().getName());
        verify(studentRepository, times(1)).findById(id);
    }

    @ParameterizedTest
    @DisplayName("Validação de e-mails - Este teste falhará para domínios .com (Exercício)")
    @CsvSource({
        "teste@educacao.org, true",
        "aluno@gmail.com, true",
        "sem_arroba.com, false",
        "email_valido@escola.edu, true",
        "null, false"
    })
    void validarEmails(String email, boolean esperado) {
        // Se o email for a string "null", tratamos como null real
        String emailToTest = "null".equals(email) ? null : email;
        
        boolean resultado = studentService.isValidEmail(emailToTest);
        
        assertEquals(esperado, resultado, "Falha na validação para o email: " + email);
    }
}
