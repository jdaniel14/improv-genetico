package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Elements.Measures;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Genetico {	
	
	public static List<Phrases> AG(){
		
		Integer generacion = 0;
		
		System.out.println("****** Inicio del GA ******");
		
		//Inicializo la poblaci—n de frases
		List<Phrases> poblacion = generarPoblacionInicial();
		
		System.out.println("-- Frase sin hemiola");
		
		imprimirFraseNumeros(poblacion.get(1));
		
		System.out.println("-- Frase con hemiola");
		
		Mutation.hemiola(poblacion.get(1));
		imprimirFraseNumeros(poblacion.get(1));
		
		
		do{
			//Selecciono por torneo a los que ser‡n cruzados/mutados
			List<Phrases> seleccion = seleccionTorneos(poblacion);
			
			//Aplico los operadores de mutacion
			cruzarSeleccion(seleccion);		
			
			//Mi poblacion ahora es lo seleccionado
			poblacion = seleccion;
			
			//Aumento el nœmero de la generaci—n en la que estoy
			generacion++;
			System.out.println("---------------------------------\n");
			//Repito el proceso por un nœmero de iteraciones
		} while(generacion < 3);
		
		
		
		
		System.out.println("****** Fin del GA ******");
				
		return poblacion;
	}
	
	
	public static void cruzarSeleccion(List<Phrases> seleccion){
		
		Crossover cross = new Crossover();
		cross.genera_frases(seleccion);
		/*
		Random ran = new Random();
		
		Mutation.hemiola(seleccion.get(ran.nextInt(seleccion.size())));
		Mutation.hemiola(seleccion.get(ran.nextInt(seleccion.size())));
		Mutation.hemiola(seleccion.get(ran.nextInt(seleccion.size())));
		Mutation.hemiola(seleccion.get(ran.nextInt(seleccion.size())));
		Mutation.hemiola(seleccion.get(ran.nextInt(seleccion.size())));
		*/
		
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
		
		System.out.println("INICIO -> Impresi—n nœmeros de la frase");
		
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
		
		System.out.println("FIN -> Impresi—n nœmeros de la frase");
		
	}
	
}
