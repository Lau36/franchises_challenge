package com.example.franquicias.franquicias_prueba.infrastructure.in;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.FranchiseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Optional;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.*;
import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.BRANCH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FranchiseHandlerTest {

    @Mock
    private IFranchiseRest franchiseRest;

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    private FranchiseRequest franchiseRequest;

    @BeforeEach
    void setUp() {
        franchiseRequest = new FranchiseRequest("Franquicia test");
    }

    @Test
    public void testCreateFranchise() {

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_FRANCHISE_PATH))
                .body(Mono.just(franchiseRequest));

        when(franchiseRest.createFranchise(any(FranchiseRequest.class))).thenReturn(Mono.empty());

        StepVerifier.create(franchiseHandler.createFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
                .verifyComplete();

        verify(franchiseRest, times(1)).createFranchise(any(FranchiseRequest.class));
    }

    @Test
    public void createFranchise_shouldReturnConflictStatus_whenDataAlreadyExistsTest() {

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_FRANCHISE_PATH))
                .body(Mono.just(franchiseRequest));

        when(franchiseRest.createFranchise(any(FranchiseRequest.class))).thenReturn(Mono.error(new AlreadyExistsException("Franchise already exists")));

        StepVerifier.create(franchiseHandler.createFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CONFLICT))
                .verifyComplete();
    }

    @Test
    public void createFranchise_shouldReturnBadRequestStatus_whenDataIsInvalidTest() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_FRANCHISE_PATH))
                .body(Mono.empty());

        StepVerifier.create(franchiseHandler.createFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.BAD_REQUEST))
                .verifyComplete();
    }

    @Test
    public void createFranchise_shouldReturnServerError_whenUnexpectedErroTest() {

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_FRANCHISE_PATH))
                .body(Mono.just(franchiseRequest));

        when(franchiseRest.createFranchise(any(FranchiseRequest.class))).thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        StepVerifier.create(franchiseHandler.createFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                .verifyComplete();
    }

    @Test
    void updateFranchise_ShouldReturnOk_WhenFranchiseUpdatedSuccessfully() {
        Long franchiseId = 1L;
        String newName = "Updated Franchise";
        ServerRequest mockRequest = mock(ServerRequest.class);

        Mockito.when(mockRequest.queryParam(FRANCHISE_ID)).thenReturn(Optional.of(String.valueOf(franchiseId)));

        NameRequest nameRequest = new NameRequest(newName);
        Mockito.when(mockRequest.bodyToMono(NameRequest.class)).thenReturn(Mono.just(nameRequest));

        Mockito.when(franchiseRest.updateFranchise(franchiseId, newName))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseHandler.updateFranchise(mockRequest))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_Error_NotFoundExceptionTest() {
        Long franchiseId = 1L;
        ServerRequest serverRequest = mock(ServerRequest.class);
        NameRequest nameRequest = new NameRequest("New Franchise Name");

        when(serverRequest.queryParam(FRANCHISE_ID)).thenReturn(Optional.of(String.valueOf(franchiseId)));
        when(serverRequest.bodyToMono(NameRequest.class)).thenReturn(Mono.just(nameRequest));
        when(franchiseRest.updateFranchise(anyLong(), anyString())).thenReturn(Mono.error(new NotFoundException("Franchise not found")));

        StepVerifier.create(franchiseHandler.updateFranchise(serverRequest))
                .consumeNextWith(response ->
                        assertEquals(HttpStatus.NOT_FOUND, response.statusCode()))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_Error_AlreadyExistsExceptionTest() {
        Long franchiseId = 1L;
        ServerRequest serverRequest = mock(ServerRequest.class);
        NameRequest nameRequest = new NameRequest("New Franchise Name");

        when(serverRequest.queryParam(FRANCHISE_ID)).thenReturn(Optional.of(String.valueOf(franchiseId)));
        when(serverRequest.bodyToMono(NameRequest.class)).thenReturn(Mono.just(nameRequest));
        when(franchiseRest.updateFranchise(anyLong(), anyString())).thenReturn(Mono.error(new AlreadyExistsException("Franchise already exists")));

        StepVerifier.create(franchiseHandler.updateFranchise(serverRequest))
                .consumeNextWith(response ->
                        assertEquals(HttpStatus.CONFLICT, response.statusCode()))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_whenBodyNotProvider_invalidDataExceptionTest() {

        Long franchiseId = 1L;
        ServerRequest serverRequest = mock(ServerRequest.class);

        when(serverRequest.queryParam(FRANCHISE_ID)).thenReturn(Optional.of(String.valueOf(franchiseId)));
        when(serverRequest.bodyToMono(NameRequest.class)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseHandler.updateFranchise(serverRequest))
                .consumeNextWith(response ->
                        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode()))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_whenFranchiseIdNotProvider_invalidDataExceptionTest() {

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.queryParam(FRANCHISE_ID)).thenReturn(Optional.empty());

        StepVerifier.create(franchiseHandler.updateFranchise(serverRequest))
                .consumeNextWith(response ->
                        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode()))
                .verifyComplete();
    }


}
