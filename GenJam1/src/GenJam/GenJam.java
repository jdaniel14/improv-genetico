package GenJam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Elements.DatosArchivo;
import Elements.PhrasePopulation;
import Elements.Phrases;
import FuncionesGeneticas.Genetico;

public class GenJam {
	
	public static void main(String[] args) {
		
		String rutaHardcode = "/Applications/XAMPP/htdocs/workspace eclipse/GenJam1/bin/View/euphony-master/datos.txt"; 
		
		System.out.println("Bemvindo ao Euricide");
		List<String> acordes;
		DatosArchivo datosArchivo = new DatosArchivo();
		// ** Lectura de datos desde el PHP **
		
		acordes = FuncionesArchivos.leeAcordes(rutaHardcode);
		for(int i = 0 ; i < acordes.size(); i++)
			System.out.println(acordes.get(i));
		datosArchivo = FuncionesArchivos.leeDatosArchivo(rutaHardcode);
		
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
		
		
		PhrasePopulation pobinicial = new PhrasePopulation();
		
		List<Phrases> frasesGeneradas = Genetico.AG(pobinicial, datosArchivo.ordenamiento);
		List<Phrases> frasesNulas = FuncionesMusicales.poblacionVacia();
		
		//Improvisacion generada
		datosArchivo.generacionOriginal = 1;
		datosArchivo.bajo = "true";
		datosArchivo.nombreArchivo = "musicagenerada.midi";
		FuncionesMusicales.crearComposicion(frasesGeneradas, acordes,datosArchivo);
		
		datosArchivo.generacionOriginal = 0;
		
		//El acompanhamiento
		datosArchivo.cortes2 = "false";
		datosArchivo.cortes4 = "false";
		datosArchivo.voicings = "true";
		datosArchivo.bajo = "true";		
		datosArchivo.nombreArchivo = "acompanhamiento.midi";
		
		FuncionesMusicales.crearComposicion(frasesNulas, acordes,datosArchivo);
		
		//Poblacion Inicial
		datosArchivo.cortes2 = "false";
		datosArchivo.cortes4 = "false";
		datosArchivo.voicings = "false";
		datosArchivo.bajo = "true";
		datosArchivo.nombreArchivo = "pobinicial.midi";
		
		FuncionesMusicales.crearComposicion(pobinicial.populationP, acordes,datosArchivo);
		
		//Solo la improvisacion
		datosArchivo.cortes2 = "false";
		datosArchivo.cortes4 = "false";
		datosArchivo.voicings = "false";
		datosArchivo.bajo = "false";
		datosArchivo.nombreArchivo = "solo.midi";
		
		FuncionesMusicales.crearComposicion(frasesGeneradas, acordes,datosArchivo);
		
		//PlayAlong
		datosArchivo.cortes2 = "false";
		datosArchivo.cortes4 = "false";
		datosArchivo.voicings = "true";
		datosArchivo.bajo = "true";
		datosArchivo.nombreArchivo = "playalong.midi";
		
		List<Phrases> frasesPlayAlong = new ArrayList<Phrases>();
		List<String> acordesPlayAlong = new ArrayList<String>();
		
		for(int i =0; i < acordes.size(); i++){
			if((i+1)%4 == 0){
				frasesPlayAlong.add(frasesNulas.get(i));
			}
			acordesPlayAlong.add(acordes.get(i));
		}
		
		for(int i =0; i < acordes.size(); i++){
			//System.out.println("#: " + i);
			//Genetico.imprimirFraseNumeros(frasesGeneradas.get(i));
			if((i+1)%4 == 0){
				frasesPlayAlong.add(frasesGeneradas.get(i));
			}
			acordesPlayAlong.add(acordes.get(i));
		}
		
		for(int i =0; i < acordes.size(); i++){
			if((i+1)%4 == 0){
				frasesPlayAlong.add(frasesNulas.get(i));
			}
			acordesPlayAlong.add(acordes.get(i));
		}
		
		Collections.shuffle(frasesGeneradas);
		int turno = 0;
		
		for(int i =0; i < acordes.size(); i++){
			if((i+1)%4 == 0){
				if(turno == 0){
					frasesPlayAlong.add(frasesGeneradas.get(i));
					turno = 1;
				}
				else{
					frasesPlayAlong.add(frasesNulas.get(i));
					turno = 0;
				}
			}
			acordesPlayAlong.add(acordes.get(i));
		}
		
		for(int i =0; i < acordes.size(); i++){
			if((i+1)%4 == 0){
				frasesPlayAlong.add(frasesNulas.get(i));
			}
			acordesPlayAlong.add(acordes.get(i));
		}
		
		FuncionesMusicales.crearComposicion(frasesPlayAlong, acordesPlayAlong,datosArchivo);
		
		//PlayAlong piano
		datosArchivo.cortes2 = "false";
		datosArchivo.cortes4 = "false";
		datosArchivo.voicings = "false";
		datosArchivo.bajo = "true";
		datosArchivo.nombreArchivo = "playalongpiano.midi";
				
		FuncionesMusicales.crearComposicion(frasesPlayAlong, acordesPlayAlong,datosArchivo);
		
		//Creacion de las sugerencias
		try{
			FuncionesArchivos.Sugerencia(acordes);
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		//	** Inicializacion de estructuras de la BD **
		//		FuncionesArchivos.initChordvsScaleBD();
//				FuncionesArchivos.initMeasureBD();
		//		FuncionesArchivos.initBassNotes();
		//		FuncionesArchivos.initVoicings();
		
	} 
	
	

}
