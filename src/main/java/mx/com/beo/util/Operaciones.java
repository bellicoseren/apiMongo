package mx.com.beo.util;

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


public class Operaciones {
	
	
	public Map<String, Object> modifica(Map<String, Object> mapActual, Map<String, Object> mapaModificar) {

		for (Map.Entry<String, Object> entry : mapActual.entrySet()) {

			for (Map.Entry<String, Object> entryMod : mapaModificar.entrySet()) {

				if (entry.getKey().equals(entryMod.getKey())) {

					mapActual.put(entry.getKey(), entryMod.getValue());
				}

			}

		}
		return mapActual;
	}

}
