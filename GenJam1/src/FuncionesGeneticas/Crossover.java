package FuncionesGeneticas;

import java.util.ArrayList;
import java.util.List;

import Elements.Measures;
import Elements.Pair;
import Elements.PhrasePopulation;
import Elements.Phrases;

public class Crossover {
	//public PhrasePopulation resultado_final = new PhrasePopulation();
	
	public void casar_hijos(Phrases frase1, Phrases frase2){
		
		Pair puntos[] = new Pair [3];
		
		Pair par_res = new Pair(-1, Integer.MAX_VALUE);

		for ( int j = 0 ; j < 3; j++ ) {
			Pair par_aux = calculaPunto( j, frase1, frase2) ;
			puntos[j] = par_aux;
			System.out.println("PUNTO " + j + " -> " + puntos[j].second);
			if ( par_res.second > par_aux.second )
				par_res = par_aux;
		}
		System.out.println("MINIMO " + par_res.first);
		String cad= "";
		boolean tipo = false;
				
		if(!tipo && (par_res.second == puntos[0].second) && puntos[0].second == puntos[1].second && puntos[1].second == puntos[2].second) {
			tipo = true;
			cad = "101";//111
		}
		if(!tipo && (par_res.second == puntos[0].second) && puntos[0].second == puntos[1].second) {
			tipo = true;
			cad = "100";//110
		}
		if(!tipo && (par_res.second == puntos[1].second) && puntos[1].second == puntos[2].second) {
			tipo = true;
			cad = "010";//011
		}
		if(!tipo && (par_res.second == puntos[0].second) && puntos[0].second == puntos[2].second) {
			tipo = true;
			cad = "110";//101
		}
	
		System.out.println("CAD " + cad);
		casamiento(par_res, frase1, frase2, tipo, cad);
	
		System.out.println("> Se realizo casamiento");
	}
	
	/*
	public void genera_frases(List <Phrases> lista_frases){
		//PhrasePopulation poblacion_frases = new PhrasePopulation();
		
		System.out.println ( "size : " + lista_frases.size() );
		List <Phrases> lista_auxiliar = null;
		List <Phrases> lista_res_auxiliar = new ArrayList<Phrases>();

		Pair puntos[] = new Pair [3];
		
		for ( int k = 0; k < 3; k++ ) {
			Pair par_res = new Pair(-1, Integer.MAX_VALUE);
			for ( int i = 0; i < lista_frases.size(); i += 2 ) {

				for ( int j = 0 ; j < 3; j++ ) {
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
	*/
	
	public void casamiento(Pair par_res, Phrases frase1, Phrases frase2, boolean tipo, String cad) {
		List <Phrases> lista = new ArrayList <Phrases>();
		List <Measures> l_msr1 = new ArrayList <Measures>();
		List <Measures> l_msr2 = new ArrayList <Measures>();
	
		if(tipo) {
			l_msr1.add(frase1.measure_list.get(0));
			l_msr2.add(frase2.measure_list.get(0));
			for(int i = 0; i < 3; i++) {
				if(cad.charAt(i) == '1'){
					l_msr1.add(frase2.measure_list.get(i+1));
					l_msr2.add(frase1.measure_list.get(i+1));
				}else{
					l_msr1.add(frase1.measure_list.get(i+1));
					l_msr2.add(frase2.measure_list.get(i+1));
				}
			}
			
			
		} else {
			for (int i = 0 ; i <= par_res.first ; i++){
				l_msr1.add(frase1.measure_list.get(i));
				l_msr2.add(frase2.measure_list.get(i));
			}
		
			for (int i = par_res.first + 1 ; i < frase1.measure_list.size() ; i++){
				l_msr2.add(frase1.measure_list.get(i));
				l_msr1.add(frase2.measure_list.get(i));
			}
			System.out.println("NO CAD : " + par_res.first);
		}
		
		String genre = (frase1.genre == frase2.genre)? frase1.genre : "Fusion" ;
		Phrases ph_1 = new Phrases(frase1.id*1000+frase2.id + par_res.first,l_msr1, genre);
		Phrases ph_2 = new Phrases(frase2.id*1000+frase1.id + par_res.first,l_msr2, genre);
		
		//lista.add(ph_1);
		//lista.add(ph_2);
		
		frase1.id = ph_1.id;
		frase1.measure_list = ph_1.measure_list;
		frase2.id = ph_2.id;
		frase2.measure_list = ph_2.measure_list;
		/*System.out.println("ANTES");
		Genetico.imprimirFraseNumeros(frase1);
		Genetico.imprimirFraseNumeros(frase2);
		System.out.println("DESPUES");*/
		//return lista;
	}

	public static Pair calculaPunto(int pos, Phrases frase1, Phrases frase2) {
		Measures msr0_f1 = frase1.measure_list.get(pos);
		Measures msr1_f1 = frase1.measure_list.get(pos+1);
		
		Measures msr0_f2 = frase2.measure_list.get(pos);
		Measures msr1_f2 = frase2.measure_list.get(pos+1);

		int inc;
		int inicio_der = msr0_f1.notas.size() - 1;
		int inicio_izq = 0;
		
		inc = -1;
		int msr0_not1 = get_nota_valida(msr0_f1, inicio_der, inc);
		//System.out.println("msr0_not1 " + msr0_not1);
		
		inc = +1;
		int msr1_not1 = get_nota_valida(msr1_f1, inicio_izq, inc);
		//System.out.println("msr1_not1 " + msr1_not1);
		
		/*Hold. Rest, multiple croosover, measure pag 151*/
		inc = -1;
		int msr0_not2 = get_nota_valida(msr0_f2, inicio_der, inc);
		//System.out.println("msr0_not2 " + msr0_not2);
		
		inc = +1;
		int msr1_not2 = get_nota_valida(msr1_f2, inicio_izq, inc); 
		//System.out.println("msr1_not2 " + msr1_not2);
		
		
		int dif_par1 = Math.abs(Math.abs(msr0_not1) - Math.abs(msr1_not1));
		int dif_par2 = Math.abs(Math.abs(msr0_not2) - Math.abs(msr1_not2));
		
		int dif_child1 = Math.abs(Math.abs(msr0_not1) - Math.abs(msr1_not2));
		int dif_child2 = Math.abs(Math.abs(msr0_not2) - Math.abs(msr1_not1));
		
		
		/*
		int dif_child1 = Math.abs(Math.abs(msr0_not1) - Math.abs(msr1_not2));
		int dif_child2 = Math.abs(Math.abs(msr0_not2) - Math.abs(msr1_not1));
		*/
		
		
		int diff1 = Math.abs(dif_par1 - dif_child1);
		int diff2 = Math.abs(dif_par2 - dif_child2);
		
		
		return new Pair(pos, Math.min(diff1, diff2));
		//return new Pair(pos, Math.min(dif_child1, dif_child2));
	}
	
	public static int get_nota_valida (Measures msr0_f1, int inicio, int inc) {
		int ind  = inicio;
		int nota = msr0_f1.notas.get(ind);
		
		/*System.out.println("ID " + msr0_f1.id);
		for( int i = 0 ; i < msr0_f1.notas.size(); i++){
			System.out.print(msr0_f1.notas.get(i)+ " ");
		}
		System.out.println();*/
		
		while ( (nota >= 15 || nota ==  0) && (ind <= 15 && ind >=0) ) {			
			//System.out.println("IND : " + ind);
			nota = msr0_f1.notas.get(ind);
			//System.out.println("IND : " + ind + " , NOTA : " + nota);
			ind += inc;
		}
		return nota;
	}
	
	public static Measures hemolia_condition(Phrases frase1, Phrases frase2){
		
		
		return null;
	}	
}
