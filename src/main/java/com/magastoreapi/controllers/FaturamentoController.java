package com.magastoreapi.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.magastoreapi.dtos.FaturamentoDto;
import com.magastoreapi.dtos.ItemFaturamentoDto;
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

        if (clienteId == null) {
            return ResponseEntity.badRequest().body("Cliente ID não pode ser nulo");
        }

        ClienteModel cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            return ResponseEntity.badRequest().body("Cliente não encontrado");
        }



        for (ItemFaturamentoDto item : faturamentoDto.getItens()) {
            UUID produtoId = item.getProdutoId();
            if (produtoId == null) {
                return ResponseEntity.badRequest().body("Produto ID não pode ser nulo");
            }

            ProductModel produto = productRepository.findById(produtoId).orElse(null);
            if (produto == null) {
                return ResponseEntity.badRequest().body("Produto não encontrado");
            }

            if (item.getValorPedido().compareTo(produto.getValorCusto()) < 0) {
                return ResponseEntity.badRequest().body("Valor do pedido não pode ser menor que o valor de custo do produto");
            }

            int quantidadeVendida = item.getQuantidade();
            if (produto.getQuantidadeEstoque() < quantidadeVendida) {
                return ResponseEntity.badRequest().body("Estoque insuficiente para realizar a venda");
            }

            BigDecimal valorPedido = item.getValorPedido();
            BigDecimal valorDesconto = item.getValorDesconto();
            BigDecimal valorVendaCalculado = valorPedido.subtract(valorDesconto);

            
            
            produto.setValorVenda(valorVendaCalculado);
            produto.calcularMargemLucro();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeVendida);
            productRepository.save(produto);

            try {
                FaturamentoModel faturamento = faturamentoService.processarFaturamento(cliente, produto, item);
                faturamentoRepository.save(faturamento);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erro ao criar a compra: " + e.getMessage());
            }
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteId).toUri();

        return ResponseEntity.created(location).body("Compra realizada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<FaturamentoModel>> obterTodosFaturamentos() {
        List<FaturamentoModel> faturamentos = faturamentoRepository.findAll();
        return ResponseEntity.ok(faturamentos);
    }
}
