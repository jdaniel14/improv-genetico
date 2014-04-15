package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.data.Tempo;
import jm.util.Write;
import jm.JMC;

import Elements.ChordvsScale;
import Elements.MapChordvsScale;
import Elements.MapNotevsSound;
import Elements.Measures;
import Elements.Phrases;
import Elements.PhrasePopulation;

public class FuncionesMusicales {
	
	private static Score s = new Score("GenJam",180);
	private static Part melodia = new Part("Melodia", JMC.PIANO ,0);
	private static Part walkin = new Part("Walkin", JMC.BASS ,1);
	
	public static String notaDelNumero(Integer nota, String acorde){
		MapChordvsScale escalas = new MapChordvsScale();
		
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
			
			if(item.chord.equalsIgnoreCase(acorde)){
				return item.notes.get(nota);		
			}
		}		
		
		return "?";
	}
	
	public static List<Integer> notasDelCompas(Measures fraseRep,String acorde){
		List<Integer> dev = new ArrayList<Integer>();
		//List<String> devNotas = new ArrayList<String>();
		
		MapChordvsScale escalas = new MapChordvsScale();
		
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
			
			if(item.chord.equalsIgnoreCase(acorde)){
				
				MapNotevsSound sonidos = new MapNotevsSound();
				
				Iterator <Integer> iterN = fraseRep.notas.iterator();
				
				while(iterN.hasNext()){
					Integer nota = iterN.next();
					
					dev.add(sonidos.NvsS.get(item.notes.get(nota)));
					//devNotas.add(item.notes.get(nota));
				}
				
				return dev;
			}
		}		
		
		return new ArrayList<Integer>();
	}
	
	public static List<String> notasEscritasDelCompas(Measures fraseRep,String acorde){
		
		List<String> devNotas = new ArrayList<String>();
		
		MapChordvsScale escalas = new MapChordvsScale();
		
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
			
			if(item.chord.equalsIgnoreCase(acorde)){
				
				MapNotevsSound sonidos = new MapNotevsSound();
				
				Iterator <Integer> iterN = fraseRep.notas.iterator();
				
				while(iterN.hasNext()){
					Integer nota = iterN.next();
					
					devNotas.add(item.notes.get(nota));
					//devNotas.add(item.notes.get(nota));
				}
				
				return devNotas;
			}
		}		
		
		return new ArrayList<String>();
	}
	
	public static void crearComposicion(PhrasePopulation pobFrases, List<String> acordes){
		

		//debo mandarle una lista de Phrases
		makeMelody(pobFrases.populationP,acordes);
		
		
		
	}
		
	public static void makeMelody(List<Phrases> pobFrases, List<String> acordes){
		
		
		//Transformacion de la lista de frases a frecuencias
		Iterator<Phrases> itFrases = pobFrases.iterator();
		
		Integer cont = 0;
		
		Phrase phr = new Phrase();
		
		List<Integer> notasRep = new ArrayList<Integer>();
		
		while(itFrases.hasNext()){
			
			Phrases reproducir = itFrases.next();
			
			for(int j = 0; j< 4; j++){
				
				List<Integer> temp = notasDelCompas(reproducir.measureId.get(j),acordes.get(j));
				
				Iterator<Integer> iter = temp.iterator();
				
				while(iter.hasNext()){
					Integer tempNota = iter.next();
					 
					notasRep.add(tempNota);
				}
				
			}
			
			cont++;
			if(cont == 4) break;
			
		}
		
		//Preparacion de las notas para la reproduccion
		
		
		//Transformo los holds iniciales en silencios
		
		Integer recorrer = 0;
		
		while(recorrer < notasRep.size()){
			
			if(notasRep.get(recorrer) == -1){
				notasRep.set(recorrer, JMC.REST);
			}
			else{
				break;
			}
			
			recorrer++;
			
		}
		
		//Reproduccion
		
		recorrer = 0;
		double duration = 0.75;
		double auxDur = 0.75;
		
		while(recorrer < notasRep.size()){
			
			Note n;
			
			if (notasRep.get(recorrer) != -1){
				Integer aux = recorrer + 1;
				auxDur = duration;
				
				while(notasRep.get(aux) == -1){
					
					duration = 1 - duration;
					
					auxDur = auxDur + 1 - duration;
										
					aux++;
				}
				
				if(auxDur == 0.25){
					n = new Note(notasRep.get(recorrer),JMC.EIGHTH_NOTE_TRIPLET);
				}
				if(auxDur == 0.75){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE_TRIPLET);
				}
				if(auxDur == 1){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE);
				}
				if(auxDur == 1.25){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.EIGHTH_NOTE_TRIPLET);
				}
				if(auxDur == 1.75){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE_TRIPLET);
				}
				if(auxDur == 2){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE);
				}
				if(auxDur == 2.25){
					n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.EIGHTH_NOTE_TRIPLET);
				}
				
				recorrer = aux;
				
			}
			else{
				recorrer++;
			}
			
			
			
		}
		
		/*
		Iterator<Integer> it = notasRep.iterator();
		
		double duration = 0.75;
		
		while(it.hasNext()){
			
			Note n;
			
			if(duration > 0.5){
				n = new Note(it.next(),JMC.QUARTER_NOTE_TRIPLET);
			}
			else{
				n = new Note(it.next(),JMC.EIGHTH_NOTE_TRIPLET);
			}
			
			duration = 1 - duration;
			phr.add(n);
		}
		*/
		
		melodia.add(phr);
		s.add(melodia);
		
		
		
		//WALKIN
		Phrase phr2 = new Phrase();
				
		// create a phrase of 32 quavers following 
        // a random walk within C minor.
        for(short i=0;i<8;i++) {
        	// next note within plus or minus a 5th.\
        	Note n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.EF3, JMC.QUARTER_NOTE);	
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.EF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);                 
                 
        }
		
		walkin.add(phr2);
		s.add(walkin);
		
		
		
		Write.midi(s,"out.midi");
	}
	
	public static void recorrerPoblacion(PhrasePopulation pobFrases){
		Iterator<Phrases> iterF = pobFrases.populationP.iterator();
		
		//while(iterF.hasNext()){
			Phrases frase = iterF.next();
			
			System.out.println("Frase: " + frase.id);
			System.out.println(frase.genre);
			
			Iterator<Measures> iterM = frase.measureId.iterator();
			
			while(iterM.hasNext()){
				Measures compas = iterM.next();
				
				System.out.println("Measure: " + compas.id);
				
				Iterator<Integer> iterN = compas.notas.iterator();
				
				while(iterN.hasNext()){
					Integer notas = iterN.next();
					
					System.out.print(notas + " ");
				}
				
				System.out.println();
				
			}
			
			
		//}
		
	}
	
	public static List<String> melodiaNumeradaANotas(List<Integer> notas, List<String> acordes){
		
		List<String> notasATocar = new ArrayList<String>();
		
		Iterator<String> iter = acordes.iterator();
		Iterator<Integer> iterNotas = notas.iterator();
		
		Integer i = 0;
		
		while(iter.hasNext()){
			String acorde = iter.next();
			
			int contador = 0;
			while (contador != 8){
				
				notasATocar.add(notaDelNumero(iterNotas.next(),acorde));
				
				contador++;
				i++;	
				
			}
			
		}
		
		return notasATocar;
	}
}
