package com.unfv.api.apirest.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.unfv.api.apirest.Entity.MedicamentosEntity;

//Le pasamos los metodos del crud
public interface IMedicamentoService {
	
	//Listar FindAll
	public List<MedicamentosEntity> findAll();
	
	//Lista por paginacion}
	public Page<MedicamentosEntity> findAllByOrderByIdAsc(Pageable pageable);
	
	//guardar Medicamentos
	public MedicamentosEntity save(MedicamentosEntity medicamentos);
	
	//borrar medicamentos
	
	public void delete(Long id);
	
	//buscar por id
	
	public MedicamentosEntity findById(Long id);

	

}
