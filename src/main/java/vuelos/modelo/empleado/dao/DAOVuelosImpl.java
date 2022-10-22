package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.AeropuertoBean;
import vuelos.modelo.empleado.beans.AeropuertoBeanImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.dao.datosprueba.DAOVuelosDatosPrueba;
import vuelos.utils.Fechas;

public class DAOVuelosImpl implements DAOVuelos {

	private static Logger logger = LoggerFactory.getLogger(DAOVuelosImpl.class);

	// conexión para acceder a la Base de Datos
	private Connection conexion;

	public DAOVuelosImpl(Connection conexion) {
		this.conexion = conexion;
	}

	/**
	 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y
	 * destino según los parámetros. Debe propagar una excepción si hay algún error
	 * en la consulta.
	 * 
	 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
	 * conexión establecida con el servidor de B.D. (inicializada en el constructor
	 * DAOVuelosImpl(...)).
	 */
	@Override
	public ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen,
			UbicacionesBean destino) throws Exception {

		String fechaVuelo_StringDB = Fechas.convertirDateAStringDB(fechaVuelo);

		String origen_ciudad = origen.getCiudad();
		String origen_estado = origen.getEstado();
		String origen_pais = origen.getPais();

		String destino_ciudad = destino.getCiudad();
		String destino_estado = destino.getEstado();
		String destino_pais = destino.getPais();
		
		/*
		 * Dos alternativas: Una, con una sola consulta SQL obtenemos lo pedido y además
		 * la información de los aeropuertos de salida y llegada distinguidos. La otra, hacer
		 * varias consultas SQL, primero la que obtiene lo pedido, y luego dos más (o
		 * una sola) para obtener la informacion de los aeropuertos.
		 * 
		 * Se prosigue con la primera.
		 */

		// Realizamos la consulta en la que incluímos la tabla de aeropuertos para poder
		// armar los AeropuertoBean correspondientes al de salida y llegada del vuelo
		// disponible.
		String query = String.format("SELECT nro_vuelo, fecha, codigo_aero_sale, nombre_aero_sale, dia_sale, ciudad_sale, estado_sale, pais_sale, hora_sale, codigo_aero_llega, nombre_aero_llega, ciudad_llega, estado_llega, pais_llega, hora_llega, modelo, tiempo_estimado, a1.direccion AS direccion_sale, a1.telefono AS telefono_sale, a2.direccion AS direccion_llega, a2.telefono AS telefono_llega"
				+ " FROM vuelos_disponibles, aeropuertos a1, aeropuertos a2"
				+ " WHERE (fecha='%s' AND ciudad_sale='%s' AND estado_sale='%s' AND pais_sale='%s' AND ciudad_llega='%s' AND estado_llega='%s' AND pais_llega='%s')"
				+ " AND codigo_aero_sale=a1.codigo AND codigo_aero_llega=a2.codigo GROUP BY nro_vuelo;"
				, fechaVuelo_StringDB, origen_ciudad, origen_estado, origen_pais, destino_ciudad,
						destino_estado, destino_pais);

		logger.debug("SQL Query: {}", query);

		// La información del arraylist debe ser: nro_vuelo, aeropuerto_salida,
		// hora_salida, aeropuerto_llegada, hora_llegada, modelo_avion, tiempo_estimado
		ArrayList<InstanciaVueloBean> toReturn = new ArrayList<InstanciaVueloBean>();

		try {
			Statement select = conexion.createStatement();
			ResultSet resultset = select.executeQuery(query);

			while (resultset.next()) {
				logger.debug("Se recuperó el item con aeropuerto de salida {}, aeropuerto de llegada {} y fecha {}",
						resultset.getString("nombre_aero_sale"), resultset.getString("nombre_aero_llega"),
						resultset.getDate("fecha"));

				AeropuertoBean aero_salida = new AeropuertoBeanImpl();
				aero_salida.setCodigo(resultset.getString("codigo_aero_sale"));
				aero_salida.setDireccion(resultset.getString("direccion_sale"));
				aero_salida.setNombre(resultset.getString("nombre_aero_sale"));
				aero_salida.setTelefono(resultset.getString("telefono_sale"));
				aero_salida.setUbicacion(origen);

				AeropuertoBean aero_llegada = new AeropuertoBeanImpl();
				aero_llegada.setCodigo(resultset.getString("codigo_aero_llega"));
				aero_llegada.setDireccion(resultset.getString("direccion_llega"));
				aero_llegada.setNombre(resultset.getString("nombre_aero_llega"));
				aero_llegada.setTelefono(resultset.getString("telefono_llega"));
				aero_llegada.setUbicacion(destino);

				InstanciaVueloBean iv = new InstanciaVueloBeanImpl();
				iv.setNroVuelo(resultset.getString("nro_vuelo"));
				iv.setModelo(resultset.getString("modelo"));
				iv.setDiaSalida(resultset.getString("dia_sale"));
				iv.setHoraLlegada(resultset.getTime("hora_llega"));
				iv.setHoraSalida(resultset.getTime("hora_sale"));
				iv.setTiempoEstimado(resultset.getTime("tiempo_estimado"));
				iv.setAeropuertoSalida(aero_salida);
				iv.setAeropuertoLlegada(aero_llegada);
				iv.setFechaVuelo(resultset.getDate("fecha"));

				toReturn.add(iv);
			}

			resultset.close();
			select.close();
		} catch (Exception e) {
			throw new Exception("Error inesperado al consultar la B.D.");
		}

		return toReturn;
	}

	/**
	 * TODO Debe retornar una lista de clases, precios y asientos disponibles de
	 * dicho vuelo. Debe propagar una excepción si hay algún error en la consulta.
	 * 
	 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
	 * conexión establecida con el servidor de B.D. (inicializada en el constructor
	 * DAOVuelosImpl(...)).
	 */
	@Override
	public ArrayList<DetalleVueloBean> recuperarDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {
		ArrayList<DetalleVueloBean> resultado = DAOVuelosDatosPrueba.generarDetalles(vuelo);
		String fechaVuelo = Fechas.convertirDateAStringDB(vuelo.getFechaVuelo());
		
		String query = String.format("SELECT clase, precio, asientos_disponibles "
				+ " FROM vuelos_disponibles"
				+ " WHERE nro_vuelo='%s' AND fecha='%s';", vuelo.getNroVuelo(), fechaVuelo);

		logger.debug("SQL Query: {}", query);

		try {
			Statement select = conexion.createStatement();
			ResultSet resultset = select.executeQuery(query);
			while (resultset.next()) {
				DetalleVueloBeanImpl dv = new DetalleVueloBeanImpl();
				dv.setAsientosDisponibles(resultset.getInt("asientos_disponibles"));
				dv.setClase(resultset.getString("clase"));
				dv.setPrecio(resultset.getFloat("precio"));
				dv.setVuelo(vuelo);
				resultado.add(dv);
			}
			select.close();
			resultset.close();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}

		return resultado;
	}
}
