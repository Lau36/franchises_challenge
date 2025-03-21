package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.aplication.impl.BranchRest;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IBranchServicePort;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
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
public class BranchRestTest {

    @Mock
    private IBranchServicePort branchServicePort;

    @InjectMocks
    private BranchRest branchRest;

    private BranchRequest branchRequest;

    @BeforeEach
    void setUp() {

        branchRequest = new BranchRequest();
        branchRequest.setName("Sucursal Principal");
        branchRequest.setFranchiseId(1);
    }

    @Test
    void testAddBranch_Success() {
        when(branchServicePort.addBranch(any(Branch.class))).thenReturn(Mono.empty());

        Mono<Void> result = branchRest.addBranch(branchRequest);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchServicePort, times(1)).addBranch(any(Branch.class));
    }

    @Test
    void testUpdateBranchName_Success() {
        Long branchId = 1L;
        String newName = "Sucursal Secundaria";

        when(branchServicePort.updateBranchName(branchId, newName)).thenReturn(Mono.empty());

        Mono<Void> result = branchRest.updateBranchName(branchId, newName);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchServicePort, times(1)).updateBranchName(branchId, newName);
    }
}
