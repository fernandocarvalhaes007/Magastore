<a id="readme-top"></a>

<h1 align="center">
    <img style="width: 200px;" alt="Desafio 1" src="src/main/resources/static/magastore.png"/>
</h1>



# Magastore - API

## Descrição

A MagaVendas API é uma aplicação desenvolvida para fornecer serviços REST para cadastros e consultas de clientes e produtos. A aplicação permite realizar operações de CRUD em clientes, cadastro de produtos com detalhes específicos, compras de produtos por clientes, e listagem detalhada de produtos incluindo quantidade vendida, estoque e lucro.


## Fucionalidades

- CRUD de Clientes 
  - Criar, ler, atualizar e deletar clientes.

- Cadastro de Produtos 
  - Quantidade em estoque
  - Valor de custo
  - Margem de lucro

- Compra de Produtos
  - Permite a compra de produtos por um cliente.
  - Recebe o valor total da compra, que deve ser validado para não ser menor que o valor de custo.
  - Permite a aplicação de descontos.

- Listagem de Produtos
  - Quantidade de produtos vendidos
  - Quantidade em estoque
  - Lucro total obtido por produto

# Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.1
- PostgreSQL 16.3.2

# Configuração do Projeto

## Banco de Dados
  
1. Instale e configure o PostgreSQL.
<br>
<br>
2. Crie um banco de dados chamado magastoreapi
<br>
<br>

    ```bash
     CREATE DATABASE magastoreapi
    ```
   <br>
3. Configure as credenciais do banco de dados no arquivo application.properties:

    ```bash
    spring.datasource.url= jdbc:postgresql://localhost:5432/magastoreapi
    spring.datasource.username=postgres
    spring.datasource.password=sua senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
    ```

## Instalação

1. Clone o repositório:
   
    ```bash
    git clone https://github.com/fernandocarvalhaes007/Magastore.git
    cd magastoreapi 
    ```

2. Compile e execute a aplicação:
     ```bash
       mvn clean install
       mvn spring-boot:run
     ```

# Documentação da API

-- em construcao --



<p align="right">(<a href="#readme-top"> VOLTAR AO TOPO </a>)</p>