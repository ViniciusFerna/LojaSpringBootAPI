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

import br.com.itestei.loja.model.Usuario;
import br.com.itestei.loja.repository.UsuarioRepository;


	@RestController
	@RequestMapping("/usuarios")
	public class UsuarioController {
		
		@Autowired
		private UsuarioRepository repUsuario;
		
		@GetMapping("/")
		public ResponseEntity<?> buscarUsuarios() {
			try {
				List<Usuario> usuarios = repUsuario.findAll();
				
				if (usuarios.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuario encontrado!");
				}
				
				return ResponseEntity.ok(usuarios);
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
			}
		}
		
		@PostMapping("/")
		public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
			try {
				if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
					return ResponseEntity.badRequest().body("O nome não pode ser vazio");
				}
				
				if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
					return ResponseEntity.badRequest().body("O email não pode ser vazio");
				}
				
				List<Usuario> usuarioComMesmoEmail = repUsuario.findByEmail(usuario.getEmail());
				if(!usuarioComMesmoEmail.isEmpty()) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com o mesmo email");
				}
				
				Usuario novoUsuario = repUsuario.save(usuario);
				return ResponseEntity.ok(novoUsuario);
				
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
			}
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<?> DeletarUsuario(@PathVariable("id") Long id) {
			try {
				Optional<Usuario> verificarExisteUser = repUsuario.findById(id);
				
				if(verificarExisteUser.isPresent()) {
					repUsuario.deleteById(id);
					
					return ResponseEntity.ok("Usuário deletado com sucesso");
				} else {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuário encontrado");
				}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problema no servidor encontrado");
			}
		}
		
		@PutMapping("/{id}")
		public ResponseEntity<?> AtualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
			try {
				Optional<Usuario> verificaExisteUser = repUsuario.findById(id);
				
				if (verificaExisteUser.isPresent()) {
					// salvaria a atualização do produto
					Usuario u = verificaExisteUser.get();
					u.setNome(usuario.getNome());
					u.setIdade(usuario.getIdade());
					u.setEmail(usuario.getEmail());
					
					repUsuario.save(u);
					
					return ResponseEntity.ok(u);
					
					
				} else {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto foi encontrado");
				}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
			}
		}
		
		
		
		
		

		
}
