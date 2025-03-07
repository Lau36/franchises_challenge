package com.example.franquicias.franquicias_prueba.infrastructure.in;

import com.example.franquicias.franquicias_prueba.aplication.IBranchRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.BranchHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Optional;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.ADD_BRANCH_PATH;
import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.BRANCH_ID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchHandlerTest {

    @Mock
    private IBranchRest branchRest;

    @InjectMocks
    private BranchHandler branchHandler;

    private BranchRequest branchRequest;

    @BeforeEach
    void setUp() {
        branchRequest = new BranchRequest("Branch Test", 1);
    }

    @Test
    public void addBranch_ShouldReturnOkStatus() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_BRANCH_PATH))
                .body(Mono.just(branchRequest));

        when(branchRest.addBranch(any(Branch.class))).thenReturn(Mono.empty());

        StepVerifier.create(branchHandler.addBranch(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(branchRest, times(1)).addBranch(any(Branch.class));
    }

    @Test
    public void addBranch_ShouldReturnConflictStatus_WhenBranchAlreadyExists() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_BRANCH_PATH))
                .body(Mono.just(branchRequest));

        when(branchRest.addBranch(any(Branch.class))).thenReturn(Mono.error(new AlreadyExistsException("Branch already exists")));

        StepVerifier.create(branchHandler.addBranch(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CONFLICT))
                .verifyComplete();
    }

    @Test
    public void addBranch_ShouldReturnBadRequestStatus_WhenDataIsInvalid() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_BRANCH_PATH))
                .body(Mono.empty());

        StepVerifier.create(branchHandler.addBranch(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.BAD_REQUEST))
                .verifyComplete();
    }

    @Test
    public void addBranch_ShouldReturnServerError_WhenUnexpectedError() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_BRANCH_PATH))
                .body(Mono.just(branchRequest));

        when(branchRest.addBranch(any(Branch.class))).thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        StepVerifier.create(branchHandler.addBranch(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                .verifyComplete();
    }

    @Test
    void updateBranchName_ShouldReturnOk_WhenBranchUpdatedSuccessfully() {
        Long branchId = 1L;
        String newName = "Updated Branch";
        ServerRequest mockRequest = mock(ServerRequest.class);

        when(mockRequest.queryParam(BRANCH_ID)).thenReturn(Optional.of(String.valueOf(branchId)));

        NameRequest nameRequest = new NameRequest(newName);
        when(mockRequest.bodyToMono(NameRequest.class)).thenReturn(Mono.just(nameRequest));

        when(branchRest.updateBranchName(branchId, newName))
                .thenReturn(Mono.empty());

        StepVerifier.create(branchHandler.updateBranchName(mockRequest))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();
    }

}
