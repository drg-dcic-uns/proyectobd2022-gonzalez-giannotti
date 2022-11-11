package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;

public class DAOReservaImpl implements DAOReserva {

	private static Logger logger = LoggerFactory.getLogger(DAOReservaImpl.class);
	
	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOReservaImpl(Connection conexion) {
		this.conexion = conexion;
	}
		
	
	@Override
	public int reservarSoloIda(PasajeroBean pasajero, 
							   InstanciaVueloBean vuelo, 
							   DetalleVueloBean detalleVuelo,
							   EmpleadoBean empleado) throws Exception {
		logger.info("Realiza la reserva de solo ida con pasajero {}", pasajero.getNroDocumento());
		
		/**
		 * TODO (parte 2) Realizar una reserva de ida solamente llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 *		
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que el controlador se encargue de manejarla.
		 */
		
		int nro_reserva = -1;
		String estado_reserva = "";
		String query = String.format("CALL reservar_ida(''%s'', ''%s'', ''%s'', ''%s'', %d, %d);", vuelo.getNroVuelo(), vuelo.getFechaVuelo(), detalleVuelo.getClase(), pasajero.getTipoDocumento(), pasajero.getNroDocumento(), empleado.getLegajo());
		
		try {
			CallableStatement cstmt = conexion.prepareCall(query);
			cstmt.executeUpdate();
			ResultSet resultset = cstmt.getResultSet();
			
				 if(resultset.next()) {
					 String s = resultset.getString("resultado"); 
					 if(s.contains("Falla")) {
						 throw new Exception(s);						 
					 }
					 nro_reserva = resultset.getInt("nroReserva");
					 estado_reserva = resultset.getString("estado_reserva");
				 }
			cstmt.close();
			resultset.close();
			
			
		} catch (SQLException ex) {
			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
			throw ex;
		}
		
		logger.debug("Reserva: {}, {}", nro_reserva, estado_reserva);
		
		return nro_reserva;
	}
	
	@Override
	public int reservarIdaVuelta(PasajeroBean pasajero, 
				 				 InstanciaVueloBean vueloIda,
				 				 DetalleVueloBean detalleVueloIda,
				 				 InstanciaVueloBean vueloVuelta,
				 				 DetalleVueloBean detalleVueloVuelta,
				 				 EmpleadoBean empleado) throws Exception {
		
		logger.info("Realiza la reserva de ida y vuelta con pasajero {}", pasajero.getNroDocumento());
		/**
		 * TODO (parte 2) Realizar una reserva de ida y vuelta llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que se encargue el controlador.
		 **/
		
		int nroReserva = -1;
		 
		 try {
			 
			 String query = String.format("CALL reservar_ida_vuelta('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d);",
					 		vueloIda.getNroVuelo(), vueloVuelta.getNroVuelo(), vueloIda.getFechaVuelo(), vueloVuelta.getFechaVuelo(), detalleVueloIda.getClase(), detalleVueloVuelta.getClase(), pasajero.getTipoDocumento(), pasajero.getNroDocumento(), empleado.getLegajo());
			 
			 CallableStatement cstmt = conexion.prepareCall(query); 
			 cstmt.executeUpdate();
			 ResultSet resultset = cstmt.getResultSet();
			 
			 if(resultset.next()) {
				 String s = resultset.getString("resultado"); 
				 if(s.contains("Falla"))
					 throw new Exception(s);
				 nroReserva = resultset.getInt("nroReserva");
			 }
			 cstmt.close();
			 resultset.close();
		  } catch (SQLException ex){
		  			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   		throw ex;
		 } 
		return nroReserva;
	}
	
	@Override
	public ReservaBean recuperarReserva(int codigoReserva) throws Exception {
		
		logger.info("Solicita recuperar información de la reserva con codigo {}", codigoReserva);
		
		/**
		 * TODO (parte 2) Debe realizar una consulta que retorne un objeto ReservaBean donde tenga los datos de la
		 *      reserva que corresponda con el codigoReserva y en caso que no exista generar una excepción.
		 *
		 * 		Debe poblar la reserva con todas las instancias de vuelo asociadas a dicha reserva y 
		 * 		las clases correspondientes.
		 * 
		 * 		Los objetos ReservaBean además de las propiedades propias de una reserva tienen un arraylist
		 * 		con pares de instanciaVuelo y Clase. Si la reserva es solo de ida va a tener un unico
		 * 		elemento el arreglo, y si es de ida y vuelta tendrá dos elementos. 
		 * 
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 */
		
		/*
		 * Importante, tenga en cuenta de setear correctamente el atributo IdaVuelta con el método setEsIdaVuelta en la ReservaBean
		 */
		
		ReservaBean reserva = new ReservaBeanImpl();
		
		logger.debug("Se recuperó la reserva: {}, {}", reserva.getNumero(), reserva.getEstado());
		
		return reserva;
	}
	

}
