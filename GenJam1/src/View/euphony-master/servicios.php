<?php
	$HTTP_RAW_POST_DATA = file_get_contents("php://input"); //es donde esta tu JSON DE ENTRADA, lo que recibe
	$data=json_decode($HTTP_RAW_POST_DATA,true); //lo decodeamos a un arreglo json
	//{"usuario":"panxito", "password":"1234"} este es el json que llega
	$lista_acordes = $data['lista_acordes']; //sacamos la info
	$tempo = $data['tempo']; //sacamos la info
	$voicings = $data['voicings']."";
	$cortes2 = $data['cortes2']."";
	$cortes4 = $data['cortes4']."";
	$ordenamiento = $data['ordenamiento']."";
	
	$filename = "datos.txt";
	$file = fopen( $filename, "w" );
	if( $file == false )
	{
	   echo ( "Error in opening new file" );
	   exit();
	}
	else {
		fwrite($file, $tempo."\n");
		fwrite($file, $voicings."\n");
		fwrite($file, $cortes2."\n");
		fwrite($file, $cortes4."\n");
		fwrite($file, $ordenamiento."\n");
		for($i = 0; $i < count($lista_acordes); $i++) {
			//echo $lista_acordes[$i];
			fwrite( $file, $lista_acordes[$i]."\n" );
		}
		fclose( $file );
	}
	//ya tenemos el archivo
	
	$old_path = getcwd();
	
	chdir('../../../bin');
	//echo "antes<br>";
	$res = exec('export DYLD_LIBRARY_PATH="";java -cp .:./../lib/mysql-connector-java-5.1.30-bin.jar:./../lib/jMusic1.6.4.jar GenJam.GenJam ./../bin/View/euphony-master/datos.txt');
	//echo $res;
	chdir($old_path);
	
	$path_midi = "http://localhost/workspace eclipse/GenJam1/bin/out.midi,http://localhost/GenJam1/bin/out.midi";

	header("Content-Type: text/html;charset=utf-8"); //esto es para las ñ y acentos
	print json_encode($path_midi); //json_encode, serializa un arreglo a un formato JSON. (lo que devolvemos) 
?>