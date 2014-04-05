package Elements;

import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataBase.Conexion;

public class MapChordvsScale {
	public List <ChordvsScale> CvsS;
	
	//Constructor que carga todos los Acordes con su respectiva Escala
	public MapChordvsScale (){
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = "SELECT idChordvsScale, chord, scale FROM chordvsscale ";
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			while(rs.next()){
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
			}
						
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		
	}	
}


