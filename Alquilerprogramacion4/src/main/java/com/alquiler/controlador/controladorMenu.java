package com.alquiler.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alquiler.proyecto.interfaces.IVehiculo;

@Controller
public class controladorMenu{
	 @Autowired
		IVehiculo repoV;
	    @GetMapping("/menu")
	    public String listar(Model modelo) {
	        modelo.addAttribute("vehiculos", repoV.findAll());
	    	return "Menu";
	       }
	    
	  //llevar datos
		@RequestMapping(value = "/llevardatos/{id}" , method = RequestMethod.GET)
		 	public String pasar(@PathVariable("id") int id, Model modelo) {
			   modelo.addAttribute("vehi", repoV.findById(id).get());
		    return "Alquiler";
		    }
		
		
}
