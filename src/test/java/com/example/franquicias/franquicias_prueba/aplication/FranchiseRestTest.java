package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.aplication.impl.FranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
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
public class FranchiseRestTest {
    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @InjectMocks
    private FranchiseRest franchiseRest;

    private FranchiseRequest franchiseRequest;

    @BeforeEach
    void setUp() {
        franchiseRequest = new FranchiseRequest();
        franchiseRequest.setName("Franquicia Principal");
    }

    @Test
    void testCreateFranchise_successTest() {
        when(franchiseServicePort.saveFranchise(any(Franchise.class))).thenReturn(Mono.empty());

        Mono<Void> result = franchiseRest.createFranchise(franchiseRequest);

        StepVerifier.create(result).verifyComplete();

        verify(franchiseServicePort, times(1)).saveFranchise(any(Franchise.class));
    }

    @Test
    void testUpdateFranchise_Success() {
        Long franchiseId = 1L;
        String newName = "Franquicia Secundaria";

        when(franchiseServicePort.updatFranchiseName(franchiseId, newName)).thenReturn(Mono.empty());

        Mono<Void> result = franchiseRest.updateFranchise(franchiseId, newName);

        StepVerifier.create(result).verifyComplete();

        verify(franchiseServicePort, times(1)).updatFranchiseName(franchiseId, newName);
    }
}
