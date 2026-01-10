package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateProfessionalUseCase - Testes Unitários")
class CreateProfessionalUseCaseTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private CreateProfessionalUseCase useCase;

    private Professional professional;

    @BeforeEach
    void setUp() {
        professional = Professional.builder()
                .name("Maria Silva")
                .email("maria@example.com")
                .phone("11999999999")
                .cpf("12345678900")
                .workStartTime(LocalTime.of(9, 0))
                .workEndTime(LocalTime.of(18, 0))
                .build();
    }

    @Test
    @DisplayName("Deve criar profissional com sucesso quando dados são válidos")
    void shouldCreateProfessionalSuccessfully() {
        // Arrange
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(repository.save(any(Professional.class))).thenReturn(professional);

        // Act
        Professional result = useCase.execute(professional);

        // Assert
        assertNotNull(result);
        assertEquals("Maria Silva", result.getName());
        verify(repository).existsByEmail("maria@example.com");
        verify(repository).existsByCpf("12345678900");
        verify(repository).save(professional);
    }

    @Test
    @DisplayName("Deve lançar ConflictException quando email já existe")
    void shouldThrowConflictExceptionWhenEmailExists() {
        // Arrange
        when(repository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, 
            () -> useCase.execute(professional));
        
        assertTrue(exception.getMessage().contains("email"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar ConflictException quando CPF já existe")
    void shouldThrowConflictExceptionWhenCpfExists() {
        // Arrange
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(true);

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, 
            () -> useCase.execute(professional));
        
        assertTrue(exception.getMessage().contains("CPF"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando apenas horário de início é informado")
    void shouldThrowExceptionWhenOnlyStartTimeProvided() {
        // Arrange
        professional.setWorkStartTime(LocalTime.of(9, 0));
        professional.setWorkEndTime(null);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> useCase.execute(professional));
        
        assertTrue(exception.getMessage().contains("horários"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando apenas horário de término é informado")
    void shouldThrowExceptionWhenOnlyEndTimeProvided() {
        // Arrange
        professional.setWorkStartTime(null);
        professional.setWorkEndTime(LocalTime.of(18, 0));
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> useCase.execute(professional));
        
        assertTrue(exception.getMessage().contains("horários"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando horário de início é após o término")
    void shouldThrowExceptionWhenStartTimeAfterEndTime() {
        // Arrange
        professional.setWorkStartTime(LocalTime.of(18, 0));
        professional.setWorkEndTime(LocalTime.of(9, 0));
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> useCase.execute(professional));
        
        assertTrue(exception.getMessage().contains("início deve ser anterior"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve criar profissional sem horários de trabalho quando ambos são null")
    void shouldCreateProfessionalWithoutWorkSchedule() {
        // Arrange
        professional.setWorkStartTime(null);
        professional.setWorkEndTime(null);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(repository.save(any(Professional.class))).thenReturn(professional);

        // Act
        Professional result = useCase.execute(professional);

        // Assert
        assertNotNull(result);
        assertNull(result.getWorkStartTime());
        assertNull(result.getWorkEndTime());
        verify(repository).save(professional);
    }
}
