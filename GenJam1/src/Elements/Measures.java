package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataBase.Conexion;

//Bean para los Measures
public class Measures {
	public int id;
	public List<Integer> notas;
	
	public Measures(int _id, List<Integer> _notas){
		id = _id;
		notas = _notas;
	}
	
	public static Measures GetMeasureId(int id_measure) {
		Conexion conexion = new Conexion();
		int _id;
		List<Integer> _notas = new ArrayList<Integer>();
		try{
			conexion.abrirConexion();
			
			_id = id_measure;
			String sql = "SELECT notas FROM Measures WHERE id = " + id_measure;
			
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			if(rs.next()){	
				_notas = separar_notas(rs.getString(1));
			}else
				return null;
		} catch (Exception e){
			System.out.println(e.toString());
			return null;
		}
		return new Measures(_id, _notas);
	}
	
	public static List<Integer> separar_notas(String list_notas) {
		
		String[] a = list_notas.split(" ");
		
		List<Integer> dev = new ArrayList<Integer>();
		
		for(int i = 0; i < a.length; i++){
			dev.add(Integer.parseInt(a[i]));
		}
		
		return dev;
	}
}
