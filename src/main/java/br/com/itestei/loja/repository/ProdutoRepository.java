package br.com.itestei.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itestei.loja.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	
}
