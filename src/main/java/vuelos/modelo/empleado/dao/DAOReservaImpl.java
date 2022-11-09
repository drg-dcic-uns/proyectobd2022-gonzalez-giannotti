package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOReservaDatosPrueba;

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
		
		/*
		 * reservar_ida(IN numero VARCHAR(45), IN fecha DATE, IN clase VARCHAR(20),
                              IN tipo_doc VARCHAR(3), IN nro_doc INT, IN legajo_empleado INT)
		 */
		
		
		int nro_reserva = -1;		
		String query = String.format("CALL reservar_ida(%s, %s, %s, %s, %d, %d);", vuelo.getNroVuelo(), vuelo.getFechaVuelo(), detalleVuelo.getClase(), pasajero.getTipoDocumento(), pasajero.getNroDocumento(), empleado.getLegajo());
		
		try {
			CallableStatement cstmt = conexion.prepareCall(query);
			ResultSet resultset = cstmt.executeQuery();
			
			while (resultset.next()) {
				String resultado = resultset.getString("resultado");
				//Lo veo muy hardcodeado, si no tuviéramos conocimiento de la estructura interna de los procedures entonces no sabríamos cuál es el mensaje específico con el que hay que comparar?
				if (resultado == "La reserva se ha realizado con exito") {
					//Si otra petición se completó justo entre medio de esta y el last_insert_id (por concurrencia) entonces se sobreescribe su valor y no sirve.
					//Otra idea que se me ocurre es acceder a la tabla de reservas, ordenar descendentemente y rescatar el ultimo numero (es llave).
					//También viendo el debug comentado de más abajo se me ocurre que en realidad habría que rescatar la tupla entera.
					query = "SELECT LAST_INSERT_ID() AS nro_reserva";
					PreparedStatement stmt = conexion.prepareStatement(query);
					ResultSet resultset2 = stmt.executeQuery(query);
					
					if (resultset2.next()) {
						nro_reserva = resultset2.getInt("nro_reserva");
					}
					
					stmt.close();
					resultset2.close();
				}
				else {
					throw new Exception(resultado);
				}
			}
			
			
			cstmt.close();
			resultset.close();
			
		} catch (SQLException ex) {
			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
			throw ex;
		}
		
		
		//logger.debug("Reserva: {}, {}", r.getNumero(), r.getEstado());
		
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
		 *
		 * try (CallableStatement ... )
		 * {
		 *  ...
		 * }
		 * catch (SQLException ex){
		 * 			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		 *  		throw ex;
		 * } 
		 */
		
		/*
		 * Datos státicos de prueba: Quitar y reemplazar por código que invoca al S.P.
		 * 
		 * - Si pasajero tiene nro_doc igual a 1 retorna 101 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva confirmada"
		 * - Si pasajero tiene nro_doc igual a 2 retorna 102 codigo de reserva y si se pregunta por dicha reserva como dato de prueba resultado "Reserva en espera"
		 * - Si pasajero tiene nro_doc igual a 3 se genera una excepción, resultado "No hay asientos disponibles"
		 * - Si pasajero tiene nro_doc igual a 4 se genera una excepción, resultado "El empleado no es válido"
		 * - Si pasajero tiene nro_doc igual a 5 se genera una excepción, resultado "El pasajero no está registrado"
		 * - Si pasajero tiene nro_doc igual a 6 se genera una excepción, resultado "El vuelo no es válido"
		 * - Si pasajero tiene nro_doc igual a 7 se genera una excepción de conexión.
		 */		
		DAOReservaDatosPrueba.registrarReservaIdaVuelta(pasajero, vueloIda, detalleVueloIda, vueloVuelta, detalleVueloVuelta, empleado);
		int resultado = DAOReservaDatosPrueba.getReserva().getNumero();
		
		return resultado;
		// Fin datos estáticos de prueba.
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
