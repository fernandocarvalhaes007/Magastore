package com.magastoreapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_FATURAMENTOS")
public class FaturamentoModel extends RepresentationModel<FaturamentoModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFaturamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")
    private ClienteModel cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto", referencedColumnName = "idProduct")
    private ProductModel product;

    private BigDecimal valorPedido;
    private BigDecimal valorDesconto;
    private BigDecimal valorVenda;
    private LocalDateTime dataFaturamento;
    
    private int quantidadeVendida;


    public UUID getIdFaturamento() {
        return idFaturamento;
    }

    public void setIdFaturamento(UUID idFaturamento) {
        this.idFaturamento = idFaturamento;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDateTime getDataFaturamento() {
        return dataFaturamento;
    }

    public void setDataFaturamento(LocalDateTime dataFaturamento) {
        this.dataFaturamento = dataFaturamento;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }
}
