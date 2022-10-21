package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		logger.info("recupera el empleado que corresponde al legajo {}.", legajo);

		String sql = "SELECT * FROM empleados WHERE legajo = " + legajo + ";";
		EmpleadoBean empleado = null;
		Statement select = null;
		ResultSet rs = null;
		
		try {
			select = conexion.createStatement();
			rs = select.executeQuery(sql);
			if(rs.next()) {
				
				// Si existe un empleado con el legajo indicado se crea el bean del empleado a retornar y se cargan los datos correspondientes
				empleado = new EmpleadoBeanImpl();
				empleado.setLegajo(rs.getInt("legajo"));
				empleado.setApellido(rs.getString("apellido"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setTipoDocumento(rs.getString("doc_tipo"));
				empleado.setNroDocumento(rs.getInt("doc_nro"));
				empleado.setDireccion(rs.getString("direccion"));
				empleado.setTelefono(rs.getString("telefono"));
				empleado.setPassword(rs.getString("password"));
			}
			rs.close();
			select.close();
			return empleado;
				
		}
		catch (SQLException ex)
		{			
			if(rs != null && !rs.isClosed())
				rs.close();
			if(select != null && !select.isClosed())
				select.close();
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}
	}

}
