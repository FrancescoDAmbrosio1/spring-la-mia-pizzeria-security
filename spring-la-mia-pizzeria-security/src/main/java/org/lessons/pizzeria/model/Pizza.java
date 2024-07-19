package org.lessons.pizzeria.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "pizza")
public class Pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Il nome non può essere vuoto")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotBlank(message = "Inserire il percorso della foto")
	@Column(name = " foto", nullable = false)
	private String url;
	
	@Column(name = "prezzo", nullable = false)
	private double price; 
	
	@OneToMany(mappedBy = "pizza")
	public List<Offerta> offerte;
	
	@ManyToMany()
	@JoinTable(
	name = "pizza_ingrediente",
	joinColumns = @JoinColumn(name = "pizza_id"),
	inverseJoinColumns = @JoinColumn (name = "ingrediente_id")
	)
	
	private Set<Ingrediente> ingrediente;
	
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Offerta> getOfferte() {
		return offerte;
	}

	public void setOfferte(List<Offerta> offerte) {
		this.offerte = offerte;
	}

	public Set<Ingrediente> getIngredienti() {
		return ingrediente;
	}

	public void setIngredienti(Set<Ingrediente> ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	
}
