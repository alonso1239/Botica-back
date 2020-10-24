package com.unfv.api.apirest.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.unfv.api.apirest.Entity.MedicamentosEntity;
//este es el que vale
public interface IMedicamentosDAO extends JpaRepository<MedicamentosEntity,Long>{
	 public List<MedicamentosEntity>findAll();
	
	public Page<MedicamentosEntity> findAlllByOrderByIdAsc(Pageable pageable);
}
