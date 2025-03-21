package com.example.franquicias.franquicias_prueba.domain.validations;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.BRANCH_NOT_FOUND;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class BranchValidationsTest {

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @InjectMocks
    private BranchValidations branchValidations;

    @Test
    public void validateExistsBranchByName_shoulReturnOkTest() {

        Mockito.when(branchPersistencePort.existsBranch("Branch name")).thenReturn(Mono.empty());

        Mono<Void> result = branchValidations.validateExistsBranchByName("Branch name");

        StepVerifier.create(result).verifyComplete();

        verify(branchPersistencePort, times(1)).existsBranch("Branch name");
    }

    @Test
    public void validateExistsBranchByName_shoulReturnErrorTest() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setFranchiseId(2L);
        branch.setName("Branch name");

        Mockito.when(branchPersistencePort.existsBranch("Branch name")).thenReturn(Mono.just(branch));

        Mono<Void> result = branchValidations.validateExistsBranchByName(branch.getName());

        StepVerifier.create(result)
                .expectError(AlreadyExistsException.class)
                .verify();

        verify(branchPersistencePort, times(1)).existsBranch(branch.getName());
    }

    @Test
    public void validateExistsBranchById_shoulReturnOkTest() {

        Mockito.when(branchPersistencePort.existsBranchById(1L)).thenReturn(Mono.just(true));

        Mono<Void> result = branchValidations.validateExistsBranchById(1L);

        StepVerifier.create(result).verifyComplete();

        verify(branchPersistencePort, times(1)).existsBranchById(1L);
    }

    @Test
    public void validateExistsBranchById_shoulReturnErrorTest() {
        Mockito.when(branchPersistencePort.existsBranchById(1L)).thenReturn(Mono.just(false));

        Mono<Void> result = branchValidations.validateExistsBranchById(1L);

        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();

        verify(branchPersistencePort, times(1)).existsBranchById(1L);


    }

    @Test
    void validateBranchId_shouldReturnBranch_whenBranchExistsTest() {
        Long branchId = 1L;
        Branch mockBranch = new Branch();
        mockBranch.setId(branchId);
        mockBranch.setName("Test Branch");

        Mockito.when(branchPersistencePort.findBranchById(branchId)).thenReturn(Mono.just(mockBranch));

        Mono<Branch> result = branchValidations.validateBranchId(branchId);

        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.getId().equals(branchId) &&
                        branch.getName().equals("Test Branch"))
                .verifyComplete();

        Mockito.verify(branchPersistencePort).findBranchById(branchId);
    }

    @Test
    void validateBranchId_shouldThrowNotFoundException_whenBranchDoesNotExistTest() {
        Long branchId = 2L;

        Mockito.when(branchPersistencePort.findBranchById(branchId)).thenReturn(Mono.empty());

        Mono<Branch> result = branchValidations.validateBranchId(branchId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException &&
                        throwable.getMessage().equals(String.format(BRANCH_NOT_FOUND, branchId)))
                .verify();

        Mockito.verify(branchPersistencePort).findBranchById(branchId);
    }


}
