package com.magastoreapi.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magastoreapi.dtos.ItemFaturamentoDto;
import com.magastoreapi.models.ClienteModel;
import com.magastoreapi.models.FaturamentoModel;
import com.magastoreapi.models.ProductModel;
import com.magastoreapi.repositories.FaturamentoRepository;

@Service
public class FaturamentoService {

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    public FaturamentoModel processarFaturamento(ClienteModel cliente, ProductModel produto, ItemFaturamentoDto dto) {
        FaturamentoModel faturamento = new FaturamentoModel();
        faturamento.setCliente(cliente);
        faturamento.setProduct(produto);
        faturamento.setValorPedido(dto.getValorPedido());
        faturamento.setValorDesconto(dto.getValorDesconto());
        faturamento.setValorVenda(dto.getValorPedido().subtract(dto.getValorDesconto()));
        faturamento.setDataFaturamento(LocalDateTime.now());
        faturamento.setQuantidadeVendida(dto.getQuantidade());

        return faturamentoRepository.save(faturamento);
    }
}
