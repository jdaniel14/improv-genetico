package GenJam;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Elements.ChordvsScale;
import Elements.MapChordvsScale;

public class FuncionesArchivos {
	public static MapChordvsScale escalas = new MapChordvsScale();

	
	public static String numeroDeLaNota(String acorde, String nota){
				
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
						
			if (acorde.equalsIgnoreCase(item.chord)){
				
				for(Integer i = 0; i < item.notes.size();i++){
					
					if(item.notes.get(i).equals(nota)){
						return i.toString();
					}
					
				}				
				
			}
			
		}
		
		return "?";
	}
	
	public static void initMeasureBD(){
		Integer idMeasure = 0;//inicializarlos desde BD
		Integer idPhrase = 0;//inicialidar desde BD
		int exit = 0;
		
		System.out.println("**Measure DataBase**");
		System.out.println("Ingrese la letra 'x' para salir y concluir la creaci—n");
		
		try{
			
			while(exit != 1){
				//Inicio de la lectura de una frase
				
				Scanner s = new Scanner(System.in);
				
				System.out.println("Ingrese el acorde en el que est‡ la frase");
				
				String acorde = s.nextLine();
				
				if(acorde.contains("x") || acorde.contains("X")){
					exit = 1;
					break;
				}
				
				idPhrase++;//leer la frase
				
				System.out.println("Ingrese la frase con id: " + idPhrase);
				
				String frase = "";
				
				//Lectura de los 4 compases
				for(Integer z = 1; z <=4 ; z++){
					
					idMeasure++;
					String lecturaCompas;
					String compas = "";
					
					System.out.println("Measure: " + z);
					
					lecturaCompas = s.nextLine();
					String[] notasLeidas = lecturaCompas.split(" ");
										
					for(Integer j = 0; j <= 7; j++){
						
						compas += numeroDeLaNota(acorde, notasLeidas[j]);
						
						
						//debemos de cambiar la nota que ingresa por su respectivo numero
						//de acuerdo a los mapeos de acordes vs escalas
						
						if (j != 7) compas += " "; //para dejar espacio entre cada numero
						
						System.out.println(compas);
						
					}
					//grabar compas, el cual tiene las notas del measure ya en numero
					idMeasure++;
					
					frase += idMeasure; //guarda el id del measure(debo elegirlo)
					if (z != 4) frase += " ";
					
				}
					
				//graba la frase que esta en "frase"
				idPhrase++;
				
			}//fin while(1)
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	
	public static void crearMeasureBD(){
		
		Integer idMeasure = 0;
		Integer idPhrase = 0;
		int exit = 0;
		
		System.out.println("**Creaci—n de MeasureBD**");
		System.out.println("Ingrese la letra 'x' para salir y concluir la creaci—n");
		
		try{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			//root elements del XML
			Document measureFile = docBuilder.newDocument();
			Element measureBD = measureFile.createElement("measureBD");
			measureFile.appendChild(measureBD);
			
			Document phraseFile = docBuilder.newDocument();
			Element phraseBD = phraseFile.createElement("phraseBD");
			phraseFile.appendChild(phraseBD);			
			
			while(exit != 1){
				//Inicio de la lectura de una frase
				
				Scanner s = new Scanner(System.in);
				//Falta leer el acorde
				System.out.println("Ingrese el acorde en el que est‡ la frase");
				
				String acorde = s.nextLine();
				
				if(acorde.contains("x") || acorde.contains("X")){
					exit = 1;
					break;
				}
				
				idPhrase++;
				
				System.out.println("Ingrese la frase con id: " + idPhrase);
				
				String frase = "";
				
				//Lectura de los 4 compases
				for(Integer z = 1; z <=4 ; z++){
					
					idMeasure++;
					String compas = "";
					
					System.out.println("Nota por nota del Measure: " + z);
					
					for(Integer j = 1; j <= 8; j++){
						
						compas += numeroDeLaNota(acorde, s.next());
						
						
						//debemos de cambiar la nota que ingresa por su respectivo numero
						//de acuerdo a los mapeos de acordes vs escalas
						
						if (j != 8) compas += " "; //para dejar espacio entre cada numero
						
					}
					
					if(exit == 1) break;
					
					//measure element
					Element measure = measureFile.createElement("Measure");
					measureBD.appendChild(measure);
					measure.setAttribute("id", idMeasure.toString());
					
					//notes element
					Element notes = measureFile.createElement("Notes");
					notes.appendChild(measureFile.createTextNode(compas));
					measure.appendChild(notes);
					
					frase += idMeasure;
					if (z != 4) frase += " ";
					
				}
					
				//phrase element
				Element phrase = phraseFile.createElement("Phrase");
				phraseBD.appendChild(phrase);
				phrase.setAttribute("id", idPhrase.toString());
				
				//measure element
				Element measures = phraseFile.createElement("Measures");
				measures.appendChild(phraseFile.createTextNode(frase));
				phrase.appendChild(measures);
				
			}//fin while(1)
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			
			//Guardo en ambos archivos
			DOMSource sourceM = new DOMSource(measureFile);
			StreamResult resultM = new StreamResult(new File("../GenJam1/src/Files/MeasureDB.xml"));
	 
			DOMSource sourceP = new DOMSource(phraseFile);
			StreamResult resultP = new StreamResult(new File("../GenJam1/src/Files/PhraseDB.xml"));
			
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(sourceM, resultM);
			transformer.transform(sourceP, resultP);
	 
			System.out.println("File saved!");
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
	}
	
}
