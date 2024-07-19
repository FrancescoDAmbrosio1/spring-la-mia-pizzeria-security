package org.lessons.pizzeria.controller;

import org.lessons.pizzeria.model.Offerta;
import org.lessons.pizzeria.repository.OffertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

	@Autowired
	private OffertaRepository offertaRepository;
	


	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute("offerta", new Offerta());
		
		return "/offerte/create";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("offerta") Offerta offerta, 
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			return"/offerte/create";
			
		}
		
		offertaRepository.save(offerta);
		
		return "redirect:/pizze/show/" + offerta.getPizza().getId();
		
	}
	
}
