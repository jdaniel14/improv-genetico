package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
	public Connection conn;//protected
	
	public Conexion(){
		this.conn = null;
	}
	
	public void commit() throws SQLException{
		conn.commit();
	}
	
	public void rollback() throws SQLException{
		conn.rollback();
	}
	
	public Connection abrirConexion() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		conn = null;
		String username="root";
      
		String password = "123";

		String database = "genjam";

		Class.forName("com.mysql.jdbc.Driver").newInstance();

		conn = DriverManager.getConnection("jdbc:mysql://localhost/"+database, username, password);
		conn.setAutoCommit(false);
     return conn;
	}

	public void cerrarConexion() throws SQLException{
			conn.close();
	}
}
