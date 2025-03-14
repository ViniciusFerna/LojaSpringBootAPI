package br.com.itestei.loja.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itestei.loja.model.Produto;
import br.com.itestei.loja.repository.ProdutoRepository;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repProduto;
	
	// GET, POST, PUT, DELETE
	@GetMapping("/")
	public ResponseEntity<?> buscarProdutos() {
		try {
			List<Produto> produtos = repProduto.findAll();
			
			if (produtos.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum produto encontrado");
			}
			
			return ResponseEntity.ok(produtos);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
		try {
			if (produto.getNome() == null || produto.getNome().isEmpty()) {
				return ResponseEntity.badRequest().body("O nome do produto não pode ser vazio.");
			}
			
			Produto newP = repProduto.save(produto);
			
			return ResponseEntity.ok(newP);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable("id") Long id) {
		try {
			Optional<Produto> verificarExiste = repProduto.findById(id);
			
			if(verificarExiste.isPresent()) {
				// Deletar se tiver algo presente
				repProduto.deleteById(id);
				
				return ResponseEntity.ok("Produto deletado com sucesso");
			} else {
				// retornar mensagem de não encontrado
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum produto foi encontrado");
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
		}
		
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		try {
			Optional<Produto> verificaExiste = repProduto.findById(id);
			
			if (verificaExiste.isPresent()) {
				// salvaria a atualização do produto
				Produto p = verificaExiste.get();
				p.setNome(produto.getNome());
				p.setQuantidade(produto.getQuantidade());
				p.setPreco(produto.getPreco());
				
				repProduto.save(p);
				
				return ResponseEntity.ok(p);
				
				
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto foi encontrado");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor");
		}
		
	}
	
	
}

