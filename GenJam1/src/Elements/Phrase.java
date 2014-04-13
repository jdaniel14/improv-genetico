package Elements;

import java.util.List;

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
