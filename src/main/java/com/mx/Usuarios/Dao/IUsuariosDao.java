package com.mx.Usuarios.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.Usuarios.Dominio.Usuarios;

public interface IUsuariosDao extends JpaRepository<Usuarios, Integer> {
	
	List<Usuarios> findByStatusIgnoreCase(String status);;
	
	Usuarios findByNombreIgnoreCase(String estado);
}
