package br.com.itestei.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itestei.loja.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findByEmail(String email);
	
}
