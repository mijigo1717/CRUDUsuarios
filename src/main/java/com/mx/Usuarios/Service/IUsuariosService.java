package com.mx.Usuarios.Service;

import java.util.List;

import com.mx.Usuarios.Dominio.Usuarios;



public interface IUsuariosService {

	Usuarios guardar (Usuarios u);
	
	Usuarios editar (Usuarios u);
	
	Usuarios buscar (int id);
	
	void eliminar (int id);
	
	List<Usuarios> listar();
}
