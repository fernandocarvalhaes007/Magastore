package com.magastoreapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;
    private String name;
    private String description;
    private BigDecimal costValue;
    private BigDecimal saleValue;
    private int stockQuantity;
    private LocalDateTime entryDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FaturamentoModel> faturamentos;

    // Getters and Setters

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCostValue() {
        return costValue;
    }

    public void setCostValue(BigDecimal costValue) {
        this.costValue = costValue;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(BigDecimal saleValue) {
        this.saleValue = saleValue;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Entity
    @Table(name = "TB_CLIENTES")
    public static class ClienteModel extends RepresentationModel<ClienteModel> implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID idCliente;
        private String nome;
        private String email;
        private String telefone;
        private String cpf;
        private LocalDateTime dataCadastro;

        @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<FaturamentoModel> faturamentos;

        // Getters and Setters

        public UUID getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(UUID idCliente) {
            this.idCliente = idCliente;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public LocalDateTime getDataCadastro() {
            return dataCadastro;
        }

        public void setDataCadastro(LocalDateTime dataCadastro) {
            this.dataCadastro = dataCadastro;
        }
    }

    @Entity
    @Table(name = "TB_FATURAMENTO")
    public static class FaturamentoModel extends RepresentationModel<FaturamentoModel> implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID idFaturamento;
        private String nome;
        private String cpf;
        private String telefone;
        private String numeroNf;
        private LocalDateTime dataVenda;
        private UUID codigoProduto;
        private String descricao;
        private String produto;
        private BigDecimal valorVenda;
        private BigDecimal valorDesconto;

        @ManyToOne
        @JoinColumn(name = "idProduct")
        private ProductModel product;

        @ManyToOne
        @JoinColumn(name = "idCliente")
        private ClienteModel cliente;

        // Getters and Setters

        public UUID getIdFaturamento() {
            return idFaturamento;
        }

        public void setIdFaturamento(UUID idFaturamento) {
            this.idFaturamento = idFaturamento;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getNumeroNf() {
            return numeroNf;
        }

        public void setNumeroNf(String numeroNf) {
            this.numeroNf = numeroNf;
        }

        public LocalDateTime getDataVenda() {
            return dataVenda;
        }

        public void setDataVenda(LocalDateTime dataVenda) {
            this.dataVenda = dataVenda;
        }

        public UUID getCodigoProduto() {
            return codigoProduto;
        }

        public void setCodigoProduto(UUID codigoProduto) {
            this.codigoProduto = codigoProduto;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getProduto() {
            return produto;
        }

        public void setProduto(String produto) {
            this.produto = produto;
        }

        public BigDecimal getValorVenda() {
            return valorVenda;
        }

        public void setValorVenda(BigDecimal valorVenda) {
            this.valorVenda = valorVenda;
        }

        public BigDecimal getValorDesconto() {
            return valorDesconto;
        }

        public void setValorDesconto(BigDecimal valorDesconto) {
            this.valorDesconto = valorDesconto;
        }

        public ProductModel getProduct() {
            return product;
        }

        public void setProduct(ProductModel product) {
            this.product = product;
        }

        public ClienteModel getCliente() {
            return cliente;
        }

        public void setCliente(ClienteModel cliente) {
            this.cliente = cliente;
        }
    }
}
