package GenJam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jm.constants.Durations;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;
import jm.JMC;

import Elements.Bajo;
import Elements.BassNote;
import Elements.ChordvsScale;
import Elements.DatosArchivo;
import Elements.MapBassNotes;
import Elements.MapChordvsScale;
import Elements.MapNotevsSound;
import Elements.MapVoicingNotes;
import Elements.Measures;
import Elements.Phrases;
import Elements.Teclado;
import Elements.VoicingNote;

public class FuncionesMusicales {
	
	private static Score s;
	private static Score partitura;
	private static Part improvisacionOriginal = new Part("Improvisacion", JMC.PIANO ,3);
	private static Part melodia = new Part("Melodia", JMC.RHODES ,0);
	private static Part walkin = new Part("Walkin", JMC.BASS ,1);
	private static Part voicings = new Part("Voicings", JMC.RHODES, 2);
	private static Part ridePart = new Part("Drums",0,9);
	private static Part snarePart = new Part("Drums 2", 0, 9);
	private static Random randoms = new Random();
	private static int cortes = 0;
	private static int tonalidad = 0;
	private static int comenzando = 0;
	private static int dondecortoVoicings = 10;
	private static int dondecortoBajo = 10;

	public static List<Phrases> poblacionVacia(){
		
		List<Phrases> dev = new ArrayList<Phrases>();
		
		for(int i = 0; i < 48; i++){
			
			Phrases frase = new Phrases();
			frase.genre = "Jazz";
			frase.id = 0;
			frase.measure_list = new ArrayList<Measures>();
			
			for(int j = 0; j < 4; j++){
				Measures measure = new Measures();
				
				measure.id = 0;
				measure.notas = new ArrayList<Integer>();
				
				for(int z = 0; z< 16; z++){
					measure.notas.add(0);
				}
				frase.measure_list.add(measure);
				
			}
			
			dev.add(frase);
		}
		
		return dev;
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
	
	
	public static void crearComposicion(List<Phrases> pobFrases, List<String> acordes, DatosArchivo datosArchivo){
		
		s = new Score(datosArchivo.nombreArchivo,datosArchivo.tempo);
		partitura = new Score("partitura.midi",datosArchivo.tempo);
		melodia = new Part("Melodia", JMC.RHODES ,0);
		walkin = new Part("Walkin", JMC.BASS ,1);
		voicings = new Part("Voicings", JMC.RHODES, 2);
		ridePart = new Part("Drums",0,9);
		snarePart = new Part("Drums 2", 0, 9);
		
		
		if(datosArchivo.cortes2.equalsIgnoreCase("true")){
			dondecortoVoicings = 10;
			dondecortoBajo = 10;
		}
		if(datosArchivo.cortes4.equalsIgnoreCase("true")){
			dondecortoVoicings = 8;
			dondecortoBajo = 8;
		}
		if((datosArchivo.cortes4.equalsIgnoreCase("false")) && (datosArchivo.cortes2.equalsIgnoreCase("false"))){
			dondecortoVoicings = 35655;
			dondecortoBajo = 35655;
		}
		
		if(datosArchivo.voicings.equalsIgnoreCase("false")){
			dondecortoVoicings = 0;
		}
		
		cortes = 0;
		makeVoicings(acordes);
		
		makeMelody(pobFrases,acordes);
		cortes = 0;
		makeBass(acordes);
		makeDrums(acordes);
		
		generaPartitura();
		
		Write.midi(s,datosArchivo.nombreArchivo);
		
		if(datosArchivo.generacionOriginal == 1){
			Write.midi(partitura,"partitura.midi");
		}
		
	}
		
	public static void makeDrums(List<String> acordes){
				
		for(int fff = 0; fff < acordes.size(); fff++){
			ridePart.addPhrase(swingTime());
			snarePart.addPhrase(swingAccents());
		}
		
		s.add(ridePart);
		s.add(snarePart);
		
		
	}
	
	public static boolean inicioNotaEnTeclado(List<String> lista, String nota){
		
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
	
	public static void makeVoicings(List<String> acordes){
		Phrase phr3 = new Phrase();
		Teclado teclado = new Teclado();
		MapNotevsSound sonidos = new MapNotevsSound();
		MapVoicingNotes notasVoicings = new MapVoicingNotes();
		
		Iterator<String> iter = acordes.iterator();
		
		
		boolean inicio = true;
		String root = "";
		int[] rootArray = null;
		
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
			
			for (int i = 0; i < teclado.notas.size(); i++){
				
				//System.out.println("Nota: " + nota);
				
				if(inicioNotaEnTeclado(teclado.notas.get(i), nota)){
//					System.out.println(keyboard.notas.get(i).get(0));
					
					Iterator<VoicingNote> itNotasB = notasVoicings.lista_voicings.iterator();
					VoicingNote itemNotaB = null;
					
					while(itNotasB.hasNext()){
						itemNotaB = itNotasB.next();
						
						if(itemNotaB.alteracion.equalsIgnoreCase(acorde)) break;
						
					}
					
					//solo entra una vez para cargar la tonalidad en root
					if(inicio && (acorde.equalsIgnoreCase("maj7"))){
						inicio = false;
						
						int tonalidad = sonidos.NvsS.get(teclado.notas.get(i).get(0));
						
						while(tonalidad > 47){
							tonalidad = tonalidad - 12;
						}
						
						int bajisimo = tonalidad;
						int n1 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz1).get(0));
						int n2 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz2).get(0));
						int n3 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz3).get(0));
						int n4 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz4).get(0));
						
						int[] aux = {bajisimo,n1,n2,n3,n4};
						rootArray = aux;
					}
					
					
					int n1 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz1).get(0));
					int n2 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz2).get(0));
					int n3 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz3).get(0));
					int n4 = sonidos.NvsS.get(teclado.notas.get(i + itemNotaB.voz4).get(0)); 
					
		            int[] pitchArray = {n1,n2,n3,n4};
		            int[] restArray = {Pitches.REST};
		            
		            int ritmo = randoms.nextInt(5);
		            
		            if(cortes < dondecortoVoicings){
		            
		            	
		            	if((ritmo == 2) || (ritmo == 3) || (comenzando < 4)){
			            	phr3.addChord(pitchArray, Durations.WHOLE_NOTE);
			            }
		            	else if (ritmo == 0){
			            	phr3.addChord(restArray, Durations.QUARTER_NOTE_TRIPLET);
			            	phr3.addChord(pitchArray, Durations.EIGHTH_NOTE_TRIPLET);
				    		phr3.addChord(restArray, Durations.QUARTER_NOTE);
				    		phr3.addChord(restArray, Durations.QUARTER_NOTE_TRIPLET);
			            	phr3.addChord(pitchArray, Durations.EIGHTH_NOTE_TRIPLET);
				    		phr3.addChord(restArray, Durations.QUARTER_NOTE);
			            }
		            	else if((ritmo == 1)|| (ritmo == 4)){
			            	phr3.addChord(pitchArray, Durations.HALF_NOTE);
			            	phr3.addChord(restArray, Durations.QUARTER_NOTE_TRIPLET);
				    		phr3.addChord(pitchArray, Durations.EIGHTH_NOTE_TRIPLET);
				    		phr3.addChord(restArray, Durations.QUARTER_NOTE);
			            }
		            	comenzando++;
			            

		            }
		            else{
						phr3.addChord(restArray,Durations.WHOLE_NOTE);
						if(cortes == 11) cortes = -1;
					}
					cortes++;
		    		
					break;
				}//fin if
				
			}//fin for
			
			
		}
		
		
		phr3.addChord(rootArray, Durations.WHOLE_NOTE);
        
		voicings.add(phr3);
		s.add(voicings);
		
	}
	
	public static void makeBass(List<String> acordes){

		//WALKIN
		Phrase phr2 = new Phrase();
		Bajo bajo = new Bajo();
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
						
			for (int i = 0; i < bajo.notas.size(); i++){
				
				//System.out.println("Nota: " + nota);
				
				if(inicioNotaEnTeclado(bajo.notas.get(i), nota)){
//					System.out.println(keyboard.notas.get(i).get(0));
					
					Iterator<BassNote> itNotasB = notasBass.lista_bajo.iterator();
					BassNote itemNotaB = null;
					
					while(itNotasB.hasNext()){
						itemNotaB = itNotasB.next();
						
						if(itemNotaB.alteracion.equalsIgnoreCase(acorde)) break;
						
					}
					
					//solo entra una vez para cargar la tonalidad en root
					if(inicio){
						inicio = false;
						root = bajo.notas.get(i).get(0);
					}
					
					if(cortes < dondecortoBajo){
					
						n = new Note(sonidos.NvsS.get(bajo.notas.get(i).get(0)), Durations.QUARTER_NOTE);
			            phr2.addNote(n);
			            n = new Note(sonidos.NvsS.get(bajo.notas.get(i + itemNotaB.tercera).get(0)), Durations.QUARTER_NOTE);
			            phr2.addNote(n);
			            n = new Note(sonidos.NvsS.get(bajo.notas.get(i + itemNotaB.cuarta).get(0)), Durations.QUARTER_NOTE);
			            phr2.addNote(n);
			            n = new Note(sonidos.NvsS.get(bajo.notas.get(i + itemNotaB.quinta).get(0)), Durations.QUARTER_NOTE);
			            phr2.addNote(n);
			            
					}
					else{
						n = new Note(JMC.REST,Durations.WHOLE_NOTE);
						phr2.add(n);
						if(cortes == 11) cortes = -1;
					}
					cortes++;
					
		            
		            
					break;
					
					
					
				}//fin if
				
			}//fin for
			
		}
        
        n = new Note(tonalidad,Durations.WHOLE_NOTE);
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
		Phrase phrOri = new Phrase();
		
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
		double duracionOriginal;
		
		int iniciaTriplets = 0;
		int iniciaCorchea = 0;
		int iniciaSemi = 0;
		
		while(recorrer < notasRep.size()){
			
			Note n = null;
			
			if(notasRep.get(recorrer) == -2){
				
			}
			
			Integer aux = recorrer + 1;
			//auxDur = duration;
			
			auxDur = 0;
			duracionOriginal = 0;
			
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
				
				if(iniciaTriplets == 1){
					duracionOriginal = Durations.SIXTEENTH_NOTE;
				}
				else if(iniciaTriplets == 2){
					duracionOriginal = Durations.EIGHTH_NOTE;
				}
				else if(iniciaTriplets == 3){
					duracionOriginal = Durations.SIXTEENTH_NOTE;
				}
				
				iniciaTriplets++;				
				
				if(iniciaTriplets == 4){
					//termino el triplet
					iniciaTriplets = 0;
				}
				
				//termina con todos los holds para una nota del triplet
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaTriplets != 0)){
					
					auxDur += Durations.EIGHTH_NOTE_TRIPLET;
					
					if(iniciaTriplets == 1){
						duracionOriginal += Durations.SIXTEENTH_NOTE;
					}
					else if(iniciaTriplets == 2){
						duracionOriginal += Durations.EIGHTH_NOTE;
					}
					else if(iniciaTriplets == 3){
						duracionOriginal += Durations.SIXTEENTH_NOTE;
					}
					
					iniciaTriplets++;
					
					if (iniciaTriplets == 4){
						iniciaTriplets = 0;
					}
					
					aux++;
				}								
			}
			else if(iniciaSemi > 0){
				auxDur = Durations.SIXTEENTH_NOTE;
				duracionOriginal = Durations.SIXTEENTH_NOTE;
				iniciaSemi++;
				if (iniciaSemi == 5) iniciaSemi = 0;
				
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaSemi != 0)){
					auxDur += Durations.SIXTEENTH_NOTE;
					duracionOriginal += Durations.SIXTEENTH_NOTE;
					iniciaSemi++;
					if (iniciaSemi == 5) iniciaSemi = 0;
					aux++;					
				}
			}
			else if(iniciaCorchea > 0){
				if(iniciaCorchea == 1){
					auxDur = Durations.QUARTER_NOTE_TRIPLET;
					duracionOriginal = Durations.EIGHTH_NOTE; 
				}
				else{
					auxDur = Durations.EIGHTH_NOTE_TRIPLET;
					duracionOriginal = Durations.EIGHTH_NOTE;
				}
				iniciaCorchea++;
				if(iniciaCorchea == 3) iniciaCorchea = 0;
				aux++;
				
				while((aux < notasRep.size()) && (notasRep.get(aux) == -1) && (iniciaCorchea != 0)){
					if(iniciaCorchea == 1){
						auxDur += Durations.QUARTER_NOTE_TRIPLET;
						duracionOriginal += Durations.EIGHTH_NOTE;
					}
					else{
						auxDur += Durations.EIGHTH_NOTE_TRIPLET;
						duracionOriginal += Durations.EIGHTH_NOTE;
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
							duracionOriginal += Durations.SIXTEENTH_NOTE;
							iniciaSemi++;
							if (iniciaSemi == 5) iniciaSemi = 0;
							aux++;		
							
						}
						else if(iniciaCorchea > 0){
							if(iniciaCorchea == 1){
								auxDur += Durations.QUARTER_NOTE_TRIPLET;
								duracionOriginal += Durations.EIGHTH_NOTE;
							}
							else{
								auxDur += Durations.EIGHTH_NOTE_TRIPLET;
								duracionOriginal += Durations.EIGHTH_NOTE;
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
			
			n = new Note(notasRep.get(recorrer),duracionOriginal);
			n.setDuration(duracionOriginal);
			phrOri.add(n);			
			
			
			recorrer = aux;
			
		}
		
		//System.out.println();
		
		//int[] pitchArray = {Pitches.c3,Pitches.g3,Pitches.b3,Pitches.e4}; 
		//phr.addChord(pitchArray, Durations.WHOLE_NOTE);
		
		while(tonalidad < 68){
			tonalidad = tonalidad + 12;
		}
		
		Note fin = new Note(tonalidad,Durations.WHOLE_NOTE);
		phr.add(fin);
		duracionOriginal = Durations.WHOLE_NOTE;
		fin.setDuration(duracionOriginal);
		phrOri.add(fin);
		
		melodia.add(phr);
		improvisacionOriginal.add(phrOri);
		
		partitura.add(improvisacionOriginal);
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
	
	public static void generaSugerencias(){
		
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
