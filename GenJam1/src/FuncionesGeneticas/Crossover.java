package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.List;

import Elements.Measures;
import Elements.Pair;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Crossover {
	public PhrasePopulation resultado_final = new PhrasePopulation();
	
	public void genera_frases(List <Phrases> lista_frases){
		//PhrasePopulation poblacion_frases = new PhrasePopulation();
		
		System.out.println ( "size : " + lista_frases.size() );
		List <Phrases> lista_auxiliar = null;
		List <Phrases> lista_res_auxiliar = new ArrayList<Phrases>();

		Pair puntos[] = new Pair [3];
		
		for ( int k = 0; k < 3; k++ ) {
			Pair par_res = new Pair(-1, Integer.MAX_VALUE);
			for ( int i = 0; i < lista_frases.size(); i += 2 ) {
				for ( int j = 0 ; j < 2; j++ ) {
					Pair par_aux = calculaPunto( j, lista_frases.get(i), lista_frases.get(i + 1)) ;
					puntos[j] = par_aux;
					if ( par_res.second > par_aux.second )
						par_res = par_aux;
				}
				String cad= "";
				boolean tipo = false;
				
				if(puntos[0].second == puntos[1].second && puntos[1].second == puntos[2].second) {
					tipo = true;
					cad = "111";
				}
				if(puntos[0].second == puntos[1].second) {
					tipo = true;
					cad = "110";
				}
				if(puntos[1].second == puntos[2].second) {
					tipo = true;
					cad = "011";
				}
				if(puntos[0].second == puntos[2].second) {
					tipo = true;
					cad = "010";
				}

				lista_auxiliar = casamiento(par_res, lista_frases.get(i), lista_frases.get(i + 1), tipo, cad);
				lista_res_auxiliar.addAll(lista_auxiliar);
			}
			lista_frases.addAll(lista_res_auxiliar);
			System.out.println(lista_frases.size());
		}

		System.out.println("FINAL " + lista_frases.size());
		
		resultado_final.populationP = new ArrayList<Phrases>();
		resultado_final.populationP.addAll( lista_frases );
		System.out.println("FINAL " + resultado_final.populationP.size());

	}
	
	public List <Phrases> casamiento(Pair par_res, Phrases frase1, Phrases frase2, boolean tipo, String cad) {
		List <Phrases> lista = new ArrayList <Phrases>();
		List <Measures> l_msr1 = new ArrayList <Measures>();
		List <Measures> l_msr2 = new ArrayList <Measures>();
	
		if(tipo) {
			
			
		} else {
			for (int i = 0 ; i <= par_res.first ; i++){
				l_msr1.add(frase1.measure_list.get(i));
				l_msr2.add(frase2.measure_list.get(i));
			}
		
			for (int i = par_res.first + 1 ; i < frase1.measure_list.size() ; i++){
				l_msr2.add(frase1.measure_list.get(i));
				l_msr1.add(frase2.measure_list.get(i));
			}
		}
		
		String genre = (frase1.genre == frase2.genre)? frase1.genre : "Fusion" ;
		Phrases ph_1 = new Phrases(frase1.id*1000+frase2.id + par_res.first,l_msr1, genre);
		Phrases ph_2 = new Phrases(frase2.id*1000+frase1.id + par_res.first,l_msr2, genre);
		lista.add(ph_1);
		lista.add(ph_2);
		return lista;
	}

	public static Pair calculaPunto(int pos, Phrases frase1, Phrases frase2) {
		Measures msr0_f1 = frase1.measure_list.get(pos);
		Measures msr1_f1 = frase1.measure_list.get(pos+1);
		
		Measures msr0_f2 = frase2.measure_list.get(pos);
		Measures msr1_f2 = frase2.measure_list.get(pos+1);

		int inc;
		int inicio_der = 7;
		int inicio_izq = 0;
		
		inc = -1;
		int msr0_not1 = get_nota_valida(msr0_f1, inicio_der, inc);
		
		inc = +1;
		int msr1_not1 = get_nota_valida(msr1_f1, inicio_izq, inc);
		
		/*Hold. Rest, multiple croosover, measure pag 151*/
		inc = -1;
		int msr0_not2 = get_nota_valida(msr0_f1, inicio_der, inc);
		
		inc = +1;
		int msr1_not2 = get_nota_valida(msr1_f1, inicio_izq, inc); 
		
		
		int dif_par1 = (msr0_not1 - msr1_not1);
		int dif_par2 = (msr0_not2 - msr1_not2);
		
		int dif_child1 = (msr0_not1 - msr1_not2);
		int dif_child2 = (msr0_not2 - msr1_not1);
		
		//int dif_par1 = (msr0_f1.notas.get(7) - msr1_f1.notas.get(0));
		//int dif_par2 = (msr0_f2.notas.get(7) - msr1_f2.notas.get(0));
		
		//int dif_child1 = (msr0_f1.notas.get(7) - msr1_f2.notas.get(0));
		//int dif_child2 = (msr0_f2.notas.get(7) - msr1_f1.notas.get(0));
		
		int diff1 = Math.abs(dif_par1 - dif_child1);
		int diff2 = Math.abs(dif_par2 - dif_child2);
		
		return new Pair(pos, Math.min(diff1, diff2));
	}
	
	public static int get_nota_valida (Measures msr0_f1, int inicio, int inc) {
		int ind  = inicio;
		int nota = msr0_f1.notas.get(ind);
		while(nota >= 15 || nota ==  0) {
			ind += inc;
			nota = msr0_f1.notas.get(ind);
		}
		return nota;
	}
}
