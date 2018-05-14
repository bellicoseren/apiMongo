package mx.com.beo.mongo.util;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

 


public class Conexion {
	
	public MongoClient crearConexion() {
		MongoClient mongo = null;
		String host=Urls.HOST_MONGO.getPath();
		String usuario=Urls.USER_MONGO.getPath();
		String pass=Urls.PASSWORD_USER_MONGO.getPath();
		String source=Urls.SOURCE_USER_MONGO.getPath();
		int serverSelectionTimeout = 1000;
			
		MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(usuario,source,pass.toCharArray());
		Builder mongoOptions = MongoClientOptions.builder().serverSelectionTimeout((System.getenv("TIME_OUT") == null
				? serverSelectionTimeout : Integer.valueOf(System.getenv("TIME_OUT"))));
		int puerto=Integer.parseInt(Urls.PUERTO_MONGO.getPath());
		mongo = new MongoClient(new ServerAddress(host, puerto),Arrays.asList(mongoCredential), mongoOptions.build());
		
		return mongo;
	}
	
	

}

