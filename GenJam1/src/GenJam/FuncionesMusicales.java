package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
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
	
	
	public static void crearComposicion(PhrasePopulation pobFrases, List<String> acordes){
		

		//debo mandarle una lista de Phrases
		makeMelody(pobFrases.populationP,acordes);
		makeBass(acordes);
		makeDrums(acordes);
		
		Write.midi(s,"out.midi");
	}
		
	public static void makeDrums(List<String> acordes){
		
		for(int fff = 0; fff < 16; fff++){
			ridePart.addPhrase(swingTime());
			snarePart.addPhrase(swingAccents());
		}
		
		s.add(ridePart);
		s.add(snarePart);
		
		
	}
	
	public static void makeBass(List<String> acordes){

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
		
		/*
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
		*/
		
		/*
		//ORNITHOLOGY
		Note n = null;
		n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.AF3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.EF4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.EF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.C3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.FS3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.B2, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.FS3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.A2, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.FS3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        */
        
        //Borrar
        Note n = null;
		
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.C3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.ES3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.B2, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.GS3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.A2, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.EF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.BF3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.E4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.C4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.D4, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.E3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.B3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        n = new Note(JMC.D3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.F3, JMC.QUARTER_NOTE);	
        phr2.addNote(n);
        n = new Note(JMC.G3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        n = new Note(JMC.A3, JMC.QUARTER_NOTE);
        phr2.addNote(n);
        
        //Fin borrar
        
        n = new Note(JMC.C3,JMC.WHOLE_NOTE);
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
			if((aux < notasRep.size() && (notasRep.get(aux) == -1))){
				
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
			
			System.out.print(aux - 2 + ": ");
			
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
		
		int[] pitchArray = {JMC.c3,JMC.g3,JMC.b3,JMC.e4}; 
		phr.addChord(pitchArray, JMC.WHOLE_NOTE);
		
		melodia.add(phr);
		s.add(melodia);
		
	}
		
	public static Phrase swingTime() {
		// build the ride line
	 	Phrase phr = new Phrase();
	 	int ride = 51;
	 	phr.addNote(new Note(ride, JMC.C));
	 	phr.addNote(new Note(ride, 0.67));
	 	phr.addNote(new Note(ride, 0.33));
	 	phr.addNote(new Note(ride, JMC.C));
	 	phr.addNote(new Note(ride, JMC.C));
		return phr;
	}
		
	public static Phrase swingAccents() {
		// build the bass line from the rootPitch 
	 	Phrase phr = new Phrase();
	 	int snare = 38;
		for (int i=0;i<4;i++) {
	 		phr.addNote(new Note(JMC.REST, 0.67));
	 		phr.addNote(new Note(snare, 0.33, (int)(Math.random()*60)));
	 	}
		return phr;
	}
	
}
