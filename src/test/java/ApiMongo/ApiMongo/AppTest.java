package ApiMongo.ApiMongo;

/**
* Copyright (c)  2017 Nova Solution Systems S.A. de C.V.
* Mexico D.F.
* Todos los derechos reservados.
*
* @author NombreDelDesarrollador 
*
* ESTE SOFTWARE ES INFORMACIÓN CONFIDENCIAL. PROPIEDAD DE NOVA SOLUTION SYSTEMS.
* ESTA INFORMACIÓN NO DEBE SER DIVULGADA Y PUEDE SOLAMENTE SER UTILIZADA DE ACUERDO CON LOS TÉRMINOS DETERMINADOS POR LA EMPRESA SÍ MISMA.
*/

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import mx.com.beo.mongo.ServiceLayoutApp;
 
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLayoutApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class AppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@SuppressWarnings("unchecked")
	@Test
	public void preregistro() throws Exception {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
		
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> documento = new HashMap<String, Object>();

		body.put("operacion", 1);		
		body.put("transaccion", "interbancarios");

		documento.put("NUMERO_CLIENTE", "1234567898");
		documento.put("BANCO", "12345");
		documento.put("ID_TIPO_CUENTA", "2");
		documento.put("NUMERO_CUENTA", "123432345678987656");
		documento.put("BENEFICIARIO", "Fernando Romero");
		documento.put("ID_TIPO_TXN", "3");
		documento.put("ALIAS", "Mi cuenta");
		documento.put("MONEDA", "MXN");
		documento.put("MONTO_MAXIMO", "9999999999999");
		documento.put("CELULAR_NOTIFICACION", "5545679889");
		documento.put("EMAIL", "mail@mail.com");
		documento.put("RFC_CURP", "BABE990918HPLLGD07");
		documento.put("FECHA_ALTA", ",17 09 15 12:08:23");
		documento.put("FECHA_MODIFICACION", ",17 09 15 12:08:23");
		
		body.put("documento", documento);
		
		
		
		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);
		LOGGER.info("Cuerpo que se arma " + entity.getBody());
		System.out.println("Cabeceras que se arman" + entity.getHeaders());
		
		ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort("preregistro/operacionesBasicas"), HttpMethod.POST, entity, Object.class);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("responseStatus", 200);
		Map<String, Object> mapBody = new HashMap<String, Object>();
		LOGGER.info("Cabacera del servicio cosumido" + response.getHeaders());
		LOGGER.info("Cuerpo del servicio cosumido" + response.getBody());
		mapBody = (Map<String, Object>) response.getBody();
		assertEquals(respuesta.get("responseStatus"), mapBody.get("responseStatus"));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
