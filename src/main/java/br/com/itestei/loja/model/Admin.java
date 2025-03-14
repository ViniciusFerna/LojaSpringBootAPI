package br.com.itestei.loja.model;

import br.com.itestei.loja.util.HashUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity // Mapear a Entidade para o JPA
public class Admin {

	// Chave primaria
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	public void setSenha(String senha) {
		this.senha =  HashUtil.hash(senha);
	}
	
	public void setSenhaComHash(String hash) {
		this.senha = hash;
	}
	
}
