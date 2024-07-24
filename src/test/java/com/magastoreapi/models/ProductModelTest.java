package com.magastoreapi.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductModelTest {

    
  // ____________________________________________________________________________________________ //

    @Test
    void testCalcularMargemLucro_ValorVendaMaiorQueValorCusto() {

        
        // Configuração do estado inicial do Objeto e definindo os valores para o teste
        ProductModel produto = new ProductModel();
        produto.setValorCusto(new BigDecimal("50.00"));
        produto.setValorVenda(new BigDecimal("75.00"));

        // Executa o método 
        produto.calcularMargemLucro();

        // Verifica se o valor retorna como o esperado pela aplicação
        assertEquals(new BigDecimal("25.00"), produto.getMargemLucro(), "A margem de lucro deve ser 25.00");
    }



  // ____________________________________________________________________________________________ //

    @Test
    void testCalcularMargemLucro_ValorVendaMenorQueValorCusto() {
        
        // Configuração do estado inicial do Objeto e definindo os valores para o teste
        ProductModel produto = new ProductModel();
        produto.setValorCusto(new BigDecimal("75.00"));
        produto.setValorVenda(new BigDecimal("50.00"));

        // Executa o método
        produto.calcularMargemLucro();

        // Verifica se o valor retorna como o esperado pela aplicação
        assertEquals(BigDecimal.ZERO, produto.getMargemLucro(), "A margem de lucro deve ser 0.00");
    }


  // ____________________________________________________________________________________________ //


    @Test
    void testCalcularMargemLucro_ValorVendaNull() {
        
        // Configuração do estado inicial do Objeto e definindo os valores para o teste
        ProductModel produto = new ProductModel();
        produto.setValorCusto(new BigDecimal("50.00"));
        produto.setValorVenda(null); 
                 
        // Executa o método
        produto.calcularMargemLucro();

        // Verifica se o valor retorna como o esperado pela aplicação
        assertEquals(BigDecimal.ZERO, produto.getMargemLucro(), "A margem de lucro deve ser 0.00");
    }


  // ____________________________________________________________________________________________ //

    @Test
    void testCalcularMargemLucro_ValorCustoNull() {
        
        
        // Configuração do estado inicial do Objeto e definindo os valores para o teste
        ProductModel produto = new ProductModel();
        produto.setValorCusto(null);
        produto.setValorVenda(new BigDecimal("75.00"));

        // Executa o método
        produto.calcularMargemLucro();

        // Verifica se o valor retorna como o esperado pela aplicação
        assertEquals(BigDecimal.ZERO, produto.getMargemLucro(), "A margem de lucro deve ser 0.00");
    }
}
