package org.lessons.pizzeria.repository;

import java.util.List;

import org.lessons.pizzeria.model.Ingrediente;
import org.lessons.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer>{
	
	public Ingrediente findByNameIgnoreCase(String name);
	
//	Query custom
	@Query("SELECT i FROM Ingrediente i WHERE i.name LIKE '%'||:input||'%' ")
			
    public List<Ingrediente> search( String input);
	
	public List<Ingrediente> findNameById(Integer id);
}


