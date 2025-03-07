package com.example.franquicias.franquicias_prueba.domain.validations;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FranchisesValidationsTest {
    @Mock
    private IFranchisePersistencePort franchisePersistencePort;

    @InjectMocks
    private FranchiseValidations franchiseValidations;

    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franchise 1");
    }

    @Test
    public void validateFranchiseName_shouldReturnOkTest() {

        Mockito.when(franchisePersistencePort.existsFranchise(franchise.getName())).thenReturn(Mono.just(false));

        Mono<Void> result = franchiseValidations.validateFranchiseName(franchise.getName());

        StepVerifier.create(result).verifyComplete();

        verify(franchisePersistencePort, times(1)).existsFranchise(franchise.getName());
    }

    @Test
    public void validateFranchiseName_shouldReturnErrorTest() {

        Mockito.when(franchisePersistencePort.existsFranchise(franchise.getName())).thenReturn(Mono.just(true));

        Mono<Void> result = franchiseValidations.validateFranchiseName(franchise.getName());

        StepVerifier.create(result)
                .expectError(AlreadyExistsException.class)
                .verify();

        verify(franchisePersistencePort, times(1)).existsFranchise(franchise.getName());
    }

    @Test
    public void validateFranchiseExistsById_shouldReturnOkTest() {
        Mockito.when(franchisePersistencePort.existsFranchiseById(1L)).thenReturn(Mono.just(true));

        Mono<Void> result = franchiseValidations.validateFranchiseExistsById(1L);

        StepVerifier.create(result).verifyComplete();

        verify(franchisePersistencePort, times(1)).existsFranchiseById(1L);
    }

    @Test
    public void validateFranchiseExistsById_shouldReturnErrorTest() {
        Mockito.when(franchisePersistencePort.existsFranchiseById(1L)).thenReturn(Mono.just(false));

        Mono<Void> result = franchiseValidations.validateFranchiseExistsById(1L);

        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();

        verify(franchisePersistencePort, times(1)).existsFranchiseById(1L);
    }


}
