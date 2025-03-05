package com.example.franquicias.franquicias_prueba.infrastructure.configuration;

import com.example.franquicias.franquicias_prueba.aplication.IBranchRest;
import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.aplication.impl.BranchRest;
import com.example.franquicias.franquicias_prueba.aplication.impl.FranchiseRest;
import com.example.franquicias.franquicias_prueba.aplication.impl.ProductRest;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IBranchServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.useCases.BranchUseCase;
import com.example.franquicias.franquicias_prueba.domain.useCases.FranchiseUseCase;
import com.example.franquicias.franquicias_prueba.domain.useCases.ProductUseCase;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.ProductValidations;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.BranchAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.FranchiseAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.ProductAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IBranchRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IFranchiseRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductBranchRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IFranchiseRepository franchiseRepository;
    private final IBranchRepository branchRepository;
    private final IProductRespository productRepository;
    private final IProductBranchRepository productBranchRepository;

    @Bean
    public FranchiseValidations franchiseValidations() {
        return new FranchiseValidations(franchisePersistencePort());
    }

    @Bean
    public IFranchiseServicePort franchiseServicePort() {
        return new FranchiseUseCase(franchisePersistencePort(), franchiseValidations());
    }

    @Bean
    public IFranchisePersistencePort franchisePersistencePort() {
        return new FranchiseAdapter(franchiseRepository);
    }

    @Bean
    public IFranchiseRest franchiseRest() {
        return new FranchiseRest(franchiseServicePort());
    }

    @Bean
    public BranchValidations branchValidations() {
        return new BranchValidations(branchPersistencePort());
    }

    @Bean
    public IBranchServicePort branchServicePort() {
        return new BranchUseCase(branchPersistencePort(), franchiseValidations(), branchValidations());
    }

    @Bean
    public IBranchPersistencePort branchPersistencePort() {
        return new BranchAdapter(branchRepository);
    }

    @Bean
    public IBranchRest branchRest() {
        return new BranchRest(branchServicePort());
    }



    @Bean
    public ProductValidations productValidations() {
        return new ProductValidations(productPersistencePort());
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort(), productValidations(), branchValidations(), franchiseValidations());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductAdapter(productBranchRepository, productRepository);
    }

    @Bean
    public IProductRest productRest() {
        return new ProductRest(productServicePort());
    }


}
