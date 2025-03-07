package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;

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
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franchise 1");

        Mockito.when(franchiseValidations.validateFranchiseName(franchise.getName())).thenReturn(Mono.empty());
        Mockito.when(franchisePersistencePort.saveFranchise(franchise)).thenReturn(Mono.empty());

        Mono<Void> result = franchiseUseCase.saveFranchise(franchise);

        StepVerifier.create(result).verifyComplete();

        Mockito.verify(franchiseValidations, times(1)).validateFranchiseName(franchise.getName());
        Mockito.verify(franchisePersistencePort, times(1)).saveFranchise(franchise);
    }

    @Test
    void updatFranchiseName_shouldUpdateFranchiseTest() {
        Long franchiseId = 1L;
        String newName = "Updated Franchise";
        Franchise mockFranchise = new Franchise();
        mockFranchise.setId(franchiseId);
        mockFranchise.setName("Old Franchise");

        Mockito.when(franchisePersistencePort.findFranchiseById(franchiseId)).thenReturn(Mono.just(mockFranchise));

        Mockito.when(franchiseValidations.validateFranchiseName(newName)).thenReturn(Mono.empty());

        Mockito.when(franchisePersistencePort.saveFranchise(mockFranchise)).thenReturn(Mono.empty());

        Mono<Void> result = franchiseUseCase.updatFranchiseName(franchiseId, newName);
        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(franchisePersistencePort, times(1)).findFranchiseById(franchiseId);
        Mockito.verify(franchiseValidations, times(1)).validateFranchiseName(newName);
        Mockito.verify(franchisePersistencePort, times(1)).saveFranchise(mockFranchise);
    }

}
