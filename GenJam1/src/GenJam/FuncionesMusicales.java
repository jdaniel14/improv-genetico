package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jm.constants.Durations;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;
import jm.JMC;

import Elements.BassNote;
import Elements.ChordvsScale;
import Elements.MapBassNotes;
import Elements.MapChordvsScale;
import Elements.MapNotevsSound;
import Elements.Measures;
import Elements.Phrases;
import Elements.PhrasePopulation;
import Elements.Teclado;

public class FuncionesMusicales {
	
	private static Score s;
	private static Part melodia = new Part("Melodia", JMC.PIANO ,0);
	private static Part walkin = new Part("Walkin", JMC.BASS ,1);
	private static Part ridePart = new Part("Drums",0,9);
	private static Part snarePart = new Part("Drums 2", 0, 9);

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
	
	
	public static void crearComposicion(List<Phrases> pobFrases, List<String> acordes, Integer tempo){
		
		s = new Score("Euricide",tempo);
		//debo mandarle una lista de Phrases
		//System.out.println("SIZE: " + acordes.size());
		makeMelody(pobFrases,acordes);
		makeBass(acordes);
		makeDrums(acordes);
		generaPartitura();
		
		Write.midi(s,"out.midi");
	}
		
	public static void makeDrums(List<String> acordes){
		
		for(int fff = 0; fff < acordes.size(); fff++){
			ridePart.addPhrase(swingTime());
			snarePart.addPhrase(swingAccents());
		}
		
		s.add(ridePart);
		s.add(snarePart);
		
		
	}
	
	public static void makeVoicings(List<String> acordes){
		
	}
	
	public static boolean inicioBass(List<String> lista, String nota){
		
		Iterator<String> iterTeclado = lista.iterator();
		
		while(iterTeclado.hasNext()){
			String item = iterTeclado.next();
			
			if(item.substring(0, 2).contains("#") || item.substring(0, 2).contains("b")){
				item = item.substring(0, 2);
				
			}
			else{
				item = item.substring(0,1);
			}
			
			if(item.equalsIgnoreCase(nota)) return true;
			
		}
		
		return false;
	}
	
	public static void makeBass(List<String> acordes){

		//WALKIN
		Phrase phr2 = new Phrase();
		Teclado keyboard = new Teclado();
		MapNotevsSound sonidos = new MapNotevsSound();
		MapBassNotes notasBass = new MapBassNotes();
		
		//System.out.println("bass");
		
		Iterator<String> iter = acordes.iterator();
		
		Note n = null;
		boolean inicio = true;
		String root = "";
		
		while(iter.hasNext()){
			String item = iter.next();
			String nota = "";
			String acorde = "";
			
			if(item.substring(0, 2).contains("#") || item.substring(0, 2).contains("b")){
				nota = item.substring(0, 2);
				acorde = item.substring(2,item.length());
				
			}
			else{
				nota = item.substring(0,1);
				acorde = item.substring(1,item.length());
			}
						
			for (int i = 0; i < keyboard.notas.size(); i++){
				
				if(inicioBass(keyboard.notas.get(i), nota)){
//					System.out.println(keyboard.notas.get(i).get(0));
					
					Iterator<BassNote> itNotasB = notasBass.lista_bajo.iterator();
					BassNote itemNotaB = null;
					
					while(itNotasB.hasNext()){
						itemNotaB = itNotasB.next();
						
						if(itemNotaB.alteracion.equalsIgnoreCase(acorde)) break;
						
					}
					
					if(inicio){
						inicio = false;
						root = keyboard.notas.get(i).get(0);
					}
					
					
					n = new Note(sonidos.NvsS.get(keyboard.notas.get(i).get(0)), Durations.QUARTER_NOTE);
		            phr2.addNote(n);
		            n = new Note(sonidos.NvsS.get(keyboard.notas.get(i + itemNotaB.tercera).get(0)), Durations.QUARTER_NOTE);
		            phr2.addNote(n);
		            n = new Note(sonidos.NvsS.get(keyboard.notas.get(i + itemNotaB.cuarta).get(0)), Durations.QUARTER_NOTE);
		            phr2.addNote(n);
		            n = new Note(sonidos.NvsS.get(keyboard.notas.get(i + itemNotaB.quinta).get(0)), Durations.QUARTER_NOTE);
		            phr2.addNote(n);
					
					break;
				}
			}
		}
        
        n = new Note(sonidos.NvsS.get(root),Durations.WHOLE_NOTE);
        phr2.add(n); 
        
		walkin.add(phr2);
		s.add(walkin);
	}
	
	public static void makeMelody(List<Phrases> pobFrases, List<String> acordes){
		
		
		Integer cant_frases = new Double(acordes.size() / 4).intValue(); 
	
		//Transformacion de la lista de frases a frecuencias
		Iterator<Phrases> itFrases = pobFrases.iterator();
		
		Integer cont = 0;
		
		Phrase phr = new Phrase();
		
		List<Integer> notasRep = new ArrayList<Integer>();
		
		while(itFrases.hasNext()){
			
			Phrases reproducir = itFrases.next();
			
			for(int j = 0; j< 4; j++){
				List<Integer> temp = notasDelCompas(reproducir.measure_list.get(j),acordes.get(j));
				
				Iterator<Integer> iter = temp.iterator();
				
				while(iter.hasNext()){
					Integer tempNota = iter.next();
				
					notasRep.add(tempNota);
				}
				
			}
			
			cont++;
			if(cont == cant_frases ) break; // *Cantidad de frases*
			
		}
		
		
		//Preparacion de las notas para la reproduccion
		
		//System.out.println("Frases sacadas");
		
		//Transformo los holds iniciales en silencios
				
		Integer recorrer = 0;
		
		while(recorrer < notasRep.size()){
			//System.out.println("Elimina hasta: " + recorrer);
			if(notasRep.get(recorrer) == -1){
				notasRep.set(recorrer, Pitches.REST);
			}
			else{
				break;
			}
			
			recorrer++;
			
		}
		
		//Reproduccion
		
		//System.out.println("**Inicia reproduccion");
				
		recorrer = 0;//posicion actual
		
		double auxDur = 0.75;
		
		int iniciaTriplets = 0;
		int iniciaCorchea = 0;
		int iniciaSemi = 0;
		
		while(recorrer < notasRep.size()){
			
			Note n = null;
			
			Integer aux = recorrer + 1;
			//auxDur = duration;
			
			auxDur = 0;
			
			//Determino el tipo de destructura que voy a imprimir
			if(notasRep.get(recorrer) == -2){
				iniciaTriplets = 1;
				
				recorrer = aux;
				
				continue;
			}
			else if (iniciaTriplets == 0){
				if((iniciaCorchea == 0) && (iniciaSemi == 0)){
					if(((notasRep.get(recorrer + 1) == -1) || (notasRep.get(recorrer + 1) == Pitches.REST)) && ((notasRep.get(recorrer + 3) == -1) || (notasRep.get(recorrer + 3) == Pitches.REST)) && (notasRep.get(recorrer + 2) != -1)){
						//es corchea
						iniciaCorchea = 1;
					}
					else{//es semicorchea
						iniciaSemi = 1;
					}
				}
			}
			
			//Verifico cada caso
			if(iniciaTriplets > 0){
				
				auxDur = Durations.EIGHTH_NOTE_TRIPLET;
				iniciaTriplets++;
				
				if(iniciaTriplets == 4){
					//termino el triplet
					iniciaTriplets = 0;
				}
				
				//termina con todos los holds para una nota del triplet
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaTriplets != 0)){
					
					auxDur += Durations.EIGHTH_NOTE_TRIPLET;
					
					iniciaTriplets++;
					
					if (iniciaTriplets == 4){
						iniciaTriplets = 0;
					}
					
					aux++;
				}								
			}
			else if(iniciaSemi > 0){
				auxDur = Durations.SIXTEENTH_NOTE;
				iniciaSemi++;
				if (iniciaSemi == 5) iniciaSemi = 0;
				
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaSemi != 0)){
					auxDur += Durations.SIXTEENTH_NOTE;
					iniciaSemi++;
					if (iniciaSemi == 5) iniciaSemi = 0;
					aux++;					
				}
			}
			else if(iniciaCorchea > 0){
				if(iniciaCorchea == 1){
					auxDur = Durations.QUARTER_NOTE_TRIPLET;
				}
				else{
					auxDur = Durations.EIGHTH_NOTE_TRIPLET;
				}
				iniciaCorchea++;
				if(iniciaCorchea == 3) iniciaCorchea = 0;
				aux++;
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaCorchea != 0)){
					if(iniciaCorchea == 1){
						auxDur += Durations.QUARTER_NOTE_TRIPLET;
					}
					else{
						auxDur += Durations.EIGHTH_NOTE_TRIPLET;
					}
					iniciaCorchea++;
					if(iniciaCorchea == 3) iniciaCorchea = 0;
					aux++;
					aux++;
				}
			}
			
			//Si es que no es un nuevo evento
			if((aux < notasRep.size() && (notasRep.get(aux) == -1))){
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && iniciaTriplets == 0){
					
					if((iniciaSemi == 0) && (iniciaCorchea == 0) && notasRep.get(aux) != -2){
						if(((notasRep.get(aux + 1) == -1) || (notasRep.get(aux + 1) == Pitches.REST)) && ((notasRep.get(aux + 3) == -1) || (notasRep.get(aux + 3) == Pitches.REST)) && (notasRep.get(aux + 2) != -1)){
							//es corchea
							iniciaCorchea = 1;
						}
						else{//es semicorchea
							iniciaSemi = 1;
						}
					}
					else{
						if(iniciaSemi > 0){
							auxDur += Durations.SIXTEENTH_NOTE;
							iniciaSemi++;
							if (iniciaSemi == 5) iniciaSemi = 0;
							aux++;		
							
						}
						else if(iniciaCorchea > 0){
							if(iniciaCorchea == 1){
								auxDur += Durations.QUARTER_NOTE_TRIPLET;
							}
							else{
								auxDur += Durations.EIGHTH_NOTE_TRIPLET;
							}
							iniciaCorchea++;
							if(iniciaCorchea == 3) iniciaCorchea = 0;
							aux++;
							aux++;
						}
					}
				}	
			}
			
			//System.out.print(aux - 2 + ": ");
			
			/** Impresion de notas
			if(Note.getNote(notasRep.get(recorrer)) != "N/A"){
				//System.out.print(Note.getNote(notasRep.get(recorrer)) + " ");
			}
			else
				//System.out.print(notasRep.get(recorrer) + " ");
			*/
			//System.out.println(auxDur);
			
			n = new Note(notasRep.get(recorrer),auxDur);
						
			phr.add(n);
			
			recorrer = aux;
			
		}
		
		//System.out.println();
		
		int[] pitchArray = {Pitches.c3,Pitches.g3,Pitches.b3,Pitches.e4}; 
		phr.addChord(pitchArray, Durations.WHOLE_NOTE);
		
		melodia.add(phr);
		s.add(melodia);
		
	}
		
	public static Phrase swingTime() {
		// build the ride line
	 	Phrase phr = new Phrase();
	 	int ride = 51;
	 	phr.addNote(new Note(ride, Durations.C));
	 	phr.addNote(new Note(ride, 0.67));
	 	phr.addNote(new Note(ride, 0.33));
	 	phr.addNote(new Note(ride, Durations.C));
	 	phr.addNote(new Note(ride, Durations.C));
		return phr;
	}
		
	public static Phrase swingAccents() {
		// build the bass line from the rootPitch 
	 	Phrase phr = new Phrase();
	 	int snare = 38;
		for (int i=0;i<4;i++) {
	 		phr.addNote(new Note(Pitches.REST, 0.67));
	 		phr.addNote(new Note(snare, 0.33, (int)(Math.random()*60)));
	 	}
		return phr;
	}
	
	public static void generaPartitura(){
		
//        View.notate(s);
//        View.show(s);
//		View.sketch(s);
		
//		View.histogram(s);
//		View.notation(s);
//		new Show(s);
        
	}
	
	
	
}
