package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.Conexion;

public class PhrasePopulation {
	public List <Phrases> populationP;
	
	public PhrasePopulation(){
		populationP = new ArrayList<Phrases>();
		
		Conexion conexion = new Conexion();
		try{
			conexion.abrirConexion();
			String sql = "SELECT id, measureId, genre FROM Phrases LIMIT 16";
			
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
				List <Measures> list_measure = new ArrayList <Measures>();
				List<Integer> lista_int_measure = separar_measures(list_measure_string);
				for(int i = 0; i < lista_int_measure.size(); i++){
					int id_measure = lista_int_measure.get(i);
					Measures measure = Measures.GetMeasureId(id_measure);
					list_measure.add(measure);
				}
				Phrases new_Phrase = new Phrases(id, list_measure, genre);
				populationP.add(new_Phrase);
			}
			
			System.out.println("> 'PhrasePopulation' inicializado correctamente");
			
			conexion.cerrarConexion();
			
		} catch(Exception e) {
			
			System.out.println(e.toString());
		}
	}
	
	private List<Integer> separar_measures(String list_measure_string) {
		
		String[] a = list_measure_string.split(" ");
		
		List<Integer> dev = new ArrayList<Integer>();
		
		for(int i = 0; i < a.length; i++){
			dev.add(Integer.parseInt(a[i]));
		}
		
		return dev;
	}
}
