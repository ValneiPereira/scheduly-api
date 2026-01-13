package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteProfessionalUseCase - Testes Unitários")
class DeleteProfessionalUseCaseTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private DeleteProfessionalUseCase useCase;

    @Test
    @DisplayName("Deve deletar profissional com sucesso quando ID existe")
    void shouldDeleteProfessionalSuccessfully() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> useCase.execute(1L));

        // Assert
        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando ID não existe")
    void shouldThrowResourceNotFoundExceptionWhenIdNotFound() {
        // Arrange
        when(repository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> useCase.execute(999L));
        
        assertTrue(exception.getMessage().contains("Profissional não encontrado"));
        assertTrue(exception.getMessage().contains("999"));
        verify(repository).existsById(999L);
        verify(repository, never()).deleteById(anyLong());
    }
}
