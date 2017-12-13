package mx.com.beo.mongo.services;
 
import java.util.List;
import java.util.Map;

import com.mongodb.WriteResult;
/**
 * Copyright (c) 2017 Nova Solution Systems S.A. de C.V. Mexico D.F. Todos los
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

public interface MongoService
{
	Integer eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	
    Integer modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaDatosNuevos);
	
	Boolean inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	 
	Map<String, Object> consulta(String nombreColeccion);
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, List<String> datosAIgnorar);
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaFormatoFechas);
	
	Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaFormatoFechas, List<String> datosAIgnorar);
	 
}

