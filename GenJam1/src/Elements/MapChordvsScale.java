package Elements;

import java.util.ArrayList;
import java.util.List;

public class MapChordvsScale {
	public List <ChordvsScale> CvsS;
	
	//Constructor que carga todos los Acordes con su respectiva Escala
	public MapChordvsScale (){
		
		CvsS = new ArrayList<ChordvsScale>();
		
		//** Cmaj7 **
		ChordvsScale temp = new ChordvsScale();
		
		temp.chord = "Cmaj7";
		
		temp.scale = "Major (avoid 4th)";
		
		//Notas del 0 al 14
		temp.notes = new ArrayList<String>();
		
		temp.notes.add("r");
		temp.notes.add("c4");
		temp.notes.add("d4");
		temp.notes.add("e4");
		temp.notes.add("g4");
		temp.notes.add("a4");
		temp.notes.add("b4");
		temp.notes.add("c5");
		temp.notes.add("d5");
		temp.notes.add("e5");
		temp.notes.add("g5");
		temp.notes.add("a5");
		temp.notes.add("b5");
		temp.notes.add("c6");
		temp.notes.add("d6");
		temp.notes.add("h");		
		
		CvsS.add(temp);
		
	}	
}


