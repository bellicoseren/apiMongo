package mx.com.beo.mongo.util;

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


public enum Urls {
	
	 /**
	  * Enum que nos arma la url del servicio notificaciones
	  * 
	  * VARIABLES DE AMBIENTE.
	  * - PROTOCOLO
	  * - HOSTNAME
	  * - PUERTO
	  * - BASEPATH
	  */

	HOST_MONGO(System.getenv("HOST_MONGO")),
	PUERTO_MONGO(System.getenv("PUERTO_MONGO")),
	BASEDATOS_MONGO(System.getenv("BASEDATOS_MONGO")),
	USER_MONGO(System.getenv("USER_MONGO")),
	PASSWORD_USER_MONGO(System.getenv("PASSWORD_USER_MONGO")),
	SOURCE_USER_MONGO(System.getenv("SOURCE_USER_MONGO"))
;
 
	private String path;
	 
	private Urls(String path){
		this.path=path; 
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

