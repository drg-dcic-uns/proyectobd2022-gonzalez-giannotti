package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

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

	@SuppressWarnings("deprecation")
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

		// funciona bien tu version pero no funciona con mi recuperarDetalle() no se
		// porque !
		// --> El error estaba en el seteo de la fecha del vuelo.

//		ArrayList<InstanciaVueloBean> resultado = new ArrayList<InstanciaVueloBean>();
//
//		String fecha = Fechas.convertirDateAStringDB(fechaVuelo);
//		String sql = "select v.nro_vuelo,v.dia_sale,v.hora_sale,v.hora_llega,v.tiempo_estimado,v.modelo,v.fecha,"
//				+ "v.codigo_aero_sale, v.codigo_aero_llega, v.pais_sale, v.estado_sale, v.ciudad_sale, v.pais_llega, v.estado_llega,"
//				+ "v.ciudad_llega, v.codigo_aero_sale,v.codigo_aero_llega,v.pais_sale,v.estado_sale,v.ciudad_sale,v.pais_llega,v.estado_llega, v.ciudad_llega,"
//				+ " s.telefono as telefono_aero_sale, s.nombre as nombre_aero_sale, s.direccion as direccion_aero_sale,"
//				+ " l.telefono as telefono_aero_llega, l.nombre as nombre_aero_llega, l.direccion as direccion_aero_llega,"
//				+ " ul.huso as huso_llega, us.huso as huso_sale  from vuelos_disponibles AS v inner join aeropuertos as s on codigo_aero_sale = s.codigo"
//				+ " inner join aeropuertos l on codigo_aero_llega= l.codigo inner join"
//				+ " ubicaciones ul on pais_llega= ul.pais and estado_llega = ul.estado and ciudad_llega = ul.ciudad   inner join"
//				+ " ubicaciones us on pais_sale= us.pais and estado_sale = us.estado and ciudad_sale = us.ciudad  WHERE v.fecha= '"
//				+ fecha + "' and v.ciudad_sale= '" + origen.getCiudad() + "' and " + " v.estado_sale= '"
//				+ origen.getEstado() + "' and v.pais_sale= '" + origen.getPais() + "' and v.ciudad_llega= '"
//				+ destino.getCiudad() + "' and " + "v.estado_llega= '" + destino.getEstado() + "' and v.pais_llega= '"
//				+ destino.getPais() + "';";
//		try {
//			Statement s = conexion.createStatement();
//			ResultSet rs = s.executeQuery(sql);
//			while (rs.next()) {
//				AeropuertoBean abSalida = new AeropuertoBeanImpl();
//				AeropuertoBean abLlegada = new AeropuertoBeanImpl();
//
//				abSalida.setCodigo(rs.getString("codigo_aero_sale"));
//				abSalida.setDireccion(rs.getString("direccion_aero_sale"));
//				abSalida.setNombre(rs.getString("nombre_aero_sale"));
//				abSalida.setTelefono(rs.getString("telefono_aero_sale"));
//
//				abLlegada.setCodigo(rs.getString("codigo_aero_llega"));
//				abLlegada.setDireccion(rs.getString("direccion_aero_llega"));
//				abLlegada.setNombre(rs.getString("nombre_aero_llega"));
//				abLlegada.setTelefono(rs.getString("telefono_aero_llega"));
//
//				UbicacionesBean ubSalida = new UbicacionesBeanImpl();
//				UbicacionesBean ubLlegada = new UbicacionesBeanImpl();
//
//				ubSalida.setCiudad(rs.getString("ciudad_sale"));
//				ubSalida.setEstado(rs.getString("estado_sale"));
//				ubSalida.setHuso(rs.getInt("huso_sale"));
//				ubSalida.setPais(rs.getString("pais_sale"));
//
//				ubLlegada.setCiudad(rs.getString("ciudad_llega"));
//				ubLlegada.setEstado(rs.getString("estado_llega"));
//				ubLlegada.setHuso(rs.getInt("huso_llega"));
//				ubLlegada.setPais(rs.getString("pais_llega"));
//
//				abSalida.setUbicacion(ubSalida);
//				abLlegada.setUbicacion(ubLlegada);
//
//				InstanciaVueloBean ins = new InstanciaVueloBeanImpl();
//				ins.setAeropuertoLlegada(abLlegada);
//				ins.setAeropuertoSalida(abSalida);
//				ins.setNroVuelo(rs.getString("nro_vuelo"));
//				ins.setFechaVuelo(rs.getDate("fecha"));
//				ins.setDiaSalida(rs.getString("dia_sale"));
//				ins.setHoraLlegada(rs.getTime("hora_llega"));
//				ins.setHoraSalida(rs.getTime("hora_sale"));
//				ins.setTiempoEstimado(rs.getTime("tiempo_estimado"));
//				ins.setModelo(rs.getString("modelo"));
//
//				resultado.add(ins);
//
//			}
//			s.close();
//			rs.close();
//		}
//
//		catch (SQLException ex) {
//			logger.error("SQLException: " + ex.getMessage());
//			logger.error("SQLState: " + ex.getSQLState());
//			logger.error("VendorError: " + ex.getErrorCode());
//		}
//
//		return resultado;

		String fechaVuelo_DBformatted = Fechas.convertirDateAStringDB(fechaVuelo);

		String origen_ciudad = origen.getCiudad();
		String origen_estado = origen.getEstado();
		String origen_pais = origen.getPais();

		String destino_ciudad = destino.getCiudad();
		String destino_estado = destino.getEstado();
		String destino_pais = destino.getPais();
		/*
		 * Dos alternativas: 1. con una sola consulta SQL obtenemos lo pedido y además
		 * la información de los aeropuertos de salida y llegada distinguidos. 2. hacer
		 * varias consultas SQL, primero la que obtiene lo pedido, y luego dos más (o
		 * una sola) para obtener la informacion de los aeropuertos.
		 * 
		 * Se prosigue con la primera.
		 */

		// Realizamos la consulta en la que incluímos la tabla de aeropuertos para poder
		// armar los AeropuertoBean correspondientes al de salida y llegada del vuelo
		// disponible.
		String query = "SELECT nro_vuelo, fecha, codigo_aero_sale, nombre_aero_sale, dia_sale, ciudad_sale, estado_sale, pais_sale, hora_sale, codigo_aero_llega, nombre_aero_llega, ciudad_llega, estado_llega, pais_llega, hora_llega, modelo, tiempo_estimado, a1.direccion AS direccion_sale, a1.telefono AS telefono_sale, a2.direccion AS direccion_llega, a2.telefono AS telefono_llega"
				+ " FROM vuelos_disponibles, aeropuertos a1, aeropuertos a2"
				+ " WHERE (fecha='%s' AND ciudad_sale='%s' AND estado_sale='%s' AND pais_sale='%s' AND ciudad_llega='%s' AND estado_llega='%s' AND pais_llega='%s')"
						.formatted(fechaVuelo_DBformatted, origen_ciudad, origen_estado, origen_pais, destino_ciudad,
								destino_estado, destino_pais)
				+ " AND codigo_aero_sale=a1.codigo AND codigo_aero_llega=a2.codigo GROUP BY nro_vuelo;";

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

				UbicacionesBean ubic_aero_salida = new UbicacionesBeanImpl();
				ubic_aero_salida.setCiudad(resultset.getString("ciudad_sale"));
				ubic_aero_salida.setEstado(resultset.getString("estado_sale"));
				ubic_aero_salida.setPais(resultset.getString("pais_sale"));

				AeropuertoBean aero_salida = new AeropuertoBeanImpl();
				aero_salida.setCodigo(resultset.getString("codigo_aero_sale"));
				aero_salida.setDireccion(resultset.getString("direccion_sale"));
				aero_salida.setNombre(resultset.getString("nombre_aero_sale"));
				aero_salida.setTelefono(resultset.getString("telefono_sale"));
				aero_salida.setUbicacion(ubic_aero_salida);

				UbicacionesBean ubic_aero_llegada = new UbicacionesBeanImpl();
				ubic_aero_llegada.setCiudad(resultset.getString("ciudad_llega"));
				ubic_aero_llegada.setEstado(resultset.getString("estado_llega"));
				ubic_aero_llegada.setPais(resultset.getString("pais_llega"));

				AeropuertoBean aero_llegada = new AeropuertoBeanImpl();
				aero_llegada.setCodigo(resultset.getString("codigo_aero_llega"));
				aero_llegada.setDireccion(resultset.getString("direccion_llega"));
				aero_llegada.setNombre(resultset.getString("nombre_aero_llega"));
				aero_llegada.setTelefono(resultset.getString("telefono_llega"));
				aero_llegada.setUbicacion(ubic_aero_llegada);

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
		String sql = "SELECT clase, precio, asientos_disponibles" + " FROM vuelos_disponibles" + " WHERE nro_vuelo= '"
				+ vuelo.getNroVuelo() + "' " + " AND fecha= '" + fechaVuelo + "' ;";

		try {
			Statement select = conexion.createStatement();
			ResultSet resultset = select.executeQuery(sql);
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
