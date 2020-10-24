package com.unfv.api.apirest.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unfv.api.apirest.DAO.IMedicamentosDAO;
import com.unfv.api.apirest.Entity.MedicamentosEntity;
import com.unfv.api.apirest.Service.IMedicamentoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Service
public class MedicamentoServiceImpl implements IMedicamentoService {
	//Inyectamos dependencias con autowired
	@Autowired
	private IMedicamentosDAO medicamentoDAO;
	
	@Override
	@Transactional(readOnly = true)//es opcional
	public List<MedicamentosEntity> findAll(){
		// TODO Auto-generated method stub
		return (List<MedicamentosEntity>) medicamentoDAO.findAll();//hacemos un casteo
	}
	
	@Override
	@Transactional(readOnly = true)//es opcional
	public Page<MedicamentosEntity> findAllByOrderByIdAsc(Pageable pageable) {
		// TODO Auto-generated method stub
		return medicamentoDAO.findAlllByOrderByIdAsc(pageable);
	}
	
	@Override
	@Transactional
	public MedicamentosEntity save(MedicamentosEntity medicamentos) {
		// TODO Auto-generated method stub
		//retorna la entidad guardada
		return medicamentoDAO.save(medicamentos);
	}
	@Override 
	@Transactional
	public void delete(Long id) {
		medicamentoDAO.deleteById(id);
		
	}
	@Override
	@Transactional(readOnly = true)
	public MedicamentosEntity findById(Long id) {
		// TODO Auto-generated method stub
		return medicamentoDAO.findById(id).orElse(null);
	}
	

}
