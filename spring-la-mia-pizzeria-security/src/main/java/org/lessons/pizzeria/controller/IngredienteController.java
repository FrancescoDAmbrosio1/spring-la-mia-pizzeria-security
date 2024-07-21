package org.lessons.pizzeria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.lessons.pizzeria.model.Ingrediente;
import org.lessons.pizzeria.repository.IngredienteRepository;
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
@RequestMapping("/ingredienti")
public class IngredienteController {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@GetMapping
	public String index(Model model) {
		
	    		
	    		model.addAttribute("list", ingredienteRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
	    		
	    		model.addAttribute("ingrediente", new Ingrediente());
	    		
	        return "/ingredienti/index";
	    }
		

	@PostMapping("create")
	public String store(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, 
			BindingResult bindingResult, Model model) {
		

		if(ingrediente.getName() != null) {
			Ingrediente ingredienteDaCercare = 
					ingredienteRepository.findByNameIgnoreCase(ingrediente.getName());
			if(ingredienteDaCercare != null) {
				bindingResult.addError(new ObjectError("IngredientError", 
						"L'ingrediente inserito esiste già"));
				
			}
			
		}
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("list", ingredienteRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    		
    		model.addAttribute("ingrediente", new Ingrediente());
    		
			return "ingredienti/index";
		}
		
		ingredienteRepository.save(ingrediente);
		
		return "redirect:/ingredienti";
	
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {

//		----Non necessario quando impostato method CASCADE sulle foreign keys del database-----
		
//		Ingrediente i = ingredienteRepository.findById(id).get();
//		
//		for (Pizza pizza : i.getPizza()) {
//			
//			pizza.getIngredienti().remove(i);
//		
//		}
		
		ingredienteRepository.deleteById(id);
		
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/search")
	public String search(@Param("input") String input, Model model) {

		List<Ingrediente> ingredienteSearch = new ArrayList<Ingrediente>();
		
		if(!input.isEmpty()) {
			
			ingredienteSearch = ingredienteRepository.search(input);
			
		} 
			
		model.addAttribute("listSearch", ingredienteSearch);	
		
		return "/ingrediente/index";
		
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		Optional<Ingrediente> ingredienteEdit;
		
		model.addAttribute("ingrediente", ingredienteEdit = ingredienteRepository.findById(id));
		
		return "/ingredienti";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ingrediente") Ingrediente ingredienteEdit,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			return "/ingredienti";
		}
		
		ingredienteRepository.save(ingredienteEdit);
		
		return "redirect:/ingredienti";
	}
	
}

	

