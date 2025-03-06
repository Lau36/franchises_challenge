package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FranchisesUseCaseTest {

    @Mock
    private  IFranchisePersistencePort franchisePersistencePort;

    @Mock
    private FranchiseValidations franchiseValidations;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    @Test
    public void addNewFranchise_shoulReturnOkTest() {
        Franchise franchise = new Franchise(1L, "Franchise 1");

        Mockito.when(franchiseValidations.validateFranchiseName(franchise)).thenReturn(Mono.empty());
        Mockito.when(franchisePersistencePort.saveFranchise(franchise)).thenReturn(Mono.empty());

        Mono<Void> result = franchiseUseCase.saveFranchise(franchise);

        StepVerifier.create(result).verifyComplete();

        Mockito.verify(franchiseValidations, Mockito.times(1)).validateFranchiseName(franchise);
        Mockito.verify(franchisePersistencePort, Mockito.times(1)).saveFranchise(franchise);
    }
}
