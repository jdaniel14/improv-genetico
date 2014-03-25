package GenJam;

import java.util.ArrayList;
import java.util.List;

import Elements.MapChordvsScale;
import Elements.MapNotevsSound;
import jm.music.data.Note;
import jm.util.Play;
import jm.JMC;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		MapNotevsSound mapSonidos = new MapNotevsSound();
		
		//MapChordvsScale map = new MapChordvsScale();
		
		//System.out.println("Nota: " + map.CvsS.get(0).notes.get(5));
		
		//FuncionesArchivos.crearMeasureBD();
		
		List<String> armonia = new ArrayList<String>();
		armonia.add("Cmaj7");
		
		List<Integer> melodia = new ArrayList<Integer>();
		melodia.add(1);
		melodia.add(2);
		melodia.add(3);
		melodia.add(1);
		melodia.add(2);
		melodia.add(3);
		melodia.add(3);
		melodia.add(1);
		
		List<String> notas = FuncionesMusicales.melodiaNumeradaANotas(melodia, armonia);
		
		for(int i = 0; i < notas.size(); i++){
			Note note = new Note();
			//System.out.println(notas.get(i));
			note.setPitch((Integer) mapSonidos.NvsS.get(notas.get(i)));
			note.setDynamic(JMC.PP);
			note.setDuration(JMC.EIGHTH_NOTE);
			
			Play.midi(note);
		}
		
		//System.out.println(JMC.C3);
		
		
		
		

	} 
	
	

}
