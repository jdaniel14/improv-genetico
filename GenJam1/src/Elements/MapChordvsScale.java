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
		temp.notes.add("c1");
		temp.notes.add("d1");
		temp.notes.add("e1");
		temp.notes.add("g1");
		temp.notes.add("a1");
		temp.notes.add("b1");
		temp.notes.add("c2");
		temp.notes.add("d2");
		temp.notes.add("e2");
		temp.notes.add("g2");
		temp.notes.add("a2");
		temp.notes.add("b2");
		temp.notes.add("c3");
		temp.notes.add("d3");
		temp.notes.add("h");		
		
		CvsS.add(temp);
		
	}	
}


