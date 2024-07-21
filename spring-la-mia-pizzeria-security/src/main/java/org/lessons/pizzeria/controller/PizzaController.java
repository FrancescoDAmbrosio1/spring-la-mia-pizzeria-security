package org.lessons.pizzeria.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.lessons.pizzeria.model.Ingrediente;
import org.lessons.pizzeria.model.Offerta;
import org.lessons.pizzeria.model.Pizza;
import org.lessons.pizzeria.repository.IngredienteRepository;
import org.lessons.pizzeria.repository.OffertaRepository;
import org.lessons.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {
	
	@Autowired
	private PizzaRepository pizzaRepository;
	
	@Autowired
	private OffertaRepository offertaRepository;
	
	@Autowired IngredienteRepository ingredienteRepository;
	
	@GetMapping
	public String index(Model model) {
	        	
	        	List<Pizza> listaPizze = new ArrayList<Pizza>();
	    		
	    		listaPizze = pizzaRepository.findAll();
	    		
	    		model.addAttribute("list", listaPizze);
	    		
	        return "/pizze/index";
	    }
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer pizzaId, Model model) {
		
		model.addAttribute("pizza", pizzaRepository.getReferenceById(pizzaId));
		
		return "pizze/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute("pizza", new Pizza());
		
		model.addAttribute("listaIngredienti", ingredienteRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
		
		model.addAttribute("ingrediente", new Ingrediente());
		
		return "/pizze/create";
	}
	
	@PostMapping("create")
	public String store(@Valid @ModelAttribute("pizza") Pizza pizza, 
			BindingResult bindingResult, Model model) {
		
		if(pizza.getPrice() <= 0) {
			
			bindingResult.addError(new ObjectError("pizza.price", "Il prezzo della pizza è obbligatorio e maggiore di 0"));
		
		}
		
		if(bindingResult.hasErrors()) {
			
			return "pizze/create";
		}
		
		pizzaRepository.save(pizza);
		
		return "redirect:/pizze";
	}
	
	@GetMapping("/search")
	public String search(@Param("input") String input, Model model) {

		List<Pizza> list = new ArrayList<Pizza>();
		
		if(!input.isEmpty()) {
			
			list = pizzaRepository.search(input);
			
		} 
			
		model.addAttribute("list", list);	
		
		return "/pizze/search";
		
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("pizza", pizzaRepository.getReferenceById(id));
		
		model.addAttribute("listaIngredienti", ingredienteRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
		
		model.addAttribute("ingrediente", ingredienteRepository.findById(id));
		
		return "/pizze/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza pizza,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			return "/pizze/edit";
		}
		
		pizzaRepository.save(pizza);
		
		return "redirect:/pizze";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		
//		----Non necessario quando impostato method CASCADE sulle foreign keys del database-----
		
//		Pizza pizza = pizzaRepository.findById(id).get();				
//		
//		for(Ingrediente ingredienti : pizza.getIngredienti()) {					
//			
//			ingredienti.getPizza().remove(pizza);		}
//		
		
		pizzaRepository.deleteById(id);
		
		return "redirect:/pizze";
	}
	
	
	@GetMapping("/show/{id}/offerte")
	public String offerte(@PathVariable("id") Integer id, Model model) {
		
		Pizza pizza = pizzaRepository.findById(id).get();
		
		Offerta offerta = new Offerta();
		
		offerta.setStartDate(LocalDate.now());
		
		offerta.setPizza(pizza);
		
		model.addAttribute("offerta", offerta);

		
		return "offerte/create";
	}
	
	@PostMapping("/offerte/create")
	public String createOfferta(
			@Valid @ModelAttribute("offerta") Offerta offerta, 
			BindingResult bindingResult, Model model) {
		
//		System.out.println(offerta.getPizza().getId());
		if(bindingResult.hasErrors()) {
			
			return"/offerte/create";
			
		}
		
		offertaRepository.save(offerta);
		
		
		return "redirect:/pizze/show/" + offerta.getPizza().getId();
		
	}
	
}
	
