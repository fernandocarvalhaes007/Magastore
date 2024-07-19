package com.magastoreapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import com.magastoreapi.dtos.FaturamentoDto;
import com.magastoreapi.models.ClienteModel;
import com.magastoreapi.models.FaturamentoModel;
import com.magastoreapi.models.ProductModel;
import com.magastoreapi.repositories.FaturamentoRepository;

@Service
public class FaturamentoService {

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    public FaturamentoModel processarFaturamento(ClienteModel cliente, ProductModel produto, FaturamentoDto dto) {
        FaturamentoModel faturamento = new FaturamentoModel();
        faturamento.setCliente(cliente);
        faturamento.setProduct(produto);
        faturamento.setValorPedido(dto.getValorPedido());
        faturamento.setValorDesconto(dto.getValorDesconto());
        faturamento.setValorVenda(dto.getValorVenda() != null ? dto.getValorVenda() : BigDecimal.ZERO);
        faturamento.setDataFaturamento(dto.getDataFaturamento());
        faturamento.setQuantidadeVendida(dto.getQuantidadeVendida());

        
        return faturamentoRepository.save(faturamento); 
    }
}
