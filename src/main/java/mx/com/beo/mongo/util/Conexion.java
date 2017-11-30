package mx.com.beo.mongo.util;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

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
		String host=Urls.HOST_MONGO.getPath();
		String usuario=Urls.ADMIN_USER_MONGO.getPath();
		String pass=Urls.ADMIN_PASS_MONGO.getPath();
		MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("admin", usuario,pass.toCharArray());
		int puerto=Integer.parseInt(Urls.PUERTO_MONGO.getPath());
		mongo = new MongoClient(new ServerAddress(host, puerto),Arrays.asList(mongoCredential)); 
		return mongo;
	}

}

