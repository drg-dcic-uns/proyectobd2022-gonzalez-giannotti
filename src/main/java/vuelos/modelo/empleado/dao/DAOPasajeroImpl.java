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
			String sql = "SELECT * FROM pasajeros WHERE doc_tipo= '" + tipoDoc + "' AND doc_nro=" + nroDoc + ";";
			Statement s = this.conexion.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				pasajero = new PasajeroBeanImpl();
				pasajero.setApellido(rs.getString("apellido"));
				pasajero.setDireccion(rs.getString("direccion"));
				pasajero.setNacionalidad(rs.getString("nacionalidad"));
				pasajero.setNombre(rs.getString("nombre"));
				pasajero.setNroDocumento(rs.getInt("doc_nro"));
				pasajero.setTelefono(rs.getString("telefono"));
				pasajero.setTipoDocumento(rs.getString("doc_tipo"));
				s.close();
				rs.close();
			} else {
				throw new Exception("No se encontro al pasajero.");
			}
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}

		logger.info("El DAO retorna al pasajero {} {}", pasajero.getApellido(), pasajero.getNombre());

		return pasajero;

	}

}
