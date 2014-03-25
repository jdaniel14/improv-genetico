package GenJam;

import Elements.MapChordvsScale;
import jm.music.data.Note;
import jm.util.Play;
import jm.JMC;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		//MapChordvsScale map = new MapChordvsScale();
		
		//System.out.println("Nota: " + map.CvsS.get(0).notes.get(5));
		
		//FuncionesArchivos.crearMeasureBD();
		Note note = new Note();
		note.setPitch(JMC.GS4);
		note.setDynamic(JMC.PP);
		note.setDuration(JMC.EIGHTH_NOTE);
		Play.midi(note);

	} 
	
	

}
