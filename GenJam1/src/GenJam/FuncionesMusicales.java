package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jm.music.data.Part;
import jm.music.data.Score;

import Elements.ChordvsScale;
import Elements.MapChordvsScale;
import Elements.Measures;
import Elements.Phrases;
import Elements.PhrasePopulation;

public class FuncionesMusicales {
	
	public static String notaDelNumero(Integer nota, String acorde){
		MapChordvsScale escalas = new MapChordvsScale();
		
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
			return item.notes.get(nota);			
		}		
		
		return "?";
	}
	
	public static void crearComposicion(PhrasePopulation pobFrases){
		
		Score s = new Score("GenJam");
		
		Part melodia = new Part("Melodia", 0);
		
		
	}
	
	public static void recorrerPoblacion(PhrasePopulation pobFrases){
		Iterator<Phrases> iterF = pobFrases.populationP.iterator();
		
		while(iterF.hasNext()){
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
			
			
		}
		
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
