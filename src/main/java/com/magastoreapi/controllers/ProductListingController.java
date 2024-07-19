package com.magastoreapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magastoreapi.dtos.ProductListingDto;
import com.magastoreapi.models.ProductModel;
import com.magastoreapi.repositories.ProductRepository;

@RestController
@RequestMapping("/lista-de-produtos")
@CrossOrigin(origins = "*")
public class ProductListingController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @Transactional(readOnly = true) 
    public ResponseEntity<List<ProductListingDto>> getProductListing() {
        List<ProductModel> listaProdutos = productRepository.findAll();

        List<ProductListingDto> productListingDtos = listaProdutos.stream()
                .map(this::convertToProductListingDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(productListingDtos);
    }

    private ProductListingDto convertToProductListingDto(ProductModel produto) {
        ProductListingDto dto = new ProductListingDto();
        dto.setIdProduct(produto.getIdProduct());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setValorCusto(produto.getValorCusto());
        dto.setValorVenda(produto.getValorVenda());
        dto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        dto.setMargemLucro(produto.getMargemLucro());
        return dto;
    }
}
