package Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import DataBase.Conexion;

//Bean para las Phrases
public class Phrase {
	public int id;
	public List<Measure> measureId;
	public String genre;
	
	public Phrase(int _id, List<Measure> _measureId, String _genre) {
		id = _id;
		measureId = _measureId;
		genre = _genre;
	}
}
