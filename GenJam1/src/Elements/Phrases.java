package Elements;

import java.util.ArrayList;
import java.util.List;

//Bean para las Phrases
public class Phrases {
	public int id;
	public List<Measures> measure_list;
	public String genre;
	
	public Phrases(){
		id = 0;
		measure_list = new ArrayList<Measures>();
		genre = "Jazz";
	}
	
	public Phrases(int _id, List<Measures> _measureId, String _genre) {
		id = _id;
		measure_list = _measureId;
		genre = _genre;
	}
}
