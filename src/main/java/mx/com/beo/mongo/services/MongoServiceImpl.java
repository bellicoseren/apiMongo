package mx.com.beo.mongo.services;
 
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import mx.com.beo.mongo.util.Conexion;
import mx.com.beo.mongo.util.Urls; 

/**
 * Copyright (c) 2017 Nova Solution Systems S.A. de C.V. Mexico D.F. TodoSysotems los
 * derechos reservados.
 *
 * @author Reynaldo Ivan Martinez Lopez
 *
 *         ESTE SOFTWARE ES INFORMACIÓN CONFIDENCIAL. PROPIEDAD DE NOVA SOLUTION
 *         SYSTEMS. ESTA INFORMACIÓN NO DEBE SER DIVULGADA Y PUEDE SOLAMENTE SER
 *         UTILIZADA DE ACUERDO CON LOS TÉRMINOS DETERMINADOS POR LA EMPRESA SÍ
 *         MISMA.
 */
@Repository
@Service
public class MongoServiceImpl implements MongoService {

 
	Conexion conexion=new Conexion();
	
	private static final String baseDatos=Urls.BASEDATOS_MONGO.getPath();

	public WriteResult eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {

		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objeto = new BasicDBObject();

		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}
		WriteResult respuesta = coleccion.remove(objeto);

		return respuesta;
	}

	public WriteResult modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta,
			Map<String, Object> mapaDatosAModificar) {

		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objetoAModificar = new BasicDBObject();
		BasicDBObject MapaConsulta = new BasicDBObject();

		Iterator<String> itConsulta = mapaDatosConsulta.keySet().iterator();
		while (itConsulta.hasNext()) {
			Object key = itConsulta.next();
			MapaConsulta.put(key.toString(), mapaDatosConsulta.get(key));
		}

		Iterator<String> it = mapaDatosAModificar.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objetoAModificar.put(key.toString(), mapaDatosAModificar.get(key));
		}

		WriteResult respuesta = coleccion.update(MapaConsulta, objetoAModificar);

		return respuesta;

	}

	

	public String inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {
 
		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objeto = new BasicDBObject();

		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}

		coleccion.insert(objeto);

		return "OK";
	}

	public Map<String, Object> consulta(String nombreColeccion) {

		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objeto = new BasicDBObject();
		BasicDBObject objetoProy = new BasicDBObject();
		Map<String, Object> mapaRespuesta = new HashMap<String, Object>();
		Map<String, Object> mapaRespuestaGeneral = new HashMap<String, Object>();
		int contador = 0;
		DBCursor cursor;
		try {
			cursor = coleccion.find(objeto, objetoProy);
			while (cursor.hasNext()) {
				DBObject key = cursor.next();
				Set<String> keyset = key.keySet();
				for (String s : keyset) {
					if(s.equalsIgnoreCase("_id")){
						mapaRespuesta.put(s, key.get(s).toString());
					}else{
						mapaRespuesta.put(s, key.get(s));
					};
				}
				contador++;
				mapaRespuestaGeneral.put(contador + "", mapaRespuesta);
				mapaRespuesta=new HashMap<String, Object>();
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return mapaRespuestaGeneral;
	}
 

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta,
			List<String> datosAIgnorar) {

		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objeto = new BasicDBObject();
		BasicDBObject objetoProy = new BasicDBObject();
		Map<String, Object> mapaRespuesta = new HashMap<String, Object>();
		Map<String, Object> mapaRespuestaGeneral = new HashMap<String, Object>();

		Map<String, Object> mapaDatosAIgnorar = new HashMap<String, Object>();

		for (String dato : datosAIgnorar) {
			mapaDatosAIgnorar.put(dato, 0);

		}

		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}

		Iterator<String> it1 = mapaDatosAIgnorar.keySet().iterator();
		while (it1.hasNext()) {
			Object key = it1.next();
			objetoProy.put(key.toString(), mapaDatosAIgnorar.get(key));
		}

		int contador = 0;
		DBCursor cursor;
		try {
			cursor = coleccion.find(objeto, objetoProy);
			while (cursor.hasNext()) {
				DBObject key = cursor.next();
				Set<String> keyset = key.keySet();
				for (String s : keyset) {
					if(s.equalsIgnoreCase("_id")){
						mapaRespuesta.put(s, key.get(s).toString());
					}else{
						mapaRespuesta.put(s, key.get(s));
					}
					
				}
				contador++;
				mapaRespuestaGeneral.put(contador + "", mapaRespuesta);
				mapaRespuesta=new HashMap<String, Object>();
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return mapaRespuestaGeneral;
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {

		MongoClient mongo = conexion.crearConexion();
		DB db = mongo.getDB(baseDatos);
		DBCollection coleccion = db.getCollection(nombreColeccion);

		BasicDBObject objeto = new BasicDBObject();
		Map<String, Object> mapaRespuesta = new HashMap<String, Object>();
		Map<String, Object> mapaRespuestaGeneral = new HashMap<String, Object>();

		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}

		int contador = 0;
		DBCursor cursor;
		try {
			cursor = coleccion.find(objeto);
			while (cursor.hasNext()) {
				DBObject key = cursor.next();
				Set<String> keyset = key.keySet();
				for (String s : keyset) {
					if(s.equalsIgnoreCase("_id")){
						mapaRespuesta.put(s, key.get(s).toString());
					}else{
						mapaRespuesta.put(s, key.get(s));
					}
				}
				 
				contador++;
				mapaRespuestaGeneral.put(contador + "", mapaRespuesta);
				mapaRespuesta=new HashMap<String, Object>(); 

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return mapaRespuestaGeneral;
	}



}
