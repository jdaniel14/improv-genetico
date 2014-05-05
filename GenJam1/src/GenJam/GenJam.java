package GenJam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import Elements.BassNote;
import Elements.MapBassNotes;
import Elements.PhrasePopulation;
import Elements.Teclado;
import FuncionesGeneticas.Crossover;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		//MapNotevsSound mapSonidos = new MapNotevsSound();
		
		//MapChordvsScale map = new MapChordvsScale();
		
		List<String> acordes;
		Integer tempo;
		
		acordes = FuncionesArchivos.leeAcordes(args[4]);
		tempo = FuncionesArchivos.leeTempo(args[4]);
		/*
		List<String> acordesOrnithology = new ArrayList<String>();

		acordesOrnithology.add("Gmaj7");
		acordesOrnithology.add("Gmaj7");
		acordesOrnithology.add("Gm7");
		acordesOrnithology.add("Gm7");
		acordesOrnithology.add("Fmaj7");
		acordesOrnithology.add("Fmaj7");
		acordesOrnithology.add("Fm7");
		acordesOrnithology.add("Bb7");
		acordesOrnithology.add("Eb7");
		acordesOrnithology.add("D7");
		acordesOrnithology.add("Gm7");
		acordesOrnithology.add("Cm7b5");
		acordesOrnithology.add("Bm7");
		acordesOrnithology.add("E7");
		acordesOrnithology.add("Am7");
		acordesOrnithology.add("D7");
		
		
		
		List<String> acordesAlice = new ArrayList<String>();

		acordesAlice.add("Dm7");
		acordesAlice.add("Gmaj7");
		acordesAlice.add("Cmaj7");
		acordesAlice.add("Fmaj7");
		acordesAlice.add("Bm7b5");
		acordesAlice.add("E7");
		acordesAlice.add("Am7");
		acordesAlice.add("Eb7");
		acordesAlice.add("Dm7");
		acordesAlice.add("G7");
		acordesAlice.add("Em7");
		acordesAlice.add("Am7");
		acordesAlice.add("Dm7");
		acordesAlice.add("G7");
		acordesAlice.add("Em7");
		acordesAlice.add("Dm7");
		*/
		
		PhrasePopulation poblacionFrases = new PhrasePopulation();
/*		

		Crossover cross = new Crossover();
		cross.genera_frases(poblacionFrases.populationP);
		
		Collections.reverse(cross.resultado_final.populationP);
		
		FuncionesMusicales.crearComposicion(cross.resultado_final, acordes,tempo);
	*/
		
		FuncionesMusicales.crearComposicion(poblacionFrases, acordes,tempo);
		
//		FuncionesArchivos.initChordvsScaleBD();
		
//		FuncionesArchivos.initMeasureBD();
		
//		FuncionesArchivos.initBassNotes();		
		

	} 
	
	

}
