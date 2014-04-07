package GenJam;

import java.util.ArrayList;
import java.util.List;

import Elements.MapChordvsScale;
import Elements.MapNotevsSound;
import jm.music.data.Note;
import jm.util.Play;
import jm.JMC;
import jm.music.data.*;
import jm.util.Write;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		MapNotevsSound mapSonidos = new MapNotevsSound();
		
		//MapChordvsScale map = new MapChordvsScale();
		
		FuncionesArchivos.initMeasureBD();
		
		//System.out.println("Nota: " + map.CvsS.get(0).notes.get(5));
		
		//FuncionesArchivos.crearMeasureBD();
		
		/*
		
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
		
		Part mypart = new Part(JMC.PIANO, 0);
		
		Phrase ph1 = new Phrase(2.0);
		Note n1 = new Note(JMC.G4, JMC.HALF_NOTE);
		ph1.addNote(n1);
		mypart.addPhrase(ph1);
		
		Phrase ph2 = new Phrase(1.0);
		Note n2 = new Note(JMC.E4, JMC.DOTTED_HALF_NOTE);
		ph1.addNote(n2);
		mypart.addPhrase(ph2);
		
		Phrase ph3 = new Phrase(0.0);
		Note n3 = new Note(JMC.C4, JMC.WHOLE_NOTE);
		ph1.addNote(n3);
		mypart.addPhrase(ph3);
		
		Write.midi(mypart, "out.midi");
		Play.midi(mypart);
		
		*/

	} 
	
	

}
