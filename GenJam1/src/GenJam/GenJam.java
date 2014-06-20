package GenJam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import jm.music.data.Phrase;

import Elements.BassNote;
import Elements.MapBassNotes;
import Elements.PhrasePopulation;
import Elements.Phrases;
import Elements.Teclado;
import FuncionesGeneticas.Crossover;
import FuncionesGeneticas.Genetico;

public class GenJam {

	
	
	public static void main(String[] args) {
		
		System.out.println("Bemvindo ao Euricide");
		List<String> acordes;
		Integer tempo = 180;
		
		// ** Lectura de datos desde el PHP **
		
		
		acordes = FuncionesArchivos.leeAcordes(args[0]);
		for(int i = 0 ; i < acordes.size(); i++)
			System.out.println(acordes.get(i));
		tempo = FuncionesArchivos.leeTempo(args[0]);
		System.out.println(tempo);
		
		/** Inicializacion de Datos de prueba***/
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
		acordesAlice.add("G7");
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
		acordesAlice.add("Dm7");
		acordesAlice.add("G7");
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
		
		acordes = acordesAlice;
		*/
		
		List<Phrases> frasesGeneradas = Genetico.AG();
		//pancho 
		FuncionesMusicales.crearComposicion(frasesGeneradas, acordes,tempo);
		

		
		
		
		//	** Inicializaci—n de estructuras de la BD **
		//		FuncionesArchivos.initChordvsScaleBD();
		//		FuncionesArchivos.initMeasureBD();
		//		FuncionesArchivos.initBassNotes();		
		

	} 
	
	

}
