package com.unfv.api.apirest.Controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unfv.api.apirest.Dto.Medicamentos;
import com.unfv.api.apirest.Entity.MedicamentosEntity;
import com.unfv.api.apirest.Service.IMedicamentoService;

@CrossOrigin(origins = {"http://localhost:4200"})
//expone servicos para q el cliente haga peticiones
@RestController
//cuando busquemos un recurso en la url va hacia este conrolador 
@RequestMapping("/home")
public class MedicamentosController {
//@autowired inyecta dependencias al dao, si hay mas de una se usa @Qualifier

	@Autowired
	private IMedicamentoService medicamentosService;
	
	private final Logger log=LoggerFactory.getLogger(MedicamentosController.class);
	
	@GetMapping(path="/medicamentos")
	public  List<MedicamentosEntity> listar()
	{
		return medicamentosService.findAll();
	}
	
	@GetMapping(path="/medicamentos/page/{page}")//desde angular pasamos el nro de pagina
	public  Page <MedicamentosEntity> listar(@PathVariable Integer page)
	{
		return medicamentosService.findAllByOrderByIdAsc(PageRequest.of(page, 6));
	}
	
	//retornara el medicamento convertido en json
	//para tratar excepciones usar response entity
	@GetMapping(path="/medicamentos/{id}")
	public ResponseEntity<?> show (@PathVariable Long id)
	{
		MedicamentosEntity medicamento=null;
		//usamos un mapa para capturar el error 
		//map: interfaz  hashmap: implementacion
		Map<String,Object>response= new HashMap<>();
		try
		{
			medicamento=medicamentosService.findById(id);
		}
		catch(DataAccessException e)//en caso de que hubiera un error con la bd
		{
			//con response colocamos el error
			response.put("mensaje","Error al realizar la consulta en la base de datos" );
			response.put("error",e.getMessage()+" :" +e.getMostSpecificCause().getMessage());
			//retornamos el mapa
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	
		if(medicamento==null)
		{
			//el nombre del mensaje en response debe ser el mismo que en service del angular
			response.put("mensaje","El medicamento con ID:"+ id + " no existe en la base de datos" );
			//retornamos el mapa
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<MedicamentosEntity>(medicamento,HttpStatus.OK); 
	}
	
	//crear de tipo post
	//nos envian el json por el request body y se transforma en un objeto medicamento
	@PostMapping("/medicamentos")
	public ResponseEntity<?> create(@Valid @RequestBody MedicamentosEntity medicamentos, BindingResult result)//con el binding result validaremos
	{
		MedicamentosEntity medicamento2=null;
		Map<String,Object>response= new HashMap<>();
		if(result.hasErrors())//si encuentra error
		{
			//creamos un list de tipo string q contiene los mensajes de error
			
			List<String> errors= result.getFieldErrors()
					.stream().map(err ->"El campo '"+ err.getField()+ "'" +err.getDefaultMessage())
					.collect(Collectors.toList());
			
			
			/*List<String> errors= new ArrayList<>();
			for(FieldError err:result.getFieldErrors())
			{
				errors.add("El campo '"+ err.getField()+ "'" +err.getDefaultMessage());
			}*/
					
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try
		{
			medicamento2 = medicamentosService.save(medicamentos);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje","Error al ingresar informacion en la base de datos" );
			response.put("error",e.getMessage()+" :" +e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El medicamento fue registrado con exito" );
		response.put("medicamento",medicamento2);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/medicamentos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody MedicamentosEntity medicamentos,BindingResult result,@PathVariable Long id)
	{
		MedicamentosEntity medicamentoActual= medicamentosService.findById(id);
		MedicamentosEntity medicamentoActualizado=null;
		
		Map<String,Object>response= new HashMap<>();
		if(result.hasErrors())//si encuentra error
		{
			//creamos un list de tuipo string q contiene los mensajes de error
			List<String> errors= result.getFieldErrors()
					.stream().map(err ->"El campo '"+ err.getField()+ "'" +err.getDefaultMessage())
					.collect(Collectors.toList());
			
			
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(medicamentoActual==null)
		{
			response.put("mensaje","Error : El medicamento con ID:"+ id + " no se puede editar, no existe en la base de datos" );
			//retornamos el mapa
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			
		}
		try
		{
		medicamentoActual.setCantidad(medicamentos.getCantidad());
		medicamentoActual.setCosto(medicamentos.getCosto());
		medicamentoActual.setDescripcion(medicamentos.getDescripcion());
		medicamentoActual.setFecha(medicamentos.getFecha());
		medicamentoActual.setNombre(medicamentos.getNombre());
		medicamentoActual.setPventa(medicamentos.getPventa());
		medicamentoActual.setStand(medicamentos.getStand());
		
		medicamentoActualizado=medicamentosService.save(medicamentoActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje","Error al actualizar datos" );
			response.put("error",e.getMessage()+" :" +e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El medicamento fue actualizado con exito" );
		response.put("medicamento",medicamentoActualizado);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/medicamentos/{id}")
	public ResponseEntity<?> delete (@PathVariable Long id)
	{
		//el map guarda el contenido q se envia en el reponse entity
		Map<String,Object>response= new HashMap<>();
		try
		{
			MedicamentosEntity medicamento= medicamentosService.findById(id);
			String nombreFotoAnterior=medicamento.getFoto();
			if(nombreFotoAnterior!=null && nombreFotoAnterior.length()>0)
			{
				Path rutaFotoAnterior=Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior=rutaFotoAnterior.toFile();
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead())
				{
					archivoFotoAnterior.delete();				
				}
				
			}
		medicamentosService.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje","Error al eliminar datos" );
			response.put("error",e.getMessage()+" :" +e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El medicamento fue eliminado con exito" );
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

	
	@PostMapping("/medicamentos/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id)//con el binding result validaremos
	{
		Map<String,Object>response= new HashMap<>();
		MedicamentosEntity medicamento= medicamentosService.findById(id);
		if(!archivo.isEmpty())
		{
			String nombreArchivo= UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ","");
			Path rutaArchivo=Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			log.info(rutaArchivo.toString());
			try {
				Files.copy(archivo.getInputStream(),rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje","Error al subir la imagen : "+nombreArchivo);
				response.put("error",e.getMessage()+" :" +e.getCause().getMessage());
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior=medicamento.getFoto();
			if(nombreFotoAnterior!=null && nombreFotoAnterior.length()>0)
			{
				Path rutaFotoAnterior=Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior=rutaFotoAnterior.toFile();
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead())
				{
					archivoFotoAnterior.delete();				
				}
				
			}
			
			
			medicamento.setFoto(nombreArchivo);
			medicamentosService.save(medicamento);
			response.put("medicamento",medicamento);
			response.put("mensaje","Imagen subida satisfactoriamente : "+nombreArchivo);
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping(path="/uploads/img/{nombreFoto:.+}")//indicamos que va a recibir una extension, JPG,PNG,ETC
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto)
	{
		Path rutaArchivo=Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		Resource recurso=null;
		try
		{
		recurso=new UrlResource(rutaArchivo.toUri());
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		//validamos el recurso
		if(!recurso.exists() && !recurso.isReadable())
		{
			throw new RuntimeException("Error no se puede cargar la imagen : "+nombreFoto);
		}
		HttpHeaders cabecera= new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);//imagen, forzamos para mostrar en el html y el status
	}

}




