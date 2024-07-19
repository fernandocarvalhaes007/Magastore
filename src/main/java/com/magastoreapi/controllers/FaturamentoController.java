package com.magastoreapi.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magastoreapi.dtos.FaturamentoDto;
import com.magastoreapi.models.ClienteModel;
import com.magastoreapi.models.ProductModel;
import com.magastoreapi.models.FaturamentoModel;
import com.magastoreapi.repositories.ClienteRepository;
import com.magastoreapi.repositories.ProductRepository;
import com.magastoreapi.repositories.FaturamentoRepository;
import com.magastoreapi.services.FaturamentoService;

@RestController
@RequestMapping("/faturamentos")
public class FaturamentoController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FaturamentoService faturamentoService;

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    @PostMapping
    public ResponseEntity<?> criarCompra(@RequestBody FaturamentoDto faturamentoDto) {
        UUID clienteId = faturamentoDto.getClienteId();
        UUID produtoId = faturamentoDto.getProdutoId();
    
        if (clienteId == null || produtoId == null) {
            return ResponseEntity.badRequest().body("Cliente ID e Produto ID não podem ser nulos");
        }
    
        ClienteModel cliente = clienteRepository.findById(clienteId).orElse(null);
        ProductModel produto = productRepository.findById(produtoId).orElse(null);
    
        if (cliente == null) {
            return ResponseEntity.badRequest().body("Cliente não encontrado");
        }
    
        if (produto == null) {
            return ResponseEntity.badRequest().body("Produto não encontrado");
        }
    
        if (faturamentoDto.getValorPedido().compareTo(produto.getValorCusto()) < 0) {
            return ResponseEntity.badRequest().body("Valor do pedido não pode ser menor que o valor de custo do produto");
        }
    
        int quantidadeVendida = faturamentoDto.getQuantidadeVendida();
        if (produto.getQuantidadeEstoque() < quantidadeVendida) {
            return ResponseEntity.badRequest().body("Estoque insuficiente para realizar a venda");
        }
    
        BigDecimal valorPedido = faturamentoDto.getValorPedido();
        BigDecimal valorDesconto = faturamentoDto.getValorDesconto();
        BigDecimal valorVendaCalculado = valorPedido.subtract(valorDesconto);
        produto.setValorVenda(valorVendaCalculado);
    
        produto.calcularMargemLucro();
        productRepository.save(produto);
    
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeVendida);
        productRepository.save(produto);
        
        try {
            FaturamentoModel faturamento = faturamentoService.processarFaturamento(cliente, produto, faturamentoDto);
            return ResponseEntity.ok(faturamento);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar a compra: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<FaturamentoModel>> obterTodosFaturamentos() {
        List<FaturamentoModel> faturamentos = faturamentoRepository.findAll();
        return ResponseEntity.ok(faturamentos);
    }
}
