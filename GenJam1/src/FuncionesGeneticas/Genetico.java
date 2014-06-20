package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Elements.Measures;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Genetico {	
	
	static final int PROB_MUTACION = 0;
	static final int PROB_CRUCE = 70;
	static final int POBLACION = 16;
	
	public static Crossover cross = new Crossover();
	
	public static Random randoms = new Random();
	
	public static List<Phrases> AG(){
		
		Integer generacion = 0;
		
		System.out.println("****** Inicio del GA ******");
		

		//Inicializo la poblaci�n de frases
		List<Phrases> poblacion = generarPoblacionInicial();		

		
		do{
			//Selecciono por torneo a los que seran cruzados/mutados
			List<Phrases> seleccion = seleccionTorneos(poblacion);
			
			//Aplico los operadores de mutacion
			cruzarSeleccion(seleccion);		
			
			//Mi poblacion ahora es lo seleccionado
			poblacion = seleccion;
			
			//Aumento el n�mero de la generacion en la que estoy
			generacion++;
	
			//Repito el proceso por un numero de iteraciones
		} while(generacion < 5);
		
		System.out.println("****** Fin del GA ******");

		return poblacion;
	}
	
	
	public static void cruzarSeleccion(List<Phrases> seleccion){
		
		for(int i = 0 ; i < seleccion.size(); i+=2) {
			
			imprimirFraseNumeros(seleccion.get(i));
			imprimirFraseNumeros(seleccion.get(i+1));
			
			System.out.println("*-Nuevo par de frases-*");
			
			if( randoms.nextInt(100) < PROB_CRUCE ) {
				
					cross.casar_hijos(seleccion.get(i) , seleccion.get(i+1) );
					 
					imprimirFraseNumeros(seleccion.get(i));
					imprimirFraseNumeros(seleccion.get(i+1));
					
					if ( randoms.nextInt(100) < PROB_MUTACION){
						Mutation.hemiola(seleccion.get(i));
					}
						
					
					if ( randoms.nextInt(100) < PROB_MUTACION){
						Mutation.hemiola(seleccion.get(i+1));
					}
					
		    }
		}
	}
	
	public static List<Phrases> seleccionTorneos(List<Phrases> poblacion){
		
		//Copiar las frases a seleccion
		List<Phrases> seleccion = new ArrayList<Phrases>();
		
		for(int i = 0; i < poblacion.size(); i++){
			
			seleccion.add(clonarPhrases(poblacion.get(i)));
						
		}
		
		java.util.Collections.shuffle(seleccion);
		
		return seleccion;
	}
	
	public static Phrases clonarPhrases(Phrases frase){
		
		Phrases temp = new Phrases();
		
		temp.genre = frase.genre;
		temp.id = frase.id;
		
		List<Measures> tempLM = new ArrayList<Measures>();
		

		Iterator<Measures> iterM = frase.measure_list.iterator();
		
		while(iterM.hasNext()){
			Measures itemM = iterM.next();
			
			Measures tempM = new Measures();
			
			tempM.id = itemM.id;
			
			List<Integer> tempLI = new ArrayList<Integer>();
			
			for(int i = 0; i < itemM.notas.size(); i++){
				tempLI.add(itemM.notas.get(i));
			}
			
			tempM.notas = tempLI;
			
			tempLM.add(tempM);
			
		}
		
		temp.measure_list = tempLM;
		
		return temp;
	}
	
	public static List<Phrases> generarPoblacionInicial(){
		
		PhrasePopulation poblacion_inicial = new PhrasePopulation();
		List<Phrases> poblacion = poblacion_inicial.populationP;
		List<Phrases> lista_aux = new ArrayList<Phrases>();
		Crossover cross = new Crossover();
		for(int i = 0; i < poblacion.size(); i+=2) {
			Phrases ph1 = clonarPhrases(poblacion.get(i));
			Phrases ph2 = clonarPhrases(poblacion.get(i+1));
			cross.casar_hijos(ph1, ph2);
			lista_aux.add(ph1);
			lista_aux.add(ph2);
		}
		System.out.println("----------------size : " + lista_aux.size());
		poblacion.addAll(lista_aux);
		System.out.println("----------------size : " + poblacion.size());
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
