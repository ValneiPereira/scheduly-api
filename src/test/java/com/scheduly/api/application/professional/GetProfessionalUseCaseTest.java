package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetProfessionalUseCase - Testes Unitários")
class GetProfessionalUseCaseTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private GetProfessionalUseCase useCase;

    private Professional professional;

    @BeforeEach
    void setUp() {
        professional = Professional.builder()
                .id(1L)
                .name("João Santos")
                .email("joao@example.com")
                .build();
    }

    @Test
    @DisplayName("Deve retornar profissional quando ID existe")
    void shouldReturnProfessionalWhenIdExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(professional));

        // Act
        Professional result = useCase.execute(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Santos", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando ID não existe")
    void shouldThrowResourceNotFoundExceptionWhenIdNotFound() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> useCase.execute(999L));
        
        assertTrue(exception.getMessage().contains("Profissional não encontrado"));
        assertTrue(exception.getMessage().contains("999"));
        verify(repository).findById(999L);
    }
}
