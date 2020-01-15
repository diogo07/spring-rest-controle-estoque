package br.com.api.controle_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.controle_estoque.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	public Cliente findByCodigo(long codigo);
}
