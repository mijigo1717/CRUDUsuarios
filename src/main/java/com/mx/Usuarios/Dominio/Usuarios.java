package com.mx.Usuarios.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Usuarios_DB")
@Data
public class Usuarios {

	@Id
	private Integer id;
	private String nombre;
	private String email;
	private String status;
	private String estado;
	
	
}
