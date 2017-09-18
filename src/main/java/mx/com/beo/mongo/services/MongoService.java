package mx.com.beo.mongo.services;
 
import java.util.List;
import java.util.Map;

import com.mongodb.WriteResult;
/**
 * Copyright (c) 2017 Nova Solution Systems S.A. de C.V. Mexico D.F. Todos los
 * derechos reservados.
 *
 * @author Reynaldo Ivan Martinez Lopez
 *
 *         ESTE SOFTWARE ES INFORMACIÓN CONFIDENCIAL. PROPIEDAD DE NOVA SOLUTION
 *         SYSTEMS. ESTA INFORMACIÓN NO DEBE SER DIVULGADA Y PUEDE SOLAMENTE SER
 *         UTILIZADA DE ACUERDO CON LOS TÉRMINOS DETERMINADOS POR LA EMPRESA SÍ
 *         MISMA.
 */

public abstract interface MongoService
{
	public abstract WriteResult eliminar(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	
	public abstract WriteResult modificacion(String nombreColeccion, Map<String, Object> mapaDatosConsulta, Map<String, Object> mapaDatosNuevos);
	
	public abstract String inserta(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	 
	public abstract Map<String, Object> consulta(String nombreColeccion);
	
	public abstract Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta, List<String> datosAIgnorar);
	
	public abstract Map<String, Object> consulta(String nombreColeccion, Map<String, Object> mapaDatosConsulta);
	
	 
}
