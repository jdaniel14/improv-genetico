package GenJam;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import DataBase.Conexion;
import Elements.ChordvsScale;
import Elements.MapChordvsScale;
import Elements.Teclado;

public class FuncionesArchivos {
	public static MapChordvsScale escalas = new MapChordvsScale();
	public static Teclado keyboard = new Teclado();
	
	public static String notaArriba(String nota){
		Integer posicion = 0;
		
		for(posicion = 0; posicion < keyboard.notas.size(); posicion++){
			if(keyboard.notas.get(posicion).equalsIgnoreCase(nota)){
				break;
			}
		}
		
		if(posicion >= keyboard.notas.size() - 1){
			return nota;
		}
		else{
			return keyboard.notas.get(posicion + 1);
		}
		
	}
	
	public static String notaAbajo(String nota){
		Integer posicion = 0;
		
		for(posicion = 0; posicion < keyboard.notas.size(); posicion++){
			if(keyboard.notas.get(posicion).equalsIgnoreCase(nota)){
				break;
			}
		}
		
		if((posicion == keyboard.notas.size()) || (posicion == 0)){
			return nota;
		}
		else{
			return keyboard.notas.get(posicion - 1);
		}
		
	}
	
	
	public static String aproximarNota(String nota, List<String> notes){
		
		String notaArriba = nota;
		String notaAbajo = nota;
		Integer veces = 0;
		
		while(true){
			
			veces++;
			
			if(veces == 50){
				return "?";
			}
			
			for(Integer i = 0; i < notes.size(); i++){
				if(notes.get(i).equalsIgnoreCase(notaArriba)){
					return notaArriba;
				}
				if(notes.get(i).equalsIgnoreCase(notaAbajo)){
					return notaAbajo;
				}
			}
			
			notaArriba = notaArriba(notaArriba);
			notaAbajo = notaAbajo(notaAbajo);
			
		}
		
	}
	
	public static String numeroDeLaNota(String acorde, String nota){
				
		Iterator<ChordvsScale> iter = escalas.CvsS.iterator();
		
		while(iter.hasNext()){
			ChordvsScale item = iter.next();
						
			if (acorde.equalsIgnoreCase(item.chord)){
				
				nota = aproximarNota(nota, item.notes);
				
				for(Integer i = 0; i < item.notes.size();i++){
					
					if(item.notes.get(i).equalsIgnoreCase(nota)){
						return i.toString();
					}					
				}
			}
		}
		
		return "?";
	}
		
	public static void grabarMeasureBD(Integer idMeasure, String measure){
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" INSERT INTO measures (id, notas) values ( " +
							idMeasure + ",'" + measure + "') " ;
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public static void grabarPhraseBD(Integer idPhrase, String phrase, String genre){
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" INSERT INTO phrases (id, measureId, genre) values ( " +
							idPhrase + ",'" + phrase + "','" + genre + "') " ;
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			conexion.conn.commit();
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	
	public static List<Integer> leerIds(){
		List<Integer> dev = new ArrayList<Integer>();
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" SELECT A.idM, B.idP " +
							" FROM " + 
							" (select max(M.id) as idM from measures M) A, " +
							" (select max(P.id) as idP from phrases P) B " ;
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			rs.next();
						
			dev.add(rs.getInt(1));
			dev.add(rs.getInt(2));
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		return dev;
	}
	
	public static void initMeasureBD(){
		Integer idMeasure = 0;//inicializarlos desde BD
		Integer idPhrase = 0;//inicialidar desde BD
		int exit = 0;
		
		List<Integer> aux = leerIds();
		
		idMeasure = aux.get(0);
		idPhrase = aux.get(1);
		
		System.out.println("Last idMeasure: " + idMeasure);
		System.out.println("Last idPhrase: " + idPhrase);
		System.out.println();
		
		System.out.println("**Measure & Phrase DataBase**");
		System.out.println("Ingrese la letra 'x' para salir y concluir la creación");
		System.out.println();
		
		try{
			
			Scanner s = new Scanner(System.in);
			
			System.out.println("Ingrese el género");
			String genre = s.nextLine();
			
			while(exit != 1){
				//Inicio de la lectura de una frase
				
				System.out.println("Ingrese el acorde en el que está la frase");
				
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
					String lecturaCompas;
					String compas = "";
					
					System.out.println("Measure: " + z);
					
					lecturaCompas = s.nextLine();
					String[] notasLeidas = lecturaCompas.split(" ");
										
					for(Integer j = 0; j <= 7; j++){
						
						compas += numeroDeLaNota(acorde, notasLeidas[j]);
						
						if (j != 7) compas += " "; //para dejar espacio entre cada numero
						
						
						
					}
					
					System.out.println(compas);
					
					//grabarMeasureBD(idMeasure, compas);
					//grabar compas, el cual tiene las notas del measure ya en numero
					
					frase += idMeasure; //guarda el id del measure(debo elegirlo)
					if (z != 4) frase += " ";
					
				}
					
				//grabarPhraseBD(idPhrase,frase,genre);
				//graba la frase que esta en "frase"
				
				System.out.println(frase);
				
				System.out.println();
				
			}//fin while(1)
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
}
