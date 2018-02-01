package mx.com.beo.mongo.services;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.bson.types.ObjectId;
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
 * @author Edgar Alan Valdes Iglesias
 *
 *         ESTE SOFTWARE ES INFORMACIÓN CONFIDENCIAL. PROPIEDAD DE NOVA SOLUTION
 *         SYSTEMS. ESTA INFORMACIÓN NO DEBE SER DIVULGADA Y PUEDE SOLAMENTE SER
 *         UTILIZADA DE ACUERDO CON LOS TÉRMINOS DETERMINADOS POR LA EMPRESA SÍ
 *         MISMA.
 */
@Repository
@Service
public class MongoServiceImpl implements MongoService {

	private Conexion conexion;
	private MongoClient mongo;
	private static final String baseDatos=Urls.BASEDATOS_MONGO.getPath();

	public Integer eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {
		DBCollection coleccion = getCollection(nombreColeccion);
		BasicDBObject objeto = new BasicDBObject();
		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}
		WriteResult respuesta = coleccion.remove(objeto);
		return respuesta.getN();
	}
	
	
	/**
	 * Modifica un documento existente en la colección especificada.
	 * <p>
	 * @param  &ensp;<b>nombreColeccion</b>  nombre de la colección donde se realizarán las modificaciones.
	 * @param  &ensp;<b>mapaDatosConsulta</b> mapa que contiene los campos utilizados como referencia para la búsqueda.
	 * @param  &ensp;<b>mapaDatosAModificar</b> mapa con los datos que serán modificados.
	 * @param  &ensp;<b>permitirUpsert</b> Si se permiten o no upserts.
	 * <p>
	 * En caso de ser <i>true</i> un nuevo parámetro puede ser agregado al documento.
	 * <p>
	 * En caso de ser <i>false</i> sólo se actualizará si los campos existen.
	 * @return &ensp;<b>Integer</b> Valor entero con el número de documentos que fueron modificados.
	 */

	public Integer modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta,
			Map<String, Object> mapaDatosAModificar,boolean permitirUpsert) {
		
		DBCollection coleccion = getCollection(nombreColeccion);
		BasicDBObject objetoAModificar = new BasicDBObject();
		BasicDBObject MapaConsulta = new BasicDBObject();
		
		Iterator<String> itConsulta = mapaDatosConsulta.keySet().iterator();
		while (itConsulta.hasNext()) {
			Object key = itConsulta.next();
			MapaConsulta.put(key.toString(), new BasicDBObject("$eq", mapaDatosConsulta.get(key)));
		}
		Iterator<String> it = mapaDatosAModificar.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			if (!permitirUpsert) {
				if (MapaConsulta.containsField(key.toString())) {
					MapaConsulta.append(key.toString(), new BasicDBObject("$exists", true).append("$eq",mapaDatosConsulta.get(key.toString())));
				} else {
					MapaConsulta.append(key.toString(), new BasicDBObject("$exists", true));
				}
			}
			objetoAModificar.append(key.toString(), mapaDatosAModificar.get(key));
		}
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", objetoAModificar);
		WriteResult respuesta = coleccion.update(MapaConsulta, setQuery,false,true);
		return respuesta.getN();
	}

	public Boolean inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {
		DBCollection coleccion = getCollection(nombreColeccion);
		BasicDBObject objeto = new BasicDBObject();
		Iterator<String> it = mapaDatosConsulta.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			objeto.put(key.toString(), mapaDatosConsulta.get(key));
		}
		WriteResult respuesta = coleccion.insert(objeto);
		return respuesta.wasAcknowledged() ? true : false;
	}

	public Map<String, Object> consulta(String nombreColeccion) {
		Map<String, Object> consulta = new HashMap<String, Object>();
		return createResponseMap(getCollection(nombreColeccion),consulta,null,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta,List<String> datosAIgnorar) {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,datosAIgnorar,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> formatoFechas) {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,formatoFechas);
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> formatoFechas,List<String> datosAIgnorar) {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,datosAIgnorar,formatoFechas);
	}	
	
	private Map<String, Object> createResponseMap(DBCollection coleccion, Map<String, Object> mapaDatosConsulta, List<String> datosAIgnorar, Map<String, Object> formatoFechas){
		BasicDBObject objetoDB = new BasicDBObject();
		Map<String, Object> mapaRespuestaGeneral = new HashMap<String, Object>();
		int contador = 0;
		DBCursor cursor;
		for (Entry entry : mapaDatosConsulta.entrySet()) {
			if(entry.getKey().toString().equals("id")) {
				ObjectId id= new ObjectId(entry.getValue().toString());
				objetoDB.put("_id", id);
			}else {	
				objetoDB.put(entry.getKey().toString(), entry.getValue());
			}
		}
		try {
			//Ignore Data for Search
			cursor = (datosAIgnorar != null) ? coleccion.find(objetoDB,getIgnoreData(datosAIgnorar)): coleccion.find(objetoDB);
			while (cursor.hasNext()) {
				Map<String, Object> mapaRespuesta = new HashMap<String, Object>();
				DBObject key = cursor.next();	
				mapaRespuesta = (Map<String, Object>) key.toMap();
				parseIdToString(mapaRespuesta,key);
				//FormatDates
				if(formatoFechas!=null)
					formatDates(mapaRespuesta,formatoFechas);
				contador++;
				mapaRespuestaGeneral.put(contador + "", mapaRespuesta);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
		return mapaRespuestaGeneral;
	}
	
	private BasicDBObject getIgnoreData (List<String> datosAIgnorar) {
		BasicDBObject objetoDB = new BasicDBObject();
		Map<String, Object> mapaDatosAIgnorar = new HashMap<String, Object>();
		for (String dato : datosAIgnorar) {
			mapaDatosAIgnorar.put(dato, 0);
		}
		for (Entry entry : mapaDatosAIgnorar.entrySet()) {
			objetoDB.put(entry.getKey().toString(), entry.getValue());
		}
		return objetoDB;
	}
	
	private void parseIdToString(Map<String, Object> _map,DBObject key) {
		if(_map.containsKey("_id")) {
			_map.put("id", key.get("_id").toString());
			_map.remove("_id");
		}
	}

	private void formatDates(Map<String, Object> _map,Map<String, Object> dates) {
		for (Entry date : dates.entrySet()) {
			if(_map.containsKey(date.getKey()) && _map.get(date.getKey()) instanceof Date){
				Date _date = (Date)_map.get(date.getKey());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date.getValue().toString());
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				_map.put(date.getKey().toString(), simpleDateFormat.format(_date));					
			}
		}
	}	
	
	private DBCollection getCollection (String nombreColeccion) {
		MongoClient mongoClient = getMongoClient();
		DB db = mongoClient.getDB(baseDatos);
		return db.getCollection(nombreColeccion);		
	}
	
	public synchronized MongoClient getMongoClient(){
		if(conexion==null){
			conexion=new Conexion();
			mongo = conexion.crearConexion();
		}
		return mongo;
	}
	
}

