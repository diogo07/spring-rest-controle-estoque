package br.com.api.controle_estoque.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.api.controle_estoque.model.Categoria;
import br.com.api.controle_estoque.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
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
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaInserida = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(categoriaInserida.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
