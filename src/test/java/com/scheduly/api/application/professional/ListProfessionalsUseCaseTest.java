package com.scheduly.api.application.professional;

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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListProfessionalsUseCase - Testes Unitários")
class ListProfessionalsUseCaseTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private ListProfessionalsUseCase useCase;

    private List<Professional> professionals;

    @BeforeEach
    void setUp() {
        Professional professional1 = Professional.builder()
                .id(1L)
                .name("Maria Silva")
                .email("maria@example.com")
                .workStartTime(LocalTime.of(9, 0))
                .workEndTime(LocalTime.of(18, 0))
                .build();

        Professional professional2 = Professional.builder()
                .id(2L)
                .name("João Santos")
                .email("joao@example.com")
                .workStartTime(LocalTime.of(8, 0))
                .workEndTime(LocalTime.of(17, 0))
                .build();

        professionals = Arrays.asList(professional1, professional2);
    }

    @Test
    @DisplayName("Deve retornar lista de profissionais quando existem profissionais cadastrados")
    void shouldReturnListOfProfessionals() {
        // Arrange
        when(repository.findAll()).thenReturn(professionals);

        // Act
        List<Professional> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Maria Silva", result.get(0).getName());
        assertEquals("João Santos", result.get(1).getName());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem profissionais")
    void shouldReturnEmptyListWhenNoProfessionals() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Professional> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }
}
