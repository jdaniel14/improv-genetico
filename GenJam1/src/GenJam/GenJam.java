package GenJam;

import java.util.ArrayList;
import java.util.List;

import Elements.MapChordvsScale;
import jm.music.data.Note;
import jm.util.Play;
import jm.JMC;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		//MapChordvsScale map = new MapChordvsScale();
		
		//System.out.println("Nota: " + map.CvsS.get(0).notes.get(5));
		
		//FuncionesArchivos.crearMeasureBD();
		
		List<String> armonia = new ArrayList<String>();
		armonia.add("Cmaj7");
		
		List<Integer> melodia = new ArrayList<Integer>();
		melodia.add(4);
		melodia.add(2);
		melodia.add(6);
		melodia.add(11);
		melodia.add(10);
		melodia.add(8);
		melodia.add(0);
		melodia.add(15);
		
		List<String> notas = FuncionesMusicales.melodiaNumeradaANotas(melodia, armonia);
		
		for(int i = 0; i < notas.size(); i++){
			System.out.println(notas.get(i));
		}
		
		//Note note = new Note();
		//note.setPitch(JMC.GS4);
		//note.setDynamic(JMC.PP);
		//note.setDuration(JMC.EIGHTH_NOTE);
		//Play.midi(note);

	} 
	
	

}
