package br.com.itestei.loja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // Identificar que é uma entidade ( modelo do banco)
@Data // Lombok para facilitar get and set
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	private double preco;
	private String nome;
	private int quantidade;
	
}
