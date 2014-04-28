package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import jm.audio.Instrument;
import jm.music.data.CPhrase;
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
		makeMelodyImproved(pobFrases.populationP,acordes);
		
		
		
	}
		
	
	
public static void makeMelodyImproved(List<Phrases> pobFrases, List<String> acordes){
		
		
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
			if(cont == 4) break; //Solo coje 4 frases. *Cantidad de frases*
			
		}
		
		
		//Preparacion de las notas para la reproduccion
		
		System.out.println("Frases sacadas");
		
		//Transformo los holds iniciales en silencios
		
		Integer recorrer = 0;
		
		while(recorrer < notasRep.size()){
			System.out.println("Elimina hasta: " + recorrer);
			if(notasRep.get(recorrer) == -1){
				notasRep.set(recorrer, JMC.REST);
			}
			else{
				break;
			}
			
			recorrer++;
			
		}
		
		//Reproduccion
		
		System.out.println("**Inicia reproduccion");
				
		recorrer = 0;//posicion actual
		boolean primerBeat = true;
		int tiempoLargo = 0;
		
		double duration = 0.75;
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
				
				aux++;
				recorrer = aux;
				
				continue;
			}
			else if (iniciaTriplets == 0){
				if((iniciaCorchea == 0) && (iniciaSemi == 0)){
					if(((notasRep.get(recorrer + 1) == -1) || (notasRep.get(recorrer + 1) == JMC.REST)) && ((notasRep.get(recorrer + 3) == -1) || (notasRep.get(recorrer + 3) == JMC.REST)) && (notasRep.get(recorrer + 2) != -1)){
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
				
				auxDur = JMC.EIGHTH_NOTE_TRIPLET;
				iniciaTriplets++;
				
				if(iniciaTriplets == 4){
					//termino el triplet
					iniciaTriplets = 0;
				}
				
				aux++;
				
				//termina con todos los holds para una nota del triplet
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaTriplets != 0)){
					
					auxDur += JMC.EIGHTH_NOTE_TRIPLET;
					
					iniciaTriplets++;
					
					if (iniciaTriplets == 4){
						iniciaTriplets = 0;
					}
					
					aux++;
				}								
			}
			else if(iniciaSemi > 0){
				auxDur = JMC.SIXTEENTH_NOTE;
				iniciaSemi++;
				if (iniciaSemi == 5) iniciaSemi = 0;
				
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaSemi != 0)){
					auxDur += JMC.SIXTEENTH_NOTE;
					iniciaSemi++;
					if (iniciaSemi == 5) iniciaSemi = 0;
					aux++;					
				}
			}
			else if(iniciaCorchea > 0){
				if(iniciaCorchea == 1){
					auxDur = JMC.QUARTER_NOTE_TRIPLET;
				}
				else{
					auxDur = JMC.EIGHTH_NOTE_TRIPLET;
				}
				iniciaCorchea++;
				if(iniciaCorchea == 3) iniciaCorchea = 0;
				aux++;
				
				
				
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaCorchea != 0)){
					if(iniciaCorchea == 1){
						auxDur += JMC.QUARTER_NOTE_TRIPLET;
					}
					else{
						auxDur += JMC.EIGHTH_NOTE_TRIPLET;
					}
					iniciaCorchea++;
					if(iniciaCorchea == 3) iniciaCorchea = 0;
					aux++;
					aux++;
				}
			}
			
			//Si es que no es un nuevo evento
			if(notasRep.get(aux) == -1){
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && iniciaTriplets == 0){
					
					if((iniciaSemi == 0) && (iniciaCorchea == 0) && notasRep.get(aux) != -2){
						if(((notasRep.get(aux + 1) == -1) || (notasRep.get(aux + 1) == JMC.REST)) && ((notasRep.get(aux + 3) == -1) || (notasRep.get(aux + 3) == JMC.REST)) && (notasRep.get(aux + 2) != -1)){
							//es corchea
							iniciaCorchea = 1;
						}
						else{//es semicorchea
							iniciaSemi = 1;
						}
					}
					else{
						if(iniciaSemi > 0){
							auxDur += JMC.SIXTEENTH_NOTE;
							iniciaSemi++;
							if (iniciaSemi == 5) iniciaSemi = 0;
							aux++;		
							aux++;
						}
						else if(iniciaCorchea > 0){
							if(iniciaCorchea == 1){
								auxDur += JMC.QUARTER_NOTE_TRIPLET;
							}
							else{
								auxDur += JMC.EIGHTH_NOTE_TRIPLET;
							}
							iniciaCorchea++;
							if(iniciaCorchea == 3) iniciaCorchea = 0;
							aux++;
							aux++;
						}
					}
				}	
			}
			
			if(Note.getNote(notasRep.get(recorrer)) != "N/A"){
				System.out.print(Note.getNote(notasRep.get(recorrer)) + " ");
			}
			else
				System.out.print(notasRep.get(recorrer) + " ");
			
			System.out.println(auxDur);
			
			n = new Note(notasRep.get(recorrer),auxDur);
						
			phr.add(n);
			
			recorrer = aux;
			
			
		}
		
		System.out.println();
		
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
		
		int[] pitchArray = {JMC.c3,JMC.g3,JMC.c4,JMC.e4,JMC.c5}; 
		phr.addChord(pitchArray, JMC.WHOLE_NOTE);
		
		melodia.add(phr);
		s.add(melodia);
		
		
		
		//WALKIN
		Phrase phr2 = new Phrase();
				
		// create a phrase of 32 quavers following 
        // a random walk within C minor.
		/*
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
        */
        //Para Dm7 - G7 - A7b9 - Cmaj7
		
        for(short i=0;i<2;i++) {
        	
        	// next note within plus or minus a 5th.\
        	
        	Note n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.A3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.A3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            
            n = new Note(JMC.G2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.B2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            

            n = new Note(JMC.G2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.B2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);


            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            

            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
            

            n = new Note(JMC.A2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.CF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
            

            n = new Note(JMC.A2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.CF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
                 
        }
		
        
        Note n = new Note(JMC.C3,JMC.WHOLE_NOTE);
        phr2.add(n); 
        
		walkin.add(phr2);
			
		s.add(walkin);
		
		
		
		Write.midi(s,"out.midi");
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
				List<Integer> temp = notasDelCompas(reproducir.measure_list.get(j),acordes.get(j));
				
				Iterator<Integer> iter = temp.iterator();
				
				while(iter.hasNext()){
					Integer tempNota = iter.next();
				
					notasRep.add(tempNota);
				}
				
			}
			
			cont++;
			if(cont == 4) break; //Solo coje 4 frases. *Cantidad de frases*
			
		}
		
		
		//Preparacion de las notas para la reproduccion
		
		System.out.println("Frases sacadas");
		
		//Transformo los holds iniciales en silencios
		
		Integer recorrer = 0;
		
		while(recorrer < notasRep.size()){
			System.out.println("Elimina hasta: " + recorrer);
			if(notasRep.get(recorrer) == -1){
				notasRep.set(recorrer, JMC.REST);
			}
			else{
				break;
			}
			
			recorrer++;
			
		}
		
		//Reproduccion
		
		System.out.println("**Inicia reproduccion");
				
		recorrer = 0;
		double duration = 0.75;
		double auxDur = 0.75;
		
		
		while(recorrer < notasRep.size()){
			
			Note n = null;
			
			Integer aux = recorrer + 1;
			auxDur = duration;
				
			while((aux < notasRep.size()) && (notasRep.get(aux) == -1)){
					
				duration = 1 - duration;
					
				auxDur = auxDur + duration;
										
				aux++;
			}
			
			if(Note.getNote(notasRep.get(recorrer)) != "N/A"){
				System.out.print(Note.getNote(notasRep.get(recorrer)) + " ");
			}
			else
				System.out.print(notasRep.get(recorrer) + " ");
			
			System.out.println(JMC.EIGHTH_NOTE_TRIPLET + JMC.QUARTER_NOTE_TRIPLET);
			
			if(auxDur == 0.25){
				n = new Note(notasRep.get(recorrer),JMC.EIGHTH_NOTE_TRIPLET);
				//System.out.println("*1");
			}
			if(auxDur == 0.75){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE_TRIPLET);
				//System.out.println("*2");
			}
			if(auxDur == 1){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE);
				//System.out.println("3");
			}
			if(auxDur == 1.25){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.EIGHTH_NOTE_TRIPLET);
				///System.out.println("4");
			}
			if(auxDur == 1.75){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE_TRIPLET);
				//System.out.println("5");
			}
			if(auxDur == 2){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE);
				//System.out.println("6");
			}
			if(auxDur == 2.25){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.EIGHTH_NOTE_TRIPLET);
				//System.out.println("7");
			}
			if(auxDur == 2.75){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE_TRIPLET);
//				System.out.println("8");
			}
			if(auxDur == 3){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE);
//				System.out.println("9");
			}
			if(auxDur == 3.25){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.EIGHTH_NOTE_TRIPLET);
			}
			if(auxDur == 3.75){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE_TRIPLET);
			}
			if(auxDur == 4){
				n = new Note(notasRep.get(recorrer),JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE + JMC.QUARTER_NOTE);
			}
			
			phr.add(n);
			
			duration = 1 - duration;
				
			recorrer = aux;
			
			
		}
		
		System.out.println();
		
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
		
		int[] pitchArray = {JMC.c3,JMC.g3,JMC.c4,JMC.e4,JMC.c5}; 
		phr.addChord(pitchArray, JMC.WHOLE_NOTE);
		
		melodia.add(phr);
		s.add(melodia);
		
		
		
		//WALKIN
		Phrase phr2 = new Phrase();
				
		// create a phrase of 32 quavers following 
        // a random walk within C minor.
		/*
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
        */
        //Para Dm7 - G7 - A7b9 - Cmaj7
		
        for(short i=0;i<2;i++) {
        	
        	// next note within plus or minus a 5th.\
        	
        	Note n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.A3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.A3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            
            n = new Note(JMC.G2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.B2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            

            n = new Note(JMC.G2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.B2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);


            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            

            n = new Note(JMC.C3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.F3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.G3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
            

            n = new Note(JMC.A2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.CF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
            

            n = new Note(JMC.A2, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.CF3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.D3, JMC.QUARTER_NOTE);
            phr2.addNote(n);
            n = new Note(JMC.E3, JMC.QUARTER_NOTE);
            phr2.addNote(n);  
                 
        }
		
        
        Note n = new Note(JMC.C3,JMC.WHOLE_NOTE);
        phr2.add(n); 
        
		walkin.add(phr2);
			
		s.add(walkin);
		
		
		
		Write.midi(s,"out.midi");
	}
	
	public static void recorrerPoblacion(PhrasePopulation pobFrases){
		Iterator<Phrases> iterF = pobFrases.populationP.iterator();
		
		int i = 0;
		
		while(iterF.hasNext()){
			i++;
			Phrases frase = iterF.next();
			
			System.out.println("Frase: " + frase.id);
			System.out.println(frase.genre);
			
			Iterator<Measures> iterM = frase.measure_list.iterator();
			
			while(iterM.hasNext()){
				Measures compas = iterM.next();
				
				System.out.println("Measure: " + compas.id);
				
				Iterator<Integer> iterN = compas.notas.iterator();
				
				while(iterN.hasNext()){
					Integer notas = iterN.next();
					
					System.out.print(notas + " ");
				}
				
				System.out.println();
				if ( i == 4) break;
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
