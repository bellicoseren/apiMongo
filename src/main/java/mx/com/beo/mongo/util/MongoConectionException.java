package mx.com.beo.mongo.util;


public class MongoConectionException extends Exception{

	private static final long serialVersionUID = -3488156158931397793L;
	/**
	 * Excepcion generica para mensaje y Throwable
	 * 
	 * @param message
	 * @param throwable
	 */
	public MongoConectionException(String message) {
		 super(message);
	}
}