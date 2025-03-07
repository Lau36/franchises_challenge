package com.example.franquicias.franquicias_prueba.infrastructure.out;


import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.FranchiseAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.FranchiseEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IFranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FranchiseAdapterTest {

    @Mock
    private IFranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseAdapter franchiseAdapter;

    private Franchise franchise;
    private FranchiseEntity franchiseEntity;

    @BeforeEach
    void setUp() {
        franchiseEntity = FranchiseEntity.builder()
                .id(null)
                .name("Franchise test")
                .build();

        franchise = new Franchise();
        franchise.setId(null);
        franchise.setName("Franchise test");
    }
    @Test
    public void testAddFranchise() {

        when(franchiseRepository.save(franchiseEntity)).thenReturn(Mono.empty());

        Mono<Void> result = franchiseAdapter.saveFranchise(franchise);

        StepVerifier.create(result).verifyComplete();

        verify(franchiseRepository, times(1)).save(franchiseEntity);
    }

    @Test
    public void testExistFranchise() {

        when(franchiseRepository.existsFranchiseByName(franchise.getName())).thenReturn(Mono.just(true));

        Mono<Boolean> result = franchiseAdapter.existsFranchise(franchise.getName());

        StepVerifier.create(result).expectNext(true).verifyComplete();

        verify(franchiseRepository, times(1)).existsFranchiseByName(franchise.getName());
    }

    @Test
    public void testExistFranchiseById() {

        when(franchiseRepository.existsFranchiseById(1L)).thenReturn(Mono.just(true));

        Mono<Boolean> result = franchiseAdapter.existsFranchiseById(1L);

        StepVerifier.create(result).expectNext(true).verifyComplete();

        verify(franchiseRepository, times(1)).existsFranchiseById(1L);
    }

    @Test
    void testFindFranchiseById_Success() {
        Long franchiseId = 1L;
        FranchiseEntity franchiseEntity = FranchiseEntity.builder()
                .id(franchiseId)
                .name("Franchise test")
                .build();

        when(franchiseRepository.findFranchiseById(franchiseId)).thenReturn(Mono.just(franchiseEntity));

        Mono<Franchise> result = franchiseAdapter.findFranchiseById(franchiseId);

        StepVerifier.create(result)
                .expectNextMatches(franchise ->
                        franchise.getId().equals(franchiseEntity.getId()) &&
                                franchise.getName().equals(franchiseEntity.getName())
                )
                .verifyComplete();

        verify(franchiseRepository, times(1)).findFranchiseById(franchiseId);
    }


}
