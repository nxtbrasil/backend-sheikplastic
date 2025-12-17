package com.sheikplastic.model;

import lombok.Data;
import java.io.Serializable;

// Deve implementar Serializable
@Data 
public class PessoaProdutoId implements Serializable {

    // Deve ter um construtor sem argumentos (Lombok @NoArgsConstructor)
    
    // Campo 1: Corresponde a @Id idPessoa na entidade
    private Long idPessoa;
    
    // Campo 2: Corresponde a @Id seqProduto na entidade
    private Long seqProduto;
    
    
    // O Lombok @Data gera automaticamente os métodos equals() e hashCode() 
    // que são necessários para chaves compostas.
}