package mx.com.beo.util;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;

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


public class Conexion {
	
	public MongoClient crearConexion() {
		MongoClient mongo = null;
		try {
			mongo = new MongoClient("172.17.0.2", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return mongo;
	}

}
