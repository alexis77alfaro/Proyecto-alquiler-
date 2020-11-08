package com.alquiler.controlador;



import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alquiler.entidades.Rol;
import com.alquiler.entidades.Usuario;
import com.alquiler.proyecto.interfaces.RolRepo;
import com.alquiler.proyecto.interfaces.UsuarioRepo;
import com.sun.el.parser.ParseException;

@Controller
public class LoginControler {
	@Autowired
	UsuarioRepo repoU;
	@Autowired
	RolRepo repoR;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="error",required = false) 
	String error,@RequestParam(value="logout",required = false) 
	String logout,  Model model, Principal principal, RedirectAttributes flash) {
		
		if (principal!=null) {
			flash.addFlashAttribute("info", "La sesión esta activa");
			return "redirect:/menu";
			
		}
		
		if (error!=null) {
			model.addAttribute("error", "Los datos ingresados son incorrectos");
		}
		if (logout!=null) {
			model.addAttribute("salida", "La sesión ha finalizado con exito");
		}
		
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	 @GetMapping("/registrar")
	    public String listar(Model modelo) {
	    	return "RegistroUsuario";
	       }
	 

	 @RequestMapping(value="/CrearRegistro", method = RequestMethod.POST)
	   public String crear(
			   @RequestParam(value = "nombre")String nombre,
			   @RequestParam(value = "apellido")String apellido,
			   @RequestParam(value = "correo")String correo,
			   @RequestParam(value = "password")String password,
			   @RequestParam(value = "dui")String dui,
			   @RequestParam(value = "dire")String dire,
			   @RequestParam(value = "licencia")String licencia)throws ParseException  {
		 
	      try {
	    	  Usuario l = new Usuario();
			  l.setNick(nombre);
		      l.setApellido(apellido);
		      l.setCorreo(correo);
		      l.setDirecion(dire);
		      l.setDui(dui);
		      l.setLicencia(licencia);
		      String bcrypt = passwordEncoder.encode(password);
		      l.setClave(bcrypt);	      
		      
             //Rol r = new Rol();    
		      //List<Rol> rol = new ArrayList<>();
		      //rol.add(r);
		      //l.setRoles(rol);
		      
		      repoU.save(l);
		      
		      
		      
		      return "redirect:/login";
		} catch (Exception e) {
			 return "eorr" + e;
		}
	      
	     
		}
}
