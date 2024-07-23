package com.magastoreapi.dtos;

import java.util.List;
import java.util.UUID;

public class FaturamentoDto {
    private UUID clienteId;
    private List<ItemFaturamentoDto> itens;

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemFaturamentoDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemFaturamentoDto> itens) {
        this.itens = itens;
    }
}
