package br.com.api.controle_estoque.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.api.controle_estoque.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	public Categoria findById(long codigo);
	

}
