package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.exception.ValidationException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateProfessionalUseCase - Testes Unitários")
class UpdateProfessionalUseCaseTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private UpdateProfessionalUseCase useCase;

    private Professional existingProfessional;
    private Professional updatedProfessional;

    @BeforeEach
    void setUp() {
        existingProfessional = Professional.builder()
                .id(1L)
                .name("Maria Silva")
                .email("maria@example.com")
                .phone("11999999999")
                .build();

        updatedProfessional = Professional.builder()
                .name("Maria Silva Santos")
                .email("maria.santos@example.com")
                .phone("11988888888")
                .workStartTime(LocalTime.of(8, 0))
                .workEndTime(LocalTime.of(17, 0))
                .build();
    }

    @Test
    @DisplayName("Deve atualizar profissional com sucesso quando dados são válidos")
    void shouldUpdateProfessionalSuccessfully() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(existingProfessional));
        when(repository.save(any(Professional.class))).thenReturn(existingProfessional);

        // Act
        Professional result = useCase.execute(1L, updatedProfessional);

        // Assert
        assertNotNull(result);
        verify(repository).findById(1L);
        verify(repository).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando profissional não existe")
    void shouldThrowResourceNotFoundExceptionWhenProfessionalNotFound() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> useCase.execute(999L, updatedProfessional));
        
        assertTrue(exception.getMessage().contains("Profissional não encontrado"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar ValidationException quando apenas horário de início é informado")
    void shouldThrowExceptionWhenOnlyStartTimeProvided() {
        // Arrange
        updatedProfessional.setWorkStartTime(LocalTime.of(9, 0));
        updatedProfessional.setWorkEndTime(null);
        when(repository.findById(1L)).thenReturn(Optional.of(existingProfessional));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, 
            () -> useCase.execute(1L, updatedProfessional));
        
        assertTrue(exception.getMessage().contains("horários"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve lançar ValidationException quando horário de início é após o término")
    void shouldThrowExceptionWhenStartTimeAfterEndTime() {
        // Arrange
        updatedProfessional.setWorkStartTime(LocalTime.of(18, 0));
        updatedProfessional.setWorkEndTime(LocalTime.of(9, 0));
        when(repository.findById(1L)).thenReturn(Optional.of(existingProfessional));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, 
            () -> useCase.execute(1L, updatedProfessional));
        
        assertTrue(exception.getMessage().contains("início deve ser anterior"));
        verify(repository, never()).save(any(Professional.class));
    }

    @Test
    @DisplayName("Deve atualizar profissional sem horários quando ambos são null")
    void shouldUpdateProfessionalWithoutWorkSchedule() {
        // Arrange
        updatedProfessional.setWorkStartTime(null);
        updatedProfessional.setWorkEndTime(null);
        when(repository.findById(1L)).thenReturn(Optional.of(existingProfessional));
        when(repository.save(any(Professional.class))).thenReturn(existingProfessional);

        // Act
        Professional result = useCase.execute(1L, updatedProfessional);

        // Assert
        assertNotNull(result);
        verify(repository).save(any(Professional.class));
    }
}
