package GenJam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DataBase.Conexion;
import Elements.ChordvsScale;
import Elements.MapChordvsScale;
import Elements.Teclado;

public class FuncionesArchivos {
	public static MapChordvsScale escalas = new MapChordvsScale();
	public static Teclado keyboard = new Teclado();
	
	public static boolean contieneNota(List<String> lista, String nota){
		
		Iterator<String> iterTeclado = lista.iterator();
		
		while(iterTeclado.hasNext()){
			String item = iterTeclado.next();
			
			if(item.equalsIgnoreCase(nota)) return true;
			
		}
		
		return false;
	}
	
	public static String notaArriba(String nota){
		Integer posicion = 0;
		
		for(posicion = 0; posicion < keyboard.notas.size(); posicion++){
			if(contieneNota(keyboard.notas.get(posicion),nota)){
				break;
			}
		}
		
		if(posicion >= keyboard.notas.size() - 1){
			return nota;
		}
		else{
			return keyboard.notas.get(posicion + 1).get(0);
		}
		
	}
	
	public static String notaAbajo(String nota){
		Integer posicion = 0;
		
		for(posicion = 0; posicion < keyboard.notas.size(); posicion++){
			if(contieneNota(keyboard.notas.get(posicion),nota)){
				break;
			}
		}
		
		if((posicion == keyboard.notas.size()) || (posicion == 0)){
			return nota;
		}
		else{
			return keyboard.notas.get(posicion - 1).get(0);
		}
		
	}
	
	
	public static String aproximarNota(String nota, List<String> notes){
		
		String notaArriba = nota;
		String notaAbajo = nota;
		Integer veces = 0;
		
		while(true){
			
			veces++;
			
			if(veces == 100){
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
			
			String sql = 	" INSERT INTO Measures (id, notas) values ( " +
					idMeasure + ",'" + measure + "') " ;
				
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			conexion.conn.commit();
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
			
			String sql = 	" INSERT INTO Phrases (id, measureId, genre) values ( " +
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
	
	public static Integer dev_id_measure(){
		Integer dev = 0;
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" select max(M.id) as idM from Measures M ";
						
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			
			rs.next();
						
			dev = rs.getInt(1);
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		return dev;
	}
	
	
	public static void initMeasureBD(){
		
		Integer idMeasure = dev_id_measure();
		
		BufferedReader br = null;
				
		try{
			
			
			br = new BufferedReader(new FileReader("bin/Files/licks.txt"));
			
			while(br.readLine() != null){
								
				//1) Leo el ID del lick
				
				Integer idPhrase = Integer.parseInt(br.readLine());
				
				
				
				//2) Leo el g�nero
				
				String genre = br.readLine();
				
				//Voy a ir almacenando los idMeasures en frase
				String frase = "";
				
				//3) Leo los 4 acordes con sus 4 measures
				for(int z = 0; z < 4; z++){
					
					//Incremento el idMeasure (lo necesito para la tabla phrases)
					idMeasure++;
					
					String acorde = br.readLine();
					String lecturaCompas = br.readLine();
					
					String[] notasLeidas = lecturaCompas.split(" ");
					
					String compas = "";
					
					for(Integer j = 0; j <= 15; j++){
						
						compas += numeroDeLaNota(acorde, notasLeidas[j]);
						
						if (j != 15) compas += " "; //para dejar espacio entre cada numero				
						
					}
					System.out.println(compas);
					grabarMeasureBD(idMeasure, compas);
					
					frase += idMeasure;
					if (z != 4) frase += " ";
				}
				
				grabarPhraseBD(idPhrase,frase,genre);
			}
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}	
	
	public static void insertarChordvsScale(String acorde, String scale, 
			String notasTeoricas, String notes){
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = " INSERT INTO ChordvsScale (chord" +
						",scale,notasTeoricas,notes) VALUES ('" +
						acorde + "','" + scale + "','" + notasTeoricas  + "','" + 
						notes + "') ";
			
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			conexion.conn.commit();
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
	}
	
	//Funci�n para cargar el archivo ChordvsScale.txt a la Base de Datos
	public static void initChordvsScaleBD(){
		
		BufferedReader br = null;
		
		try{
			
			
			br = new BufferedReader(new FileReader("bin/Files/chordvsscale.txt"));
			
			while(br.readLine() != null){
				
				//1)Leo el acorde
				String acorde = br.readLine();
				
				//2)Leo el nombre de la escala
				String scale = br.readLine();
				
				//3)Leo las notas teoricas que deberian incluirse
				String notasTeoricas = br.readLine();
				
				//4)Leo las notas de las escala
				String notes = br.readLine();
				
				insertarChordvsScale(acorde, scale, notasTeoricas, notes);
				
			}
			
			System.out.println("Carga de ChordvsScale.txt exitosa");
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}	
	}
	
	public static void insertarBass(String alteracion, Integer tercera, Integer cuarta, Integer quinta){
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = " INSERT INTO Bass (alteracion" +
						",tercera,cuarta,quinta) VALUES ('" +
						alteracion + "'," + tercera + "," + cuarta + "," + 
						quinta + ") ";
			
			PreparedStatement pst = conexion.conn.prepareStatement(sql);
			
			pst.execute();
			conexion.conn.commit();
			
			conexion.cerrarConexion();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public static void initBassNotes(){
		BufferedReader br = null;
		
		try{
			
			
			br = new BufferedReader(new FileReader("bin/Files/bass_notes.txt"));
			
			while(br.readLine() != null){
				
				//1)Leo la alteracion
				String alteracion = br.readLine();
				
				//2)Leo la tercera
				Integer tercera = Integer.parseInt(br.readLine());
				
				//3)Leo la cuarta
				Integer cuarta = Integer.parseInt(br.readLine());
				
				//4)Leo la quinta
				Integer quinta = Integer.parseInt(br.readLine());
				
				insertarBass(alteracion, tercera, cuarta, quinta);
				
			}
			
			System.out.println("Carga de bass_notes.txt exitosa");
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}	
	}
	
	
	public static List<String> leeAcordes(String nombreArchivo){
		
		BufferedReader br = null;
		
		List<String> devolver = new ArrayList<String>();
		
		try{
			br = new BufferedReader(new FileReader(nombreArchivo));
			String next = br.readLine();
			
			if(next != null){
				next = br.readLine();
			}
			
			while(next != null){
				
				//1)Leo el acorde
				devolver.add(next);
				next = br.readLine();
				
			}
			
			System.out.println("Carga de " + nombreArchivo + " exitosa");
			return devolver;
			
		}
		catch(Exception e){
			System.out.println(e.toString());
			return null;
		}
		finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}	
		
	}
	
	public static Integer leeTempo(String nombreArchivo){
		
		BufferedReader br = null;
		
		Integer devolver;
		
		try{
			br = new BufferedReader(new FileReader(nombreArchivo));
			
			devolver = Integer.parseInt(br.readLine());
			
			System.out.println("Carga de " + nombreArchivo + " exitosa");
			return devolver;
			
		}
		catch(Exception e){
			System.out.println(e.toString());
			return 0;
		}
		finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}	
	}
	
	
}
