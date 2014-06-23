package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Elements.Measures;
import Elements.Phrases;

public class Mutation {
	
	//Convenio para id's de hijos mutados: 2000 + id_original
	
	//Resta una octava (-6) a las frases
	public static void octave(Phrases frase){
		
	}
	
	//Transpone una 3era abajo (-2) a las frases que se encuentran en el limite 14
	public static void transpose2(Phrases frase){
		
	}
	
	public static void hemiola(Phrases frase){
		
		//Busco 3 pares de corcheas juntas de acuedo al siguiente criterio
		//La ultima nota debe der un numero mayor o menor al primero por una unidad
		
		//Dejo un silecio de un tiempo
		//Repito las corcheas 5 veces
				
		Iterator<Measures> iterM = frase.measure_list.iterator();
		
		List<List<Integer>> posibles = new ArrayList<List<Integer>>();
		
		List<Integer> repetir = new ArrayList<Integer>();
		
		while(iterM.hasNext()){
			
			Measures item = iterM.next();
			
			List<Integer> lista_notas = item.notas;
			
			for(int i = 0; i < lista_notas.size() - 10; i = i+ 2){
				
				if(validarPosicion(lista_notas,i)) {
										
					for(int j = i; j < i + 12; j ++){	
						repetir.add(lista_notas.get(j));						
					}					
					
					posibles.add(repetir);
					
					break;
				}
				else{
					 continue;
				}
			}
			
			if(posibles.size() > 0){
				
				Random ran = new Random();
				
				crearFraseHemiola(posibles.get(ran.nextInt(posibles.size())), frase.measure_list);
				
				System.out.println("> Se realiz— un hemiola");
								
				break;
			}
			
		}
		
	}
	
	
	public static void crearFraseHemiola(List<Integer> repetir, List<Measures> fraseinicial){

		System.out.print("Repeticion de: ");
		for(int i = 0; i < repetir.size(); i++){
			System.out.print(repetir.get(i) + " ");
		}
		System.out.println();
	
		Measures temp = new Measures();
		
		Integer cantMeasures = 0;
		Integer disponibles = 16;
		
		Integer actualIngresado = 0;
		
		//Inicializo el measure con un silencio de negra
		temp.id = fraseinicial.get(cantMeasures).id + 2000;
		
		
		while(cantMeasures != 4){
			
			if(disponibles == 0){
				
				fraseinicial.get(cantMeasures).id = temp.id;
				fraseinicial.get(cantMeasures).notas = temp.notas;
				
				cantMeasures++;
				
				if(cantMeasures == 4) break;
				
				disponibles = 16;				
				
				temp = new Measures();
				temp.id = fraseinicial.get(cantMeasures).id + 2000;
			}
			
			while(disponibles != 0){
				
				temp.notas.add(repetir.get(actualIngresado));
			
				disponibles--;
				actualIngresado++;
				
				if((actualIngresado == 12) && (cantMeasures == 3)){
					actualIngresado = 0;
					cantMeasures = 4;
					break;
				}
				
				if(actualIngresado == 12) actualIngresado = 0;
				
			}
			
		}	
		
		temp.notas.add(0);
		temp.notas.add(0);
		temp.notas.add(0);
		temp.notas.add(0);
		
		fraseinicial.get(3).id = temp.id;
		fraseinicial.get(3).notas = temp.notas;
		
		
		
	}
	
	public static boolean validarPosicion(List<Integer> lista_notas, int i){
		
		return (	
			(lista_notas.get(i) != 0) && 
			(lista_notas.get(i) != 15) &&
			(lista_notas.get(i) != 16) &&
			(lista_notas.get(i + 2) != 0) && 
			(lista_notas.get(i + 2) != 15) &&
			(lista_notas.get(i + 2) != 16) &&
			(lista_notas.get(i + 4) != 0) && 
			(lista_notas.get(i + 4) != 15) &&
			(lista_notas.get(i + 4) != 16) &&
			(lista_notas.get(i + 6) != 0) && 
			(lista_notas.get(i + 6) != 15) &&
			(lista_notas.get(i + 6) != 16) &&
			(lista_notas.get(i + 8) != 0) && 
			(lista_notas.get(i + 8) != 15) &&
			(lista_notas.get(i + 8) != 16) &&
			(lista_notas.get(i + 10) != 0) && 
			(lista_notas.get(i + 10) != 15) &&
			(lista_notas.get(i + 10) != 16) &&
			(
				(lista_notas.get(i + 1) == 0) ||
				(lista_notas.get(i + 1) == 15)
			) &&
			(		
				(lista_notas.get(i + 3) == 0) ||
				(lista_notas.get(i + 3) == 15)
			) &&
			(		
				(lista_notas.get(i + 5) == 0) ||
				(lista_notas.get(i + 5) == 15)
			) &&
			(		
				(lista_notas.get(i + 7) == 0) ||
				(lista_notas.get(i + 7) == 15)
			) &&
			(		
				(lista_notas.get(i + 9) == 0) ||
				(lista_notas.get(i + 9) == 15)
			) &&
			(		
				(lista_notas.get(i + 11) == 0) ||
				(lista_notas.get(i + 11) == 15)
			)/* &&
			(
				(lista_notas.get(i) == lista_notas.get(i + 10) + 1) ||
				(lista_notas.get(i) == lista_notas.get(i + 10) - 1)
			)*/
			
		);
	}
	
	
	
	
}
