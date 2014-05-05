package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.Conexion;

public class MapBassNotes {
	public List<BassNote> lista_bajo = new ArrayList<BassNote>();;
	
	public MapBassNotes(){
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = "SELECT id, alteracion, tercera, cuarta, quinta FROM Bass ";
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			while(rs.next()){
				BassNote temp = new BassNote();
				temp.alteracion = rs.getString(2);
				temp.tercera = rs.getInt(3);
				temp.cuarta = rs.getInt(4);
				temp.quinta = rs.getInt(5);
				
				lista_bajo.add(temp);
			}
			
			conexion.cerrarConexion();
			
			System.out.println("> 'MapBassNotes' cargado correctamente");
		}
		catch(Exception e){
			System.out.println(e.toString());
		}		
	}	
	
	
}


