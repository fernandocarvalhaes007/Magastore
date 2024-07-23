<a id="readme-top"></a>

<h1 align="center">
    <img style="width: 200px;" alt="Desafio 1" src="src/main/resources/static/magastore.png"/>
</h1>



# Magastore - API

## Descrição

A Magastore API é uma aplicação desenvolvida para fornecer serviços REST para cadastros e consultas de clientes e produtos. A aplicação permite realizar operações de CRUD em clientes, cadastro de produtos com detalhes específicos, compras de produtos por clientes, e listagem detalhada de produtos incluindo quantidade vendida, estoque e lucro.


## Funcionalidades

- CRUD de Clientes 
  - Criar, ler, atualizar e deletar clientes.

- Cadastro de Produtos 
  - Quantidade em estoque
  - Valor de custo


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
- Maven

# Configuração do Projeto

## Estrutura do Projeto


- `com.magastoreapi.controllers`: Contém os controladores REST que expõem os endpoints da API.
- `com.magastoreapi.models`: Contém as classes de modelo que representam as entidades do banco de dados.
- `com.magastoreapi.dtos`: Contém as classes de Data Transfer Object (DTO) usadas para transferir dados entre o cliente e o servidor.
- `com.magastoreapi.repositories`: Contém as interfaces de repositório para acesso aos dados.
- `com.magastoreapi.services`: Contém as classes de serviço que implementam a lógica de negócio.


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

- A aplicação estará disponível em http://localhost:8080


## Endpoints da API
- Cadastro de clientes

  - GET /clientes: Lista todos os clientes.
  - POST /clientes: Cria um novo cliente.
  - GET /clientes/{id}: Obtém um cliente específico pelo ID.
  - PUT /clientes/{id}: Atualiza um cliente específico pelo ID.
  - DELETE /clientes/{id}: Remove um cliente específico pelo ID.

- Cadastro de produtos
  - GET /produtos: Lista todos os produtos.
  - POST /produtos: Cria um novo produto.
  - GET /produtos/{id}: Obtém um produto específico pelo ID.
  - PUT /produtos/{id}: Atualiza um produto específico pelo ID.
  - DELETE /produtos/{id}: Remove um produto específico pelo ID.

- Faturamentos
  - POST /faturamentos: Registra uma nova compra.
  - GET /faturamentos: Lista todos os faturamentos.

- Listagem de produtos/estoque
  - GET /lista-de-produtos: Lista produtos com detalhes de vendas, estoque e lucro

# Exemplo de Requisição

## Criar um Cliente
```bash
 POST /clientes
 Content-Type: application/json

  {
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "telefone": "123456789"
                          }
```

## Criar um Produto 


```bash

 POST /produtos
 Content-Type: application/json

  {
    "nome": "Dell",
    "descricao": " INSPRON 433, WIN10, SSD240GB, RAM 8GB",
    "valorCusto": 3000.00,
    "quantidadeEstoque": 1
                      }

```

## Registrar uma Compra


```bash

 POST /faturamentos
 Content-Type: application/json

 {
   "clienteId": "351b44c1-14ab-4d69-9ded-0655dbe06781",
   "itens": [
             {
              "produtoId": "814a1b64-bc8a-4dcd-b3c0-e39ca9586b71",
              "quantidade": 1,
              "valorPedido": 5000.00,
              "valorDesconto": 300.00
             },
              {
                "produtoId": "de4f628d-9bca-45cd-a1c5-78c12a46b10e",
                "quantidade": 2,
                "valorPedido": 6000.00,
                "valorDesconto": 300.00
              }
           ]
        }

```




<p align="right">(<a href="#readme-top"> VOLTAR AO TOPO </a>)</p>