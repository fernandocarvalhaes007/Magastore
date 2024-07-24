package com.magastoreapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.magastoreapi.models.ClienteModel;
import com.magastoreapi.repositories.ClienteRepository;



// Carregamento apenas dos componentes de MVC - arquitetura MODEL VIEW CONTROLLER 

@WebMvcTest(ClienteController.class)
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

           
    @Test   //  Cria um objeto ClienteModel com um ID e nome (Cliente Teste).
    void testListarClientes() throws Exception {
        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente(UUID.randomUUID());
        cliente.setNome("Cliente Teste");


         //  Configura o mock para retornar uma lista com o cliente criado quando findAll() for chamado.
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));


        //Simula uma requisição GET para /clientes.
        //Verifica se o status da resposta é 200 OK e se o nome do cliente na resposta é "Cliente Teste".
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cliente Teste"));
    }



 // ____________________________________________________________________________________________ //


       //Cria um cliente com um ID específico e nome
    @Test
    void testObterCliente() throws Exception {
        UUID id = UUID.randomUUID();
        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente(id);
        cliente.setNome("Cliente Teste");


        // Configura o mock para retornar o cliente criado quando findById(id) for chamado
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));


        // Simula uma requisição GET para /clientes/{id} com o ID específico.
        mockMvc.perform(get("/clientes/{id}", id))

        //Verifica se o status da resposta é 200 OK e se o nome do cliente na resposta é "Cliente Teste"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente Teste"));
    }



 // ____________________________________________________________________________________________ //


    @Test
    void testObterCliente_NotFound() throws Exception {
        UUID id = UUID.randomUUID();


        // Configura o mock para retornar Optional.empty() quando findById(id) for chamado.
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());


        //Simula uma requisição GET para /clientes/{id} com o ID específico.
        mockMvc.perform(get("/clientes/{id}", id))

        //Verifica se o status da resposta é 404 Not Found, pois o cliente não foi encontrado.
                .andExpect(status().isNotFound());
    }



  // ____________________________________________________________________________________________ //


    @Test   //  Cria um cliente com um ID e nome.
    void testCriarCliente() throws Exception {
        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente(UUID.randomUUID());
        cliente.setNome("Cliente Teste");

        // Configura o mock para retornar o cliente criado quando save() for chamado com qualquer objeto ClienteModel
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(cliente);


        // Simula uma requisição POST para /clientes com o corpo da requisição em JSON
        mockMvc.perform(post("/clientes")
                .contentType("application/json")
                .content("{\"nome\":\"Cliente Teste\"}"))

                //Verifica se o status da resposta é 200 OK e se o nome do cliente na resposta é "Cliente Teste"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente Teste"));
    }


 // ____________________________________________________________________________________________ //


    @Test  //Cria um cliente com um ID e nome
    void testAtualizarCliente() throws Exception {
        UUID id = UUID.randomUUID();
        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente(id);
        cliente.setNome("Cliente Teste");


        // Configura o mock para indicar que o cliente existe e retornar o cliente atualizado quando save() for chamado.
        when(clienteRepository.existsById(id)).thenReturn(true);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(cliente);


        // Simula uma requisição PUT para /clientes/{id} com o corpo da requisição em JSON.
        mockMvc.perform(put("/clientes/{id}", id)
                .contentType("application/json")
                .content("{\"nome\":\"Cliente Atualizado\"}"))

                //Verifica se o status da resposta é 200 OK e se o nome do cliente atualizado na resposta é "Cliente Atualizado"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente Atualizado"));
    }

 // ____________________________________________________________________________________________ //


    @Test
    void testAtualizarCliente_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        // Configura o mock para indicar que o cliente não existe com o ID fornecido.
        when(clienteRepository.existsById(id)).thenReturn(false);

        // Simula uma requisição PUT para /clientes/{id} com o corpo da requisição em JSON
        mockMvc.perform(put("/clientes/{id}", id)
                .contentType("application/json")
                .content("{\"nome\":\"Cliente Teste\"}"))

                //Verifica se o status da resposta é 404 Not Found, pois o cliente não foi encontrado para atualização
                .andExpect(status().isNotFound());
    }


 // ____________________________________________________________________________________________ //    

    
    @Test
    void testExcluirCliente() throws Exception {
        UUID id = UUID.randomUUID();

         //Configura o mock para indicar que o cliente existe com o ID fornecido.
        when(clienteRepository.existsById(id)).thenReturn(true);

        // Simula uma requisição DELETE para /clientes/{id}
        mockMvc.perform(delete("/clientes/{id}", id))

        //Verifica se o status da resposta é 204 No Content
                .andExpect(status().isNoContent());
    }


  // ____________________________________________________________________________________________ //
      

    @Test
    void testExcluirCliente_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

       // Configura o mock para indicar que o cliente não existe com o ID fornecido.
        when(clienteRepository.existsById(id)).thenReturn(false);

        //Simula uma requisição DELETE para /clientes/{id}
        mockMvc.perform(delete("/clientes/{id}", id))

        //Verifica se o status da resposta é 404 Not Found, pois o cliente não foi encontrado para exclusão
                .andExpect(status().isNotFound());
    }
}
