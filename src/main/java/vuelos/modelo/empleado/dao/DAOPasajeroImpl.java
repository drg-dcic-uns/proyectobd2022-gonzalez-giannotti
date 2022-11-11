package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOPasajeroDatosPrueba;

public class DAOPasajeroImpl implements DAOPasajero {

	private static Logger logger = LoggerFactory.getLogger(DAOPasajeroImpl.class);

	private static final long serialVersionUID = 1L;

	// conexión para acceder a la Base de Datos
	private Connection conexion;

	public DAOPasajeroImpl(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public PasajeroBean recuperarPasajero(String tipoDoc, int nroDoc) throws Exception {
		/**
		 * TODO (parte 2) Deberá recuperar de la B.D. los datos de un pasajero que tenga
		 * el tipo de documento y numero pasados como parámetro y devolver los datos en
		 * un objeto EmpleadoBean. Si no existe el pasajero deberá retornar null y si
		 * ocurre algun error deberá generar y propagar una excepción.
		 *
		 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
		 * conexión establecida con el servidor de B.D. (inicializada en el constructor
		 * DAOPasajeroImpl(...)).
		 */

		PasajeroBean pasajero = null;
		try {
			String query = String.format("SELECT * FROM pasajeros WHERE doc_tipo='%s' AND doc_nro=%d;", 
							tipoDoc, nroDoc);
			Statement stmt = this.conexion.createStatement();
			ResultSet resultset = stmt.executeQuery(query);
			if (resultset.next()) {
				pasajero = new PasajeroBeanImpl();
				pasajero.setApellido(resultset.getString("apellido"));
				pasajero.setDireccion(resultset.getString("direccion"));
				pasajero.setNacionalidad(resultset.getString("nacionalidad"));
				pasajero.setNombre(resultset.getString("nombre"));
				pasajero.setNroDocumento(resultset.getInt("doc_nro"));
				pasajero.setTelefono(resultset.getString("telefono"));
				pasajero.setTipoDocumento(resultset.getString("doc_tipo"));

				stmt.close();
				resultset.close();

				logger.info("El DAO retorna al pasajero {} {}", pasajero.getApellido(), pasajero.getNombre());
			}
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw ex;
		}

		return pasajero;
	}

}
