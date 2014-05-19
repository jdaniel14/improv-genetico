package Elements;
import java.util.List;

import jm.constants.Durations;
import jm.constants.Pitches;
import jm.constants.ProgramChanges;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.util.Play;
import jm.util.Write;

public class Composicion {
	List <String> partitura;
	int tempo;
	int octava;
	String genero;
	
	void generar_midi(){
		Part mypart = new Part(ProgramChanges.PIANO, 0);
		
		Phrase ph1 = new Phrase(2.0);
		Note n1 = new Note(Pitches.G4, Durations.HALF_NOTE);
		ph1.addNote(n1);
		mypart.addPhrase(ph1);
		
		Phrase ph2 = new Phrase(1.0);
		Note n2 = new Note(Pitches.E4, Durations.DOTTED_HALF_NOTE);
		ph2.addNote(n2);
		 mypart.addPhrase(ph2);
		
		Phrase ph3 = new Phrase(0.0);
		Note n3 = new Note(Pitches.C4, Durations.WHOLE_NOTE);
		ph3.addNote(n3);
		mypart.addPhrase(ph3);
		
		mypart.setTempo(140);
		
		Write.midi(mypart, "out.midi");
		
		
	}
	void reproducir_midi(Part partitura){

		Play.midi(partitura);
	}
	
	void dibujar_partitura(){}
}
