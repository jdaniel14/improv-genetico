package GenJam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
	
	public static Integer dev_id_measure(){
		Integer dev = 0;
		
		Conexion conexion = new Conexion();
		
		try{
			conexion.abrirConexion();
			
			String sql = 	" select max(M.id) as idM from measures M ";
						
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
				
				//2) Leo el gŽnero
				
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
					
					for(Integer j = 0; j <= 7; j++){
						
						compas += numeroDeLaNota(acorde, notasLeidas[j]);
						
						if (j != 7) compas += " "; //para dejar espacio entre cada numero				
						
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
			
			String sql = " INSERT INTO chordvsscale (chord" +
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
	
	//Funci—n para cargar el archivo ChordvsScale.txt a la Base de Datos
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
	
	
}
