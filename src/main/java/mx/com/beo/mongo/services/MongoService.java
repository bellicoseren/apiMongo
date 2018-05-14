package mx.com.beo.mongo.services;
 
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

import mx.com.beo.mongo.util.MongoConectionException;
 

public interface MongoService
{
	Integer eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException;
	
    Integer modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaDatosNuevos,boolean permitirUpsert) throws MongoConectionException;
	
	Boolean inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException;
	 
	Map<String, Object> consulta(String nombreColeccion) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, List<String> datosAIgnorar) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, DBObject query, List<String> datosAIgnorar) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, DBObject query) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaFormatoFechas) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, DBObject query, Map<String, Object> mapaFormatoFechas) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaFormatoFechas, List<String> datosAIgnorar) throws MongoConectionException;
	
	Map<String, Object> consulta(String nombreColeccion, DBObject query, Map<String, Object> mapaFormatoFechas, List<String> datosAIgnorar) throws MongoConectionException;
	 
}

