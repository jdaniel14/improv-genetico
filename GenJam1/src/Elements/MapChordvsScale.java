package Elements;

import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataBase.Conexion;

public class MapChordvsScale {
	public List <ChordvsScale> CvsS = new ArrayList<ChordvsScale>();
	
	public List<String> separarNotasEnUnaCadena(String notas){
		String[] a = notas.split(" ");
		
		List<String> dev = new ArrayList<String>();
		//dev.add("r");
		
		for(int i = 0; i < a.length; i++){
			dev.add(a[i]);
		}
		
		dev.add("T");
		//dev.add("h");
		
		return dev;
	}
	
	//Constructor que carga todos los Acordes con su respectiva Escala
	public MapChordvsScale (){
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = "SELECT id, chord, scale, notasTeoricas, notes FROM chordvsscale ";
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			while(rs.next()){
				ChordvsScale temp = new ChordvsScale();
				
				temp.chord = rs.getString(2);
				temp.scale = rs.getString(3);
				temp.notes = separarNotasEnUnaCadena(rs.getString(5));
				
				CvsS.add(temp);
			}
			
			//System.out.println("> 'MapChordvsScale' inicializado correctamente");
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		
	}	
}


