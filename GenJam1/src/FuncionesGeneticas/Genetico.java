package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Elements.Measures;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Genetico {	
	
	static final int PROB_MUTACION = 5;
	static final int PROB_CRUCE = 60;
	
	public static Crossover cross = new Crossover();
	
	public static List<Phrases> AG(){
		
		Integer generacion = 0;
		
		System.out.println("****** Inicio del GA ******");
		
		//Inicializo la poblaci—n de frases
		List<Phrases> poblacion = generarPoblacionInicial();		
		/*
		imprimirFraseNumeros(poblacion.get(0));
		imprimirFraseNumeros(poblacion.get(1));
		
		cross.casar_hijos(poblacion.get(0) , poblacion.get(1) );
		
		imprimirFraseNumeros(poblacion.get(0));
		imprimirFraseNumeros(poblacion.get(1));
		*/
		
		do{
			//Selecciono por torneo a los que seran cruzados/mutados
			List<Phrases> seleccion = seleccionTorneos(poblacion);
			
			//Aplico los operadores de mutacion
			cruzarSeleccion(seleccion);		
			
			//Mi poblacion ahora es lo seleccionado
			poblacion = seleccion;
			
			//Aumento el nï¿½mero de la generacion en la que estoy
			generacion++;
			
			//Repito el proceso por un numero de iteraciones
		} while(generacion < 5);
		
		
		
		System.out.println("****** Fin del GA ******");
				
		return poblacion;
	}
	
	
	public static void cruzarSeleccion(List<Phrases> seleccion){
		
		for(int i = 0 ; i < seleccion.size(); i+=2) {
			
			System.out.println("*-Nuevo par de frases-*");
			
			Random ran_cruce = new Random();
			
			if( ran_cruce.nextInt(100) < PROB_CRUCE ) {
				
					cross.casar_hijos(seleccion.get(i) , seleccion.get(i+1) );
					
					Random ran_mut = new Random();
					
					if ( ran_mut.nextInt(100) < PROB_MUTACION) 
						Mutation.hemiola(seleccion.get(i));
					
					if ( ran_mut.nextInt(100) < PROB_MUTACION)
						Mutation.hemiola(seleccion.get(i+1));
					
		    }
		}
	}
	
	public static List<Phrases> seleccionTorneos(List<Phrases> poblacion){
		
		List<Phrases> seleccion = new ArrayList<Phrases>();
		seleccion = poblacion;
		
		
		
		
		
		
		
		return poblacion;
	}
	
	public static List<Phrases> generarPoblacionInicial(){
		
		PhrasePopulation poblacion_inicial = new PhrasePopulation();
		List<Phrases> poblacion = poblacion_inicial.populationP;
		
		
		
		
		return poblacion;
	}
	
	public static void imprimirFraseNumeros(Phrases frase){
		
		System.out.println("INICIO -> Impresion numeros de la frase");
		
		System.out.println("Frase id: " + frase.id);
		
		Iterator <Measures> iterM = frase.measure_list.iterator();
		
		while(iterM.hasNext()){
			Measures compas = iterM.next();
			
			System.out.print("Measure id: " + compas.id + " || ");
			
			Iterator <Integer> iterInt = compas.notas.iterator();
			
			while(iterInt.hasNext()){
				Integer notaNumber = iterInt.next();
				
				System.out.print(notaNumber + " ");
				
			}
			System.out.println();
			
		}
		
		System.out.println("FIN -> Impresion numeros de la frase");
		
	}
	
}
