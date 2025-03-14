package br.com.itestei.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itestei.loja.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	// SELECT * FROM Admin WHERE id = {id}
	// .findByID
	public Admin findByEmailAndSenha(String email, String senha);
	
}
