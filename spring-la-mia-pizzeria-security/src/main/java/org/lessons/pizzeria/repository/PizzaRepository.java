package org.lessons.pizzeria.repository;

import java.util.List;
import org.lessons.pizzeria.model.Ingrediente;
import org.lessons.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
//	Query custom
	@Query("SELECT p FROM Pizza p WHERE p.name LIKE '%'||:input||'%' ")
			
    public List<Pizza> search( String input);

	public List<Pizza> findByNameContainingIgnoreCase(String name);
	
	public Pizza removeByIngredienteId(Object ingredienti);

	public List<Ingrediente> getIngredienti(Pizza pizza);
}
