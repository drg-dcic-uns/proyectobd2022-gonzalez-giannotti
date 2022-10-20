package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.AeropuertoBean;
import vuelos.modelo.empleado.beans.AeropuertoBeanImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOVuelosDatosPrueba;
import vuelos.utils.Fechas;

public class DAOVuelosImpl implements DAOVuelos {

	private static Logger logger = LoggerFactory.getLogger(DAOVuelosImpl.class);

	// conexión para acceder a la Base de Datos
	private Connection conexion;

	public DAOVuelosImpl(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen,
			UbicacionesBean destino) throws Exception {
		/**
		 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y
		 * destino según los parámetros. Debe propagar una excepción si hay algún error
		 * en la consulta.
		 * 
		 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
		 * conexión establecida con el servidor de B.D. (inicializada en el constructor
		 * DAOVuelosImpl(...)).
		 */

		String fechaVuelo_DBformatted = Fechas.convertirDateAStringDB(fechaVuelo);

		String origen_ciudad = origen.getCiudad();
		String origen_estado = origen.getEstado();
		String origen_pais = origen.getPais();

		String destino_ciudad = destino.getCiudad();
		String destino_estado = destino.getEstado();
		String destino_pais = destino.getPais();

		//Realizamos la consulta en la que incluímos la tabla de aeropuertos para poder armar los AeropuertoBean correspondientes al de salida y llegada del vuelo disponible
		String query = "SELECT *" + " FROM vuelos_disponibles NATURAL JOIN aeropuertos"
				+ " WHERE (fecha='%s' AND ciudad_sale='%s' AND estado_sale='%s' AND pais_sale='%s' AND ciudad_llega='%s' AND estado_llega='%s' AND pais_llega='%s')"
						.formatted(fechaVuelo_DBformatted, origen_ciudad, origen_estado, origen_pais, destino_ciudad,
								destino_estado, destino_pais)
				+ " AND codigo_aero_sale=aeropuertos.codigo;";

		logger.debug("SQL Query: {}", query);

		// La información del arraylist debe ser: nro_vuelo, aeropuerto_salida, hora_salida, aeropuerto_llegada, hora_llegada, modelo_avion, tiempo_estimado
		ArrayList<InstanciaVueloBean> toReturn = new ArrayList<InstanciaVueloBean>();

		try {
			Statement select = conexion.createStatement();
			ResultSet resultset = select.executeQuery(query);

			while (resultset.next()) {
//				logger.debug("Se recuperó el item con nombre {} y fecha {}", resultset.getString("nombre_batalla"),
//						resultset.getDate("fecha"));
				
				UbicacionesBean ubic_aero_salida = new UbicacionesBeanImpl();
				ubic_aero_salida.setCiudad(resultset.getString("ciudad_sale"));
				ubic_aero_salida.setEstado(resultset.getString("estado_sale"));
				ubic_aero_salida.setPais(resultset.getString("pais_sale"));
				
				System.out.println("UBICACIÓN DE SALIDA QUE VA A PARAR EN EL AEROPUERTO BEAN");
				System.out.println(ubic_aero_salida.getCiudad());
				System.out.println(ubic_aero_salida.getEstado());
				System.out.println(ubic_aero_salida.getPais());
				
				AeropuertoBean aero_salida = new AeropuertoBeanImpl();
				aero_salida.setCodigo(resultset.getString("codigo_aero_sale"));
				aero_salida.setDireccion(resultset.getString("direccion"));
				aero_salida.setNombre(resultset.getString("nombre_aero_sale"));
				aero_salida.setTelefono(resultset.getString("telefono"));
				aero_salida.setUbicacion(ubic_aero_salida);
				
				System.out.println("AEROPUERTO DE SALIDA:");
				System.out.println(aero_salida.getCodigo());
				System.out.println(aero_salida.getTelefono());
				
				//Hacer lo mismo para la ubicacion de llegada y el aeropuerto de llegada
				
				//----------------------------------------------------
				
				InstanciaVueloBean iv = new InstanciaVueloBeanImpl();
				iv.setNroVuelo(resultset.getString("nro_vuelo"));
				iv.setModelo(resultset.getString("modelo"));
				iv.setDiaSalida(resultset.getString("dia_sale"));
				iv.setAeropuertoSalida(aero_salida);
				
				//Todavia no funciona porque falta implementar que se pueda elegir las opciones correctas de las listas desplegables origen y destino
				toReturn.add(iv);
			}

		} catch (Exception e) {
			throw new Exception("Error inesperado al consultar la B.D.");
		}

		// Ej: Recupera la lista de vuelos disponibles para la fecha Thu Oct 06 23:59:09
		// ART 2022 desde Argentina - CABA - Buenos Aires a España - Cataluña -
		// Barcelona.
		
		System.out.println(toReturn.toString());

		return toReturn;
	}

	@Override
	public ArrayList<DetalleVueloBean> recuperarDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {
		/**
		 * TODO Debe retornar una lista de clases, precios y asientos disponibles de
		 * dicho vuelo. Debe propagar una excepción si hay algún error en la consulta.
		 * 
		 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
		 * conexión establecida con el servidor de B.D. (inicializada en el constructor
		 * DAOVuelosImpl(...)).
		 */
		// Datos estáticos de prueba. Quitar y reemplazar por código que recupera los
		// datos reales.
		ArrayList<DetalleVueloBean> resultado = DAOVuelosDatosPrueba.generarDetalles(vuelo);

		return resultado;
		// Fin datos estáticos de prueba.
	}
}
