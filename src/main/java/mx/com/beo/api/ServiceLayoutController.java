package mx.com.beo.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.WriteResult;

import mx.com.beo.services.MongoService; 
import mx.com.beo.util.Operaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


@RestController
public class ServiceLayoutController {

	@Autowired
	private MongoService mongo;

	private static Logger log = Logger.getLogger(ServiceLayoutController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insercion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insercion(RequestEntity<Object> request) {

		Map<String, Object> mapaInsertar = new HashMap<String, Object>();
		Map<String, Object> mapaResultado = new HashMap<String, Object>();
		try {

			mapaInsertar = (Map<String, Object>) request.getBody();

			String respuesta = mongo.inserta("Cliente", mapaInsertar);
			 
			mapaResultado.put("CodigoError: ", respuesta);
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (HttpMessageNotReadableException e) {
			 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Insertar");

			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Insertar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultaVarios", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultaVarios(RequestEntity<Object> request) {

		Map<String, Object> mapaDatosConsulta = new HashMap<String, Object>();
		Map<String, Object> mapaResultado = new HashMap<String, Object>();

		try {
			mapaDatosConsulta = (Map<String, Object>) request.getBody();
			mapaResultado = mongo.consulta("Cliente", mapaDatosConsulta);
			return new ResponseEntity<Object>(mapaResultado.values(), HttpStatus.OK);
		} catch (HttpMessageNotReadableException e) {
			 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultaEspecifica", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consulta(RequestEntity<Object> request) {

		Map<String, Object> mapaDatosConsulta = new HashMap<String, Object>();
		Map<String, Object> mapaResultado = new HashMap<String, Object>();

		List<String> datosAIgnorar = new ArrayList<>();

		try {
			mapaDatosConsulta = (Map<String, Object>) request.getBody();
			datosAIgnorar.add("_id");
			mapaResultado = mongo.consulta("Cliente", mapaDatosConsulta, datosAIgnorar);
			return new ResponseEntity<Object>(mapaResultado.values(), HttpStatus.OK);
		} catch (HttpMessageNotReadableException e) {
			 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/consultaTodo", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultaTodo() {

		Map<String, Object> mapaResultado = null;

		try {
			mapaResultado = mongo.consulta("Cliente");
			return new ResponseEntity<Object>(mapaResultado.values(), HttpStatus.OK);
		} catch (HttpMessageNotReadableException e) {
			 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Consultar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/eliminacion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> eliminacion(RequestEntity<Object> request) {

		Map<String, Object> mapaDatosConsulta = new HashMap<String, Object>();
		Map<String, Object> mapaResultado = new HashMap<String, Object>();
		;

		try {
			mapaDatosConsulta = (Map<String, Object>) request.getBody();
			WriteResult resultado = mongo.eliminar("Cliente", mapaDatosConsulta);

			if (resultado.getLastError().ok()) {
				mapaResultado.put("responseStatus", 200);
				mapaResultado.put("responseError", "OK");
			} else {
				mapaResultado.put("responseStatus", 400);
				mapaResultado.put("responseError", "Error, Al Eliminar");
			}
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		} catch (HttpMessageNotReadableException e) {
		 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Eliminar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Eliminar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/moficicacion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> moficicacion(RequestEntity<Object> request) {

		Map<String, Object> mapaDatosConsulta = new HashMap<String, Object>();
		Map<String, Object> mapaDatosAModificar = new HashMap<String, Object>();
		Map<String, Object> mapaResultado = new HashMap<String, Object>();

		Map<String, Object> mapaResultadoConsulta = new HashMap<String, Object>();
		Map<String, Object> mapaResultadoConsultaValor = new HashMap<String, Object>();
		List<String> datosAIgnorar = new ArrayList<>();

		Map<String, Object> mapaResultadofinal = new HashMap<String, Object>();
 
		WriteResult resultado = null;

		Operaciones operaciones=new Operaciones();
		try {
			mapaDatosConsulta = (Map<String, Object>) request.getBody();

			mapaDatosAModificar.putAll((Map<String, Object>) mapaDatosConsulta.get("datosAModificar"));

			mapaDatosConsulta.remove("datosAModificar");
			datosAIgnorar.add("_id");
			mapaResultadoConsulta = mongo.consulta("Cliente", mapaDatosConsulta, datosAIgnorar);

			mapaResultadoConsultaValor.putAll((Map<? extends String, ? extends Object>) mapaResultadoConsulta.get("1"));

			
			if (mapaResultadoConsulta.size() >= 1 && mapaResultadoConsulta.size() <= 1) {
				mapaResultadofinal = operaciones.modifica(mapaResultadoConsultaValor, mapaDatosAModificar);
				 
				resultado = mongo.modificacion("Cliente", mapaDatosConsulta, mapaResultadofinal);
			} else {

			}

			if (resultado.getLastError().ok()) {
				mapaResultado.put("responseStatus", 200);
				mapaResultado.put("responseError", "OK");
			} else {
				mapaResultado.put("responseStatus", 400);
				mapaResultado.put("responseError", "Error, Al Modificar");
			}
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		} catch (HttpMessageNotReadableException e) {
		 
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Modificar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			mapaResultado.put("responseStatus", 400);
			mapaResultado.put("responseError", "Error, Al Modificar");
			return new ResponseEntity<Object>(mapaResultado, HttpStatus.OK);
		}

	}

}
