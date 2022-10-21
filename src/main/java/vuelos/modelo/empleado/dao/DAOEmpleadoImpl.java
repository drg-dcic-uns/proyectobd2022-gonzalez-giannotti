package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.EmpleadoBeanImpl;

public class DAOEmpleadoImpl implements DAOEmpleado {

	private static Logger logger = LoggerFactory.getLogger(DAOEmpleadoImpl.class);

	// conexión para acceder a la Base de Datos
	private Connection conexion;

	public DAOEmpleadoImpl(Connection c) {
		this.conexion = c;
	}

	/**
	 * TODO Debe recuperar de la B.D. los datos del empleado que corresponda al
	 * legajo pasado como parámetro y devolver los datos en un objeto EmpleadoBean.
	 * Si no existe el legajo deberá retornar null y si ocurre algun error deberá
	 * generar y propagar una excepción.
	 * 
	 * Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una
	 * conexión establecida con el servidor de B.D. (inicializada en el constructor
	 * DAOEmpleadoImpl(...)).
	 */
	@Override
	public EmpleadoBean recuperarEmpleado(int legajo) throws Exception {
		logger.info("Recupera el empleado que corresponde al legajo {}.", legajo);

		String query = "SELECT * FROM empleados WHERE legajo = %d;".formatted(legajo);
		
		logger.debug("SQL Query: {}", query);
		
		EmpleadoBean empleado = null;
		Statement select = null;
		ResultSet resultset = null;

		try {
			select = conexion.createStatement();
			resultset = select.executeQuery(query);
			if (resultset.next()) {

				// Si existe un empleado con el legajo indicado se crea el bean del empleado a
				// retornar y se cargan los datos correspondientes
				empleado = new EmpleadoBeanImpl();
				empleado.setLegajo(resultset.getInt("legajo"));
				empleado.setApellido(resultset.getString("apellido"));
				empleado.setNombre(resultset.getString("nombre"));
				empleado.setTipoDocumento(resultset.getString("doc_tipo"));
				empleado.setNroDocumento(resultset.getInt("doc_nro"));
				empleado.setDireccion(resultset.getString("direccion"));
				empleado.setTelefono(resultset.getString("telefono"));
				empleado.setPassword(resultset.getString("password"));
			}
			resultset.close();
			select.close();

		} catch (SQLException ex) {
			if (resultset != null && !resultset.isClosed())
				resultset.close();
			if (select != null && !select.isClosed())
				select.close();
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}
		
		return empleado;
	}

}
