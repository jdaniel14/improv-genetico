package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Elements.ChordvsScale;
import Elements.MapChordvsScale;

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
