package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.List;

import Elements.Measures;
import Elements.Pair;
import Elements.Phrases;


public class Ordenamiento {
	public static List <Pair> ini_fin(List<Phrases> poblacion){
		
		List <Pair> list_res = new ArrayList<Pair>();
		
		Measures msr0_f1, msr1_f1;
		int inc;
		int inicio_der;
		int inicio_izq;
		int indice;
		Crossover cross = new Crossover();
		
		for(int i = 0 ; i < poblacion.size(); i++){
			Phrases ph = poblacion.get(i);
			
			int msr0_not1 = -1, msr1_not1 = -1;
			
			indice = 3;
			inc = -1;
			while(msr0_not1<=0 || msr0_not1>=15){
				msr0_f1 = ph.measure_list.get(indice);
				inicio_der = msr0_f1.notas.size() - 1;
				msr0_not1 = cross.get_nota_valida(msr0_f1, inicio_der, inc);
				indice += inc;
			}
			
			indice = 0;
			inc = +1;
			while(msr1_not1<=0 || msr1_not1>=15){
				msr1_f1 = ph.measure_list.get(indice);
				inicio_izq = 0;
				msr1_not1 = cross.get_nota_valida(msr1_f1, inicio_izq, inc);
				indice += inc;
			}
			
			
			
			if(msr0_not1==0 || msr0_not1==15 || msr1_not1==0 || msr1_not1==15){
				System.out.println("..........................................");
				Genetico.imprimirFraseNumeros(ph);
				System.out.println("..........................................");
			}
			
			list_res.add(new Pair(msr1_not1, msr0_not1));
			
		}
		return list_res;
	}

	public static List<Phrases> ordenar(List<Phrases> poblacion) {
		
		int punto = poblacion.size() / 2;
		List<Phrases> lista_orden = new ArrayList<Phrases>();
		List<Phrases> lista_final = new ArrayList<Phrases>();
		
		for(int i = 0 ; i < punto; i++)
			lista_orden.add(poblacion.get(i));
		
		lista_final = permutacion(lista_orden);
				
		for(int i = punto ; i < poblacion.size(); i++)
			lista_final.add(poblacion.get(i));
		
		return lista_final;
	}
	public static List<Phrases> permutacion(List<Phrases> poblacion/*List <Pair> lista_phrases*/) {

		List <Pair> lista_phrases = ini_fin(poblacion);
		List<Integer> arr_res = new ArrayList<Integer>();
		int tam = lista_phrases.size();
		boolean usado[] = new boolean[tam];
		int tabla_dist[][] = new int [tam][tam];
		
		for(int i = 0 ; i < tam; i++) {
			usado[i] = false;
			tabla_dist[i][i] = -1;
		}
		
		for(int i = 0 ; i < tam; i++) {
			for(int j = 0 ; j < tam; j++) {
				if(i != j){
					tabla_dist[i][j] = Math.abs(lista_phrases.get(i).second - lista_phrases.get(j).first);
				}
			}
		}
		for(int i = 0 ; i < tam; i++) {
			for(int j = 0 ; j < tam; j++) {
				System.out.print(tabla_dist[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("second");
		System.out.println("TAM " + tam);
		
		Pair par_x = new Pair(-1,Integer.MIN_VALUE);
		Pair par_y = new Pair(-1,Integer.MAX_VALUE);
		
		Pair arr_res_x[] = new Pair [tam];
		Pair arr_res_y[] = new Pair [tam];
		
		for(int i = 0 ; i < tam; i++){
			arr_res_x[i] = new Pair();
			arr_res_y[i] = new Pair();
		}
		
		for(int i = 0 ; i < tam; i++){
			int val_y = Integer.MAX_VALUE;
			for(int j = 0 ; j < tam; j++){
				if(tabla_dist[j][i] != -1){
					if ( val_y > tabla_dist[j][i] ) {
						val_y = tabla_dist[j][i];
						arr_res_y[i].first = j;
						arr_res_y[i].second = val_y;
					}
				}
			}
		}
		
		Pair min_y = new Pair(-1, Integer.MIN_VALUE);
		for(int i = 0 ; i < tam; i++){
			//System.out.print(arr_res_y[i].second + " ");
			if(arr_res_y[i].second > min_y.second){
				min_y.first = i;
				min_y.second = arr_res_y[i].second;
			}
		}
		//System.out.println();
		usado[min_y.first] = true;
		for(int i = 0 ; i < tam; i++){
			int val_x = Integer.MAX_VALUE;
			for(int j = 0 ; j < tam; j++){
				if(tabla_dist[i][j] != -1){
					if ( val_x > tabla_dist[i][j] ) {
						val_x = tabla_dist[i][j];
						arr_res_x[i].first = i;
						arr_res_x[i].second = val_x;
					}
				}
			}
		}
		arr_res_x[min_y.first] = new Pair(-1, Integer.MIN_VALUE);
		
		Pair min_x = new Pair(-1, Integer.MIN_VALUE);
		for(int i = 0 ; i < tam; i++){
			//System.out.print(arr_res_x[i].second + " ");
			if(arr_res_x[i].second > min_x.second){
				min_x.first = i;
				min_x.second = arr_res_x[i].second;
			}
		}
		//System.out.println();
		usado[min_x.first] = true;
		
		arr_res.add(min_y.first);
		System.out.println("MAXIMO I : " + lista_phrases.get(min_y.first).first + " - " + lista_phrases.get(min_y.first).second);		
		
		int cont_usados = 2;
		int ind_busq = min_y.first;
		
		while(cont_usados < tam){
			Pair busq_min = busca_min_fila(tabla_dist, usado, ind_busq, tam);
			System.out.println("MAXIMO  : " + lista_phrases.get(busq_min.first).first + " - " + lista_phrases.get(busq_min.first).second);
			arr_res.add(busq_min.first);
			usado[busq_min.first] = true;
			ind_busq = busq_min.first;
			cont_usados++;
		}
		arr_res.add(min_x.first);
		System.out.println("MAXIMO F : " + lista_phrases.get(min_x.first).first + " - " + lista_phrases.get(min_x.first).second);
		//return arr_res;
		
		List<Phrases> poblacion_res = new ArrayList<Phrases>();
		for(int i = 0; i < arr_res.size(); i++){
			poblacion_res.add(poblacion.get(arr_res.get(i)));
		}
		return poblacion_res;
	}
	
	public static Pair busca_min_fila(int tabla_dist[][], boolean usado[], int ind_busq, int tam){
		 
		 Pair min = new Pair(-1, Integer.MAX_VALUE);
		 for(int i = 0 ; i < tam; i++){
			 //System.out.println("AQUI1");
			 //System.out.println(tabla_dist[ind_busq][i] + " - " + usado[i]);
			 if(tabla_dist[ind_busq][i] != -1 && !usado[i]){
				 //System.out.println("AQUI2");
				 if(min.second > tabla_dist[ind_busq][i]){
					 //System.out.println("AQUI3");
					 min.first = i;
					 min.second = tabla_dist[ind_busq][i];
				 }
			 }
		 }
		 return min;
	}
}
