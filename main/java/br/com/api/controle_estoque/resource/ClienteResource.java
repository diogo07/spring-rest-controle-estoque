package br.com.api.controle_estoque.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.controle_estoque.event.RecursoCriadoEvent;
import br.com.api.controle_estoque.model.Cliente;
import br.com.api.controle_estoque.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;	
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarTodos(){
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPorCodigo(@PathVariable long codigo){
		Cliente cliente = clienteRepository.findByCodigo(codigo);
		return cliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cliente);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> cadastrar(@RequestBody @Valid Cliente cliente, HttpServletResponse response){
		Cliente clienteSalvo = clienteRepository.save(cliente);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo){
		clienteRepository.deleteById(codigo);
	}
	
}
