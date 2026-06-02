package com.mx.Usuarios.Service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import com.mx.Usuarios.Dao.IUsuariosDao;
import com.mx.Usuarios.Dominio.Usuarios;

import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.*;

class UsuariosServiceTest {
	
	
	@Mock
	private IUsuariosDao dao;
	
	//Inyectamos el Mock(dao similado dentro del servicio)
	@InjectMocks
	private UsuariosService service;
	

	@BeforeEach
	void setUp() throws Exception {
		//Inicializamos los mocks antes de cada prueba iunitaria
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGuardar() {
		Usuarios usr = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		
		//simulacion de guardar desde el dao
		when(dao.save(usr)).thenReturn(usr);
		
		Usuarios guardado = service.guardar(usr);
		
		assertNotNull(guardado);
		assertEquals("Juan", guardado.getNombre());
		
		verify(dao).save(usr);
	}
	
	@Test
	void testEditar() {
	       //Datos de prueba: El alumno original y el alumno con los datos modificados
		Usuarios usrOriginal = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		Usuarios usrModificado = new Usuarios (2, "nombre Update", "juan@correo.com", "Disable", "Puebla");
	       
	       //Simulación de comportamientos (Stubbing)
	       // Simulamos que primero lo encuentra en la base de datos
	       when(dao.findById(1)).thenReturn(Optional.of(usrOriginal));
	       // Simulamos que guarda los datos modificados con éxito
	       when(dao.save(any(Usuarios.class))).thenReturn(usrModificado);
	       
	       //Ejecución del método del servicio
	       Usuarios resultado = service.editar(usrModificado);
	       
	       //Aseveraciones (Assertions)
	       assertNotNull(resultado);
	       assertEquals("nombre Update", resultado.getNombre());
	       assertEquals(2, resultado.getId());
	       
	       //Verificaciones de interacciones con el DAO
	       verify(dao).save(any(Usuarios.class));
	    }
	
	void testBuscar() {
		Usuarios usr = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		when(dao.findById(1)).thenReturn(Optional.of(usr));
		
		Usuarios buscado = service.buscar(1);
		
		assertNotNull(buscado);
		
		verify(dao).findById(1);
	}
	
	@Test
	void testEliminar() {
		service.eliminar(1);
		verify(dao).deleteById(1);
	}
	
	@Test
	void testListar() {
		List<Usuarios> lista = Arrays.asList(
				new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla"),
				new Usuarios (2, "Carlos", "calor@correo.com", "Enable", "CDMX"),
				new Usuarios (3, "Diego", "diego@correo.com", "Disable", "Puebla")
				);
		
		//simulamos el compartamiuento del DAO
		when(dao.findAll(any(Sort.class))).thenReturn(lista);
		
		//Ejecutamos la llamada de dao
		List<Usuarios> resultado = service.listar();
		
		//Assertiones
		assertEquals(3, resultado.size());
		
		//verificamos que el mock DAO llamo al menos una vez al metodo findAll
		verify(dao).findAll(any(Sort.class));
	}
	
	
	@Test
	void testBuscarPorNombre() {
		//daot simulados
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		Usuarios usr2 = new Usuarios (2, "Pedro", "pedro@correo.com", "Enable", "CDMX");
		
		//simulamos el comprtamiento
		when(dao.findByNombreIgnoreCase("Pedro")).thenReturn(usr2);
		
		Usuarios encontrado = service.buscarPorNombre("Pedro");
		
		assertNotNull(encontrado);
		assertEquals("Pedro", encontrado.getNombre());
		
		verify(dao).findByNombreIgnoreCase("Pedro");
	}
	
	@Test
	void testBuscarPorStatus() {
		//datos simulados
		Usuarios usr1 = new Usuarios (1, "Juan", "juan@correo.com", "Disable", "Puebla");
		Usuarios usr2 = new Usuarios (2, "Pedro", "pedro@correo.com", "Disable", "CDMX");

		
		when(dao.findByStatusIgnoreCase("Disable")).thenReturn(Arrays.asList(usr1,usr2));
		
		List<Usuarios> resusltado = service.buscarPorStatus("Disable");
		

		assertEquals(2, resusltado.size());
		assertEquals("Disable", resusltado.get(1).getStatus());
		
		verify(dao).findByStatusIgnoreCase("Disable");
		
	}
	

}
