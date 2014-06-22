package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.Conexion;

public class MapVoicingNotes {
	public List<VoicingNote> lista_voicings = new ArrayList<VoicingNote>();
	
	public MapVoicingNotes(){
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" SELECT id, alteracion, voz1, voz2, voz3, voz4 " +
							" FROM Voicings ";
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			while(rs.next()){
				VoicingNote temp = new VoicingNote();
				temp.alteracion = rs.getString(2);
				temp.voz1 = rs.getInt(3);
				temp.voz2 = rs.getInt(4);
				temp.voz3 = rs.getInt(5);
				temp.voz4 = rs.getInt(6);
				
				lista_voicings.add(temp);
			}
			
			conexion.cerrarConexion();
			
			System.out.println("> 'MapVoicingNotes' cargado correctamente");
		}
		catch(Exception e){
			System.out.println(e.toString());
		}		

	}
}
