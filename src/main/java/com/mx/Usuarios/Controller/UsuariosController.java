package com.mx.Usuarios.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Usuarios.Dominio.Usuarios;
import com.mx.Usuarios.Service.UsuariosService;

@RestController
@RequestMapping("usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuariosService service;
	
	@GetMapping("listar")
	public ResponseEntity<List<Usuarios>> listar(){
		List<Usuarios> lista = service.listar();
		
		if(lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}	
	}
	
	@PostMapping("guardar")
	public ResponseEntity<?> guardar(@RequestBody Usuarios u){
		Usuarios aux = service.buscar(u.getId());
		
		if(aux == null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(u));
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error: Ese ID ya esiste , intenta con otro");
		}
	}
	
	@GetMapping("buscar/{id}")
	public ResponseEntity<Usuarios> buscar (@PathVariable int id){
		Usuarios aux = service.buscar(id);
		if(aux == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(aux);
		}
	}
	
	@PutMapping("editar")
	public ResponseEntity<Usuarios> editar(@RequestBody Usuarios u){
		return ResponseEntity.status(HttpStatus.OK).body(service.editar(u));
	}
	
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<String> eliminar (@PathVariable int id){
		service.eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Mensaje: Eliminacion exitosa!");
	}
	
	@GetMapping("buscarNombre/{nombre}")
	public ResponseEntity<Usuarios> buscarPorNombre(@PathVariable String nombre){
		Usuarios aux = service.buscarPorNombre(nombre);
		
		if(aux == null ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(aux);
		}
	}
	
	@GetMapping ("buscarStatus")
	public ResponseEntity<List<Usuarios>> buscarPorStatus(@RequestParam String status){
		List<Usuarios> lista = service.buscarPorStatus(status);

		if(lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}
	}
	
}
