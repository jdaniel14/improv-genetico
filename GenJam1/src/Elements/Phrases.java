package Elements;

import java.util.List;

//Bean para las Phrases
public class Phrases {
	public int id;
	public List<Measures> measureId;
	public String genre;
	
	public Phrases(int _id, List<Measures> _measureId, String _genre) {
		id = _id;
		measureId = _measureId;
		genre = _genre;
	}
}
