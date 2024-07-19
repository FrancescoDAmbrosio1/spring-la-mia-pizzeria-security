package org.lessons.pizzeria.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ingredienti")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "L'inserimento dell'ingrediente è obbligatorio")
	@Column(name = "nome", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "ingrediente")
	private Set<Pizza> pizza;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Pizza> getPizza() {
		return pizza;
	}

	public void setPizza(Set<Pizza> pizza) {
		this.pizza = pizza;
	}


	
	
}
