package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.List;

import Elements.Measures;
import Elements.Pair;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Crossover {
	public PhrasePopulation resultado_final;
	
	public void genera_frases(List <Phrases> lista_frases){
		//PhrasePopulation poblacion_frases = new PhrasePopulation();
		
		List <Phrases> lista_auxiliar = null;
		List <Phrases> lista_res_auxiliar = new ArrayList<Phrases>();
		for ( int k = 0; k < 3; k++ ) {
			Pair par_res = new Pair(-1, Integer.MAX_VALUE);
			for ( int i = 0; i < lista_frases.size(); i += 2 ) {
				for ( int j = 0 ; j < 2; j++ ) {
					Pair par_aux = calculaPunto( j, lista_frases.get(i), lista_frases.get(i + 1)) ;
					if ( par_res.second > par_aux.second )
						par_res = par_aux;
				}
				lista_auxiliar = casamiento(par_res, lista_frases.get(i), lista_frases.get(i + 1));
				lista_res_auxiliar.addAll(lista_auxiliar);
			}
			lista_frases.addAll(lista_res_auxiliar);
		}
		resultado_final.populationP = lista_frases;
	}
	
	public List <Phrases> casamiento(Pair par_res, Phrases frase1, Phrases frase2) {
		List <Phrases> lista = new ArrayList <Phrases>();
		List <Measures> l_msr1 = new ArrayList <Measures>();
		List <Measures> l_msr2 = new ArrayList <Measures>();
	
		for (int i = 0 ; i <= par_res.first ; i++){
			l_msr1.add(frase1.measureId.get(i));
			l_msr2.add(frase2.measureId.get(i));
		}
		
		for (int i = par_res.first + 1 ; i < frase1.measureId.size() ; i++){
			l_msr2.add(frase1.measureId.get(i));
			l_msr1.add(frase2.measureId.get(i));
		}
		
		String genre = (frase1.genre == frase2.genre)? frase1.genre : "Fusion" ;
		Phrases ph_1 = new Phrases(frase1.id*1000+frase2.id + par_res.first,l_msr1, genre);
		Phrases ph_2 = new Phrases(frase2.id*1000+frase1.id + par_res.first,l_msr2, genre);
		lista.add(ph_1);
		lista.add(ph_2);
		return lista;
	}

	public static Pair calculaPunto(int pos, Phrases frase1, Phrases frase2) {
		Measures msr0_f1 = frase1.measureId.get(pos);
		Measures msr1_f1 = frase1.measureId.get(pos+1);
		
		Measures msr0_f2 = frase2.measureId.get(pos);
		Measures msr1_f2 = frase2.measureId.get(pos+1);
		
		int dif_par1 = (msr0_f1.notas.get(7) - msr1_f1.notas.get(0));
		int dif_par2 = (msr0_f2.notas.get(7) - msr1_f2.notas.get(0));
		
		int dif_child1 = (msr0_f1.notas.get(7) - msr1_f2.notas.get(0));
		int dif_child2 = (msr0_f2.notas.get(7) - msr1_f1.notas.get(0));
		
		int diff1 = Math.abs(dif_par1 - dif_child1);
		int diff2 = Math.abs(dif_par2 - dif_child2);
		
		return new Pair(pos, Math.min(diff1, diff2));
	}
}
