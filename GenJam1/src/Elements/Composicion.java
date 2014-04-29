package Elements;
import java.util.List;

import jm.JMC;
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
		Part mypart = new Part(JMC.PIANO, 0);
		
		Phrase ph1 = new Phrase(2.0);
		Note n1 = new Note(JMC.G4, JMC.HALF_NOTE);
		ph1.addNote(n1);
		mypart.addPhrase(ph1);
		
		Phrase ph2 = new Phrase(1.0);
		Note n2 = new Note(JMC.E4, JMC.DOTTED_HALF_NOTE);
		ph2.addNote(n2);
		 mypart.addPhrase(ph2);
		
		Phrase ph3 = new Phrase(0.0);
		Note n3 = new Note(JMC.C4, JMC.WHOLE_NOTE);
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
