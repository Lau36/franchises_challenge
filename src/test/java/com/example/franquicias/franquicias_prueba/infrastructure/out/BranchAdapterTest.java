package com.example.franquicias.franquicias_prueba.infrastructure.out;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.BranchAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.BranchEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IBranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchAdapterTest {
    @Mock
    private IBranchRepository branchRepository;

    @InjectMocks
    private BranchAdapter branchAdapter;

    @Test
    public void saveFranchise_shouldSaveBranchTest() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setFranchiseId(2L);
        branch.setName("Test Branch");

        BranchEntity branchEntity = BranchEntity.builder()
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .build();

        when(branchRepository.save(any(BranchEntity.class))).thenReturn(Mono.just(branchEntity));

        Mono<Void> result = branchAdapter.saveFranchise(branch);

        StepVerifier.create(result).verifyComplete();

        verify(branchRepository, times(1)).save(any(BranchEntity.class));
    }

    @Test
    public void existsFranchise_shouldReturnTrueTest() {
        when(branchRepository.existsBranchByName("Test Branch")).thenReturn(Mono.just(true));

        Mono<Boolean> result = branchAdapter.existsFranchise("Test Branch");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(branchRepository, times(1)).existsBranchByName("Test Branch");
    }

    @Test
    public void existsFranchise_shouldReturnFalseTest() {
        when(branchRepository.existsBranchByName("Test Branch")).thenReturn(Mono.just(false));

        Mono<Boolean> result = branchAdapter.existsFranchise("Test Branch");

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(branchRepository, times(1)).existsBranchByName("Test Branch");
    }

    @Test
    public void existsBranch_shouldReturnBranchTest() {
        BranchEntity branchEntity = new BranchEntity(1L, "Test Branch", 2L);
        when(branchRepository.findBranchByName("Test Branch")).thenReturn(Mono.just(branchEntity));

        Mono<Branch> result = branchAdapter.existsBranch("Test Branch");

        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.getId().equals(1L) && branch.getName().equals("Test Branch") && branch.getFranchiseId().equals(2L))
                .verifyComplete();

        verify(branchRepository, times(1)).findBranchByName("Test Branch");
    }

    @Test
    public void existsBranch_shouldReturnEmptyTest() {
        when(branchRepository.findBranchByName("Test Branch")).thenReturn(Mono.empty());

        Mono<Branch> result = branchAdapter.existsBranch("Test Branch");

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchRepository, times(1)).findBranchByName("Test Branch");
    }

    @Test
    public void existsBranchById_shouldReturnTrueTest() {
        when(branchRepository.existsBranchById(1L)).thenReturn(Mono.just(true));

        Mono<Boolean> result = branchAdapter.existsBranchById(1L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(branchRepository, times(1)).existsBranchById(1L);
    }

    @Test
    public void existsBranchById_shouldReturnFalseTest() {
        when(branchRepository.existsBranchById(1L)).thenReturn(Mono.just(false));

        Mono<Boolean> result = branchAdapter.existsBranchById(1L);

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(branchRepository, times(1)).existsBranchById(1L);
    }

    @Test
    void findBranchById_shouldReturnBranchTest() {
        Long branchId = 1L;
        Long franchiseId = 100L;

        BranchEntity mockBranchEntity = new BranchEntity();
        mockBranchEntity.setId(branchId);
        mockBranchEntity.setName("Test Branch");
        mockBranchEntity.setFranchiseId(franchiseId);

        when(branchRepository.findBranchById(branchId))
                .thenReturn(Mono.just(mockBranchEntity));

        Mono<Branch> result = branchAdapter.findBranchById(branchId);

        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.getId().equals(branchId) &&
                        branch.getName().equals("Test Branch") &&
                        branch.getFranchiseId().equals(franchiseId))
                .verifyComplete();

        verify(branchRepository, times(1)).findBranchById(branchId);
    }


}
