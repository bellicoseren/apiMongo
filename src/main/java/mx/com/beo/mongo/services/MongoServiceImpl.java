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
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.beo.mongo.util.Conexion;
import mx.com.beo.mongo.util.Constantes;
import mx.com.beo.mongo.util.MongoConectionException; 

 
@Repository
@Service
public class MongoServiceImpl implements MongoService {

	private Conexion conexion;
	private MongoClient mongo;
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoServiceImpl.class);

	public Integer eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException {
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
	 * @throws MongoConectionException 
	 */

	public Integer modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta,
			Map<String, Object> mapaDatosAModificar,boolean permitirUpsert) throws MongoConectionException {
		
		DBCollection coleccion = getCollection(nombreColeccion);
		BasicDBObject objetoAModificar = new BasicDBObject();
		BasicDBObject basicDBObject = new BasicDBObject();
		
		Iterator<String> itConsulta = mapaDatosConsulta.keySet().iterator();
		while (itConsulta.hasNext()) {
			Object key = itConsulta.next();
			basicDBObject.put(key.toString(), new BasicDBObject("$eq", mapaDatosConsulta.get(key)));
		}
		Iterator<String> it = mapaDatosAModificar.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			if (!permitirUpsert) {
				if (basicDBObject.containsField(key.toString())) {
					basicDBObject.append(key.toString(), new BasicDBObject("$exists", true).append("$eq",mapaDatosConsulta.get(key.toString())));
				} else {
					basicDBObject.append(key.toString(), new BasicDBObject("$exists", true));
				}
			}
			objetoAModificar.append(key.toString(), mapaDatosAModificar.get(key));
		}
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", objetoAModificar);
		WriteResult respuesta = coleccion.update(basicDBObject, setQuery,false,true);
		return respuesta.getN();
	}

	public Boolean inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException {
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

	public Map<String, Object> consulta(String nombreColeccion) throws MongoConectionException {
		Map<String, Object> consulta = new HashMap<>();
		return createResponseMap(getCollection(nombreColeccion),consulta,null,null,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta,List<String> datosAIgnorar) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,datosAIgnorar,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, DBObject query,List<String> datosAIgnorar) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),null,query,datosAIgnorar,null);
	}	

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,null,null);
	}

	public Map<String, Object> consulta(String nombreColeccion, DBObject query) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),null,query,null,null);
	}	

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> formatoFechas) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,null,formatoFechas);
	}

	public Map<String, Object> consulta(String nombreColeccion, DBObject query, Map<String, Object> formatoFechas) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),null,query,null,formatoFechas);
	}	

	public Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> formatoFechas,List<String> datosAIgnorar) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),mapaDatosConsulta,null,datosAIgnorar,formatoFechas);
	}

	public Map<String, Object> consulta(String nombreColeccion, DBObject query, Map<String, Object> formatoFechas,List<String> datosAIgnorar) throws MongoConectionException {
		return createResponseMap(getCollection(nombreColeccion),null,query,datosAIgnorar,formatoFechas);
	}	
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> createResponseMap(DBCollection coleccion, Map<String, Object> mapaDatosConsulta,DBObject query, List<String> datosAIgnorar, Map<String, Object> formatoFechas){
		Map<String, Object> mapaRespuestaGeneral = new HashMap<>();
		int contador = 0;
		DBCursor cursor;
		DBObject objetoDB = null;
		if(query != null)
			objetoDB = query;
		else {
			objetoDB = new BasicDBObject();
			for (Entry entry : mapaDatosConsulta.entrySet()) {
				if(entry.getKey().toString().equals(Constantes.ID)) {
					ObjectId id= new ObjectId(entry.getValue().toString());
					objetoDB.put(Constantes.ID_ID, id);
				}else {	
					objetoDB.put(entry.getKey().toString(), entry.getValue());
				}
			}
		}
		try {
			//Ignore Data for Search
			cursor = (datosAIgnorar != null) ? coleccion.find(objetoDB,getIgnoreData(datosAIgnorar)): coleccion.find(objetoDB);
			while (cursor.hasNext()) { 
				DBObject key = cursor.next();	
				Map<String, Object> mapaRespuesta = (Map<String, Object>) key.toMap();
				parseIdToString(mapaRespuesta,key);
				//FormatDates
				if(formatoFechas!=null)
					formatDates(mapaRespuesta,formatoFechas);
				contador++;
				mapaRespuestaGeneral.put(contador + "", mapaRespuesta);
			}
			
			
		} catch (Exception e) { 
			LOGGER.error("Error: " , e.getMessage());
		}
		return mapaRespuestaGeneral;
	}
	
	private BasicDBObject getIgnoreData (List<String> datosAIgnorar) {
		BasicDBObject objetoDB = new BasicDBObject();
		Map<String, Object> mapaDatosAIgnorar = new HashMap<>();
		for (String dato : datosAIgnorar) {
			mapaDatosAIgnorar.put(dato, 0);
		}
		for (Entry entry : mapaDatosAIgnorar.entrySet()) {
			objetoDB.put(entry.getKey().toString(), entry.getValue());
		}
		return objetoDB;
	}
	
	private void parseIdToString(Map<String, Object> mapaParse,DBObject key) {
		if(mapaParse.containsKey(Constantes.ID_ID)) {
			mapaParse.put(Constantes.ID, key.get(Constantes.ID_ID).toString());
			mapaParse.remove(Constantes.ID_ID);
		}
	}

	private void formatDates(Map<String, Object> mapaFormato ,Map<String, Object> dates) {
		for (Entry date : dates.entrySet()) {
			if(mapaFormato.containsKey(date.getKey()) && mapaFormato.get(date.getKey()) instanceof Date){
				Date date1 = (Date)mapaFormato.get(date.getKey());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date.getValue().toString());
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				mapaFormato.put(date.getKey().toString(), simpleDateFormat.format(date1));					
			}
		}
	}	
	
	@SuppressWarnings("deprecation")
	private DBCollection getCollection(String nombreColeccion) throws MongoConectionException {
		DBCollection dbCollectiondb = null;
		try {
			MongoClient mongoClient = getMongoClient();
			DB db = mongoClient.getDB(Constantes.BASEDATOS);
			dbCollectiondb = db.getCollection(nombreColeccion);
			
			//valida conexion
			db.getCollection(nombreColeccion).findOne();

		} catch (MongoTimeoutException e) {
			LOGGER.error("Error conexion con Mongo" , e.getMessage());
			throw new MongoConectionException("Error conexion con Mongo");
		}
		return dbCollectiondb;
	}
	
	public synchronized MongoClient getMongoClient(){
		if(conexion==null){
			conexion=new Conexion();
			mongo = conexion.crearConexion();
		}
		return mongo;
	}
	
}

