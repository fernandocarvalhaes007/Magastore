package com.magastoreapi.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magastoreapi.dtos.ProductDto;
import com.magastoreapi.models.ClienteModel;
import com.magastoreapi.models.FaturamentoModel;
import com.magastoreapi.models.ProductModel;
import com.magastoreapi.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductDto productDto) {
        ProductModel product = new ProductModel();
        product.setNome(productDto.getNome());
        product.setDescricao(productDto.getDescricao());
        product.setValorCusto(productDto.getValorCusto());
        product.setValorVenda(productDto.getValorVenda());
        product.setQuantidadeEstoque(productDto.getQuantidadeEstoque());
        product.setDataEntrada(LocalDateTime.now());
        product.setDataAtualizacao(LocalDateTime.now());
        product.calcularMargemLucro(); 
        ProductModel savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductModel> updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto) {
        Optional<ProductModel> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductModel product = optionalProduct.get();
            product.setNome(productDto.getNome());
            product.setDescricao(productDto.getDescricao());
            product.setValorCusto(productDto.getValorCusto());
            product.setValorVenda(productDto.getValorVenda());
            product.setQuantidadeEstoque(productDto.getQuantidadeEstoque());
            product.setDataAtualizacao(LocalDateTime.now());
            product.calcularMargemLucro(); 
            ProductModel updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/compra")
    @Transactional
    public ResponseEntity<String> purchaseProduct(@RequestBody FaturamentoModel faturamento) {
        Optional<ProductModel> optionalProduct = productRepository.findById(faturamento.getProduct().getIdProduct());
        if (optionalProduct.isPresent()) {
            ProductModel product = optionalProduct.get();
            ClienteModel cliente = faturamento.getCliente(); 
            if (faturamento.getValorPedido().compareTo(product.getValorCusto()) < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor total da compra não pode ser menor que o valor de custo");
            }
            product.setQuantidadeEstoque(product.getQuantidadeEstoque() - faturamento.getQuantidadeVendida());
            product.setDataAtualizacao(LocalDateTime.now());
            productRepository.save(product);
            return ResponseEntity.ok("Compra realizada com sucesso pelo cliente: " + cliente.getNome());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }
}
