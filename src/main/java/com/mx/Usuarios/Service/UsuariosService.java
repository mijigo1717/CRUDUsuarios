package com.mx.Usuarios.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Usuarios.Dao.IUsuariosDao;
import com.mx.Usuarios.Dominio.Usuarios;

@Service
public class UsuariosService implements IUsuariosService{
	
	
	@Autowired
	private IUsuariosDao dao;

	@Override
	public Usuarios guardar(Usuarios u) {
		return dao.save(u);
	}

	@Override
	public Usuarios editar(Usuarios u) {
		return dao.save(u);
	}

	@Override
	public Usuarios buscar(int id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(int id) {
		dao.deleteById(id);
	}

	@Override
	public List<Usuarios> listar() {
		return dao.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	public Usuarios buscarPorNombre(String nombre) {
		return  dao.findByNombreIgnoreCase(nombre);
	}
	
	public List <Usuarios> buscarPorStatus(String status){
		return dao.findByStatusIgnoreCase(status);
	}
}
