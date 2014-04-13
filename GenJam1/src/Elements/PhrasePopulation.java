package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.Conexion;

public class PhrasePopulation {
	List <Phrase> populationP;
	
	public PhrasePopulation(){
		populationP = new ArrayList<Phrase>();
		
		Conexion conexion = new Conexion();
		try{
			conexion.abrirConexion();
			String sql = "SELECT id, measureId, genre FROM Phrase ORDER BY RAND() LIMIT 16";
			
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			while(rs.next()){	
				int id = rs.getInt(1);
				String list_measure_string = rs.getString(2);
				String genre = rs.getString(3);
				
				/*
				 for de 4 measures 
				 */
				List <Measure> list_measure = new ArrayList <Measure>();
				List<Integer> lista_int_measure = separar_measures(list_measure_string);
				for(int i = 0; i < lista_int_measure.size(); i++){
					int id_measure = lista_int_measure.get(i);
					Measure measure = Measure.GetMeasureId(id_measure);
					list_measure.add(measure);
				}
				Phrase new_Phrase = new Phrase(id, list_measure, genre);
				populationP.add(new_Phrase);
			}
			
			System.out.println("> 'PhrasePopulation' inicializado correctamente");
			
			conexion.cerrarConexion();
			
		} catch(Exception e) {
			
			System.out.println(e.toString());
		}
	}
	
	public List<Integer> separar_measures(String list_measure_string) {
		
		String[] a = list_measure_string.split(" ");
		
		List<Integer> dev = new ArrayList<Integer>();
		
		for(int i = 0; i < a.length; i++){
			dev.add(Integer.parseInt(a[i]));
		}
		
		return dev;
	}
}
