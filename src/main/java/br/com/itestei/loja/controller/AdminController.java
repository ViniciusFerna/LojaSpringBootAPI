package br.com.itestei.loja.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itestei.loja.model.Admin;
import br.com.itestei.loja.repository.AdminRepository;
import br.com.itestei.loja.util.HashUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	// variavel para persistencia do banco
	@Autowired
	private AdminRepository repAdmin;
	
	@PostMapping("/")
	public ResponseEntity<?> salvarAdmin(@RequestBody Admin admin) {
		try {
			
			if(admin.getNome().isEmpty() || admin.getEmail().isEmpty() || admin.getSenha().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha os campos corretamente");
			}
			
			Admin adminCriado = repAdmin.save(admin);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(adminCriado);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao salvar");
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> buscarAdmin() {
		try {
			List<Admin> admins = repAdmin.findAll();
			if (admins.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não há nenhum admin cadastrado");
			}
			
			return ResponseEntity.ok(admins);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro no servidor");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarUnicoAdmin(@PathVariable("id") Long id) {
		Optional<Admin> verificarExisteAdmins = repAdmin.findById(id);
		
		try {
			if (verificarExisteAdmins.isPresent()) {
				Admin a = verificarExisteAdmins.get();
				
				a.getNome();
				a.getEmail();
				a.getSenha();
				
				return ResponseEntity.ok(a);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não há nenhum admin");
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro no servidor");
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarAdmin(@PathVariable("id") Long id) {
		try {
			Optional<Admin> verificarExisteAdmins = repAdmin.findById(id);
			
			if (verificarExisteAdmins.isPresent()) {
				repAdmin.deleteById(id);
				
				return ResponseEntity.ok("Admin deletado com sucesso");
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não há nenhum admin para deletar");
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro no servidor");
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarAdmin(@PathVariable Long id, @RequestBody Admin admin) {
		try {
			Optional<Admin> ad = repAdmin.findById(id);
			
			if(ad.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin não existe");
			}
			
			Admin atualizarAdmin = ad.get();
			
			atualizarAdmin.setEmail(admin.getEmail());
			atualizarAdmin.setNome(admin.getNome());
			
			if (admin.getEmail().isEmpty() || admin.getNome().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os campos");
			}
			
			// verificar se a senha está vazia
			if (admin.getSenha().equals(HashUtil.hash(""))) {
				// pegar senha antiga
				String hash = repAdmin.findById(admin.getId()).get().getSenha();
				// atualizar a senha com a antiga senha
				admin.setSenhaComHash(hash);
			}
			
			
			
			// atulizar o objeto do banco
			repAdmin.save(atualizarAdmin);
			
			return ResponseEntity.ok("Admin atualizado com sucesso");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao atualizar");
		}
	}
	
	// login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Admin admin) {
		try {
			Admin ad = repAdmin.findByEmailAndSenha(admin.getEmail(), admin.getSenha());
			
			if (ad == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email ou senha incorretos");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body("Login realizado com sucesso");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao realizar o login");
		}
	}
	
	
	
	
	
	
	
	

	
	
	
	
}
