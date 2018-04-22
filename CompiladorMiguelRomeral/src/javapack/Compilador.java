package javapack;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Compilador {
	
	public static void main(String argv[]) throws Exception{
		if(argv.length == 0){
			System.err.println("Por favor, introduzca uno o varios archivos .prog a analizar.");
		}
		for(String archivo : argv){
			File file = new File(archivo);
			if(file.exists() && file.getName().contains(".prog")){
		    	FileReader fr = new FileReader(file);
			  	Scan lex = new Scan(fr);
			  	lex.ponerArchivo(archivo);
			  	Parser miParser = new Parser(lex);
			  	miParser.limpiar();
			  	lex.ponerParser(miParser);
			  	System.out.println(
			  			"-------------------------------------------------------------------------------------------------------------------------------------------------");
			  	System.out.println(
			  			"************************************************\tAnalisis del archivo \""+archivo+"\"\t*************************************************");
			  	miParser.ponerPath(archivo);
			  	miParser.parse();
			}else{
				System.err.println("El archivo \""+archivo+"\" no ha podido ser leido, puesto que es necesario pasarle como parametro un archivo de tipo \".prog\" que exista.");
			}
		}
	}	
	
	public static String mostrarFallo(String path, int linea, int columna) throws ArrayIndexOutOfBoundsException{
		String msg = "";
		try {
			linea--;
			String programa;
			programa = "";

			Path p = Paths.get(path);
			byte[] bytes = Files.readAllBytes(p);
			InputStream is = new ByteArrayInputStream(bytes);
			BufferedReader b = new BufferedReader(new InputStreamReader(is));

			while (linea > 0) {
				b.readLine();
				linea--;
			}
			programa = b.readLine();
			char[] letras = programa.toCharArray();
			b.close();
			msg = programa + "\r\n";
			for (int i = 0; i < columna; i++) {
				if (letras[i] == '\t') {
					msg += "\t";
				} else {
					msg += " ";
				}
			}
			msg += "^";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public static ArrayList<String> sacaIDEs(String nodo) {
		ArrayList<String> ides = new ArrayList<String>();
		boolean escribiendo = false;
		int lenght = nodo.length();
		int i = 4;
		String ide = "";
		while(i<lenght){
			if(nodo.substring(i-4, i).equals("ide(") ||
					nodo.substring(i-3, i).equals("num(")){
				escribiendo = true;
			}
			if(escribiendo){
				if(nodo.charAt(i) == ')' ||
						nodo.charAt(i) == '('){
					ides.add(ide);
					ide = "";
					escribiendo = false;
					
				}else{
					ide += nodo.charAt(i);
				}
			}
			i++;
			
		}
		if(escribiendo){
			ides.add(ide);
		}
		return ides;
	}

	public static String tipoIDEs(TablaSimbolos ts, ArrayList<String> ides){
		boolean entero = false;
		boolean booleano = false;
		for(String s : ides){
			if(ts.esta(s)){
				Simbolo sim = ts.getSimbolo(s);
				if(sim.tipo.equals("integer")){
					if(!entero){
						entero = true;
					}
				}else{
					if(!booleano){
						booleano = true;
					}
				}
			}
		}
		String tipo = "ninguno";
		if(entero){
			if(booleano){
				tipo = "ambos";
			}else{
				tipo = "integer";
			}
		}else{
			if(booleano){
				tipo = "boolean";
			}
		}
		return tipo;
	}
	
	public static boolean finWhile(ArrayList<String> vals, String nodo) {
		boolean fin = false;
		for(String v : vals){
			if(nodo.contains("asign("+v))
				fin = true;
		}
		return fin;
	}
	
	public static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
	
}
