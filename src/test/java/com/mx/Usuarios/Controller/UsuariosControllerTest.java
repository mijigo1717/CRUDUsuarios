package com.mx.Usuarios.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.mx.Usuarios.Dominio.Usuarios;
import com.mx.Usuarios.Service.UsuariosService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UsuariosController.class)//Levanta solo el controlador
class UsuariosControllerTest {

	@Autowired
	//simula las peticiones HTTP
	private MockMvc mockMvc;
	
	@MockitoBean
	//Creamos un servicio falso para poder controlar las respuestas
	private UsuariosService service;
	
	//Objeto de serializacion
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testListar() throws Exception{
		//Datos simulados
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		Usuarios usr2 = new Usuarios (2, "nombre Update", "juan@correo.com", "Disable", "Puebla");
		
		//Simulacion del service
		when(service.listar()).thenReturn(Arrays.asList(usr1, usr2));
		
		//Peticion Get
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/listar"))
		.andExpect(status().isOk())//Esperamos un status 200 de la respuessta
		//Validamos que la lista tenga almenos 2 registros 
		.andExpect(jsonPath("$.length()").value(2));
	}
	
	@Test
	void testGuardar() throws Exception{
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		
		//Simulando el proceso de guardar del service

		when(service.guardar(any(Usuarios.class))).thenReturn(usr1);
		
		//Peticiojn Post
		mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/guardar")
				//El contenido de la aplicacion sera tipo json
				.contentType(MediaType.APPLICATION_JSON)
				//El cuerpo es serializado con mapper de java a json
				.content(mapper.writeValueAsString(usr1)))
		//Espero un 201 en la respuesta
		.andExpect(status().isCreated())
		//y validamos que el nombre del objeto de la respuest sea "Juan"
		.andExpect(jsonPath("$.nombre").value("Juan"));
	}
	
	@Test
	void testBuscar() throws Exception{
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		
		when(service.buscar(1)).thenReturn(usr1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/buscar/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nombre").value("Juan"));
	}
	
	@Test
	void testEditarUsuarioController() throws Exception {
	    // Datos simulados
	    Usuarios usuarioModificado = new Usuarios(1, "Juan", "juan@correo.com", "Enable", "Puebla");
	    
	    // Simulamos que cuando el controlador llame a 'service.editar', el servicio responderá con éxito
	    when(service.editar(any(Usuarios.class))).thenReturn(usuarioModificado);

	    mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/editar") 
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(mapper.writeValueAsString(usuarioModificado))) // Convertimos el objeto Java a JSON string
	            
	            // Verificamos que la respuesta HTTP sea 200 OK
	            .andExpect(status().isOk())
	            
	            // Verificamos que el JSON de respuesta tenga los datos correctos
	            .andExpect(jsonPath("$.id").value(1))
	            .andExpect(jsonPath("$.nombre").value("Juan"))
	            .andExpect(jsonPath("$.email").value("juan@correo.com"))
	            .andExpect(jsonPath("$.status").value("Enable"));

	    // Verificación extra: Asegurarnos de que el controlador realmente invocó al servicio una vez
	    verify(service).editar(any(Usuarios.class));
	}
	
	@Test
	void testEliminar() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/eliminar/1"))
		.andExpect(status().isOk());
		
		Mockito.verify(service, Mockito.times(1)).eliminar(1);
	}
	
	@Test
	void testBuscarPorNombre() throws Exception {
		 // Datos simulados
	    Usuarios usr1 = new Usuarios(1, "Juan", "juan@correo.com", "Enable", "Puebla");
		
		when(service.buscarPorNombre("Juan")).thenReturn(usr1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/buscarNombre/Juan"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.email").value("juan@correo.com"));	
	}
	
	@Test
	void testBuscarPorNivel() throws Exception {
		//Datos simulados
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		Usuarios usr2 = new Usuarios (2, "nombre Update", "juan@correo.com", "Disable", "Puebla");
		
		when(service.buscarPorStatus("Disable")).thenReturn(Arrays.asList(usr1,usr2));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/buscarStatus")
		.param("status", "Disable"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(2));
		
	}


	

}
