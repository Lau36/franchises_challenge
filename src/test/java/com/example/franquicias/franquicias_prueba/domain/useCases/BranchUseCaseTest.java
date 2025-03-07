package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {
    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private FranchiseValidations franchiseValidations;

    @Mock
    private BranchValidations branchValidations;

    @InjectMocks
    private BranchUseCase branchUseCase;

    private Branch branch;

    @BeforeEach
    void setUp() {
        branch = new Branch();
        branch.setId(1L);
        branch.setFranchiseId(2L);
        branch.setName("Test Branch");
    }

    @Test
    void addBranch_ShouldSaveBranch_WhenValidationsPass() {

        when(franchiseValidations.validateFranchiseExistsById(branch.getFranchiseId()))
                .thenReturn(Mono.empty());
        when(branchValidations.validateExistsBranchByName(branch.getName()))
                .thenReturn(Mono.empty());
        when(branchPersistencePort.saveFranchise(branch))
                .thenReturn(Mono.empty());

        Mono<Void> result = branchUseCase.addBranch(branch);

        StepVerifier.create(result).verifyComplete();

        verify(franchiseValidations, times(1)).validateFranchiseExistsById(branch.getFranchiseId());
        verify(branchValidations, times(1)).validateExistsBranchByName(branch.getName());
        verify(branchPersistencePort, times(1)).saveFranchise(branch);
    }

}
