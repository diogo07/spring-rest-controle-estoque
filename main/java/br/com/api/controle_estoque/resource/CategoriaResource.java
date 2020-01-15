package br.com.api.controle_estoque.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.controle_estoque.event.RecursoCriadoEvent;
import br.com.api.controle_estoque.model.Categoria;
import br.com.api.controle_estoque.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;		
	
	@GetMapping
	public ResponseEntity<List<Categoria>> listarTodas(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias); 
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable long codigo) {
		Categoria categoria = categoriaRepository.findById(codigo);
		return categoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> salvar(@RequestBody @Valid Categoria categoria, HttpServletResponse response) {
		Categoria categoriaInserida = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaInserida.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaInserida);
	}

}
