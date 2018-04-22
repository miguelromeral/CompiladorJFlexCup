package javapack;

import java.util.*;
import java.util.Map.*;

public class TablaSimbolos {
	
	public HashMap<String,Simbolo> tabla;
	public static String[] reservadas = {"program", "is", "begin",
		"end","var","integer","boolean","read",
		"write","skip","while","do","if","then",
		"else","or","and","true","false","not"
		};
	
	public TablaSimbolos(){
		tabla = new HashMap<String,Simbolo>();
		for(String s : reservadas){
			Simbolo sim = new Simbolo(s, 0, 0, "reservada", s);
			tabla.put(s, sim);
		}
		
	}
	public void insertar(Simbolo s){
		tabla.put(s.nombre, s);
	}
	public String tipo(String nombre){
		String t = null;
		if(tabla.containsValue(nombre)){
			t = tabla.get(nombre).tipo;
		}
		return t;
	}
	
	public String toString(){
		/*RESERVADAS*/
		String r = "Identificadores reservados:\n";
		r += ".................................................................................................................................................\n";
		r += "Nombre\t\tUsado en lineas (Linea,Caracter)\n"+
				".................................................................................................................................................\n";
		Iterator<Entry<String, Simbolo>> it = tabla.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Simbolo> e = (Map.Entry<String, Simbolo>) it.next();
			Simbolo s = tabla.get(e.getKey());
			if(s.tipo.equals("reservada")){
				r += s.nombre +"\t\t"+ s.usadaEn+"\n";
			}
		}
		r += ".................................................................................................................................................\n";
		r += "Nombre\t\t\tTipo\t\tValor\t\tLinea\tCarac.\tInic.\tUsada en lineas (Linea,Caracter)\n";
		r += ".................................................................................................................................................\n";
		it = tabla.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Simbolo> e = (Map.Entry<String, Simbolo>) it.next();
			Simbolo s = tabla.get(e.getKey());
			if(!s.tipo.equals("reservada"))
			r += s.toString() +"\n";
		}
		return r;
	}
	
	public ArrayList<String> sinUsar(String nodo){
		ArrayList<String> us = new ArrayList<String>();
		Iterator<Entry<String, Simbolo>> it = tabla.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Simbolo> e = (Map.Entry<String, Simbolo>) it.next();
			Simbolo s = tabla.get(e.getKey());
			if(!s.tipo.equals("reservada"))
				if(!nodo.contains("ide("+s.nombre) && !nodo.contains("asign("+s.nombre)){
					us.add(s.nombre);
				}
		}
		return us;
	}
	
	public void usada(String res, int fila, int columna){
		if(tabla.containsKey(res)){
			Simbolo s = tabla.get(res);
			s.usadaEn.add("("+fila+","+columna+")");
		}
	}
	
	public ArrayList<Simbolo> getIdentificadores(){
		ArrayList<Simbolo> ides = new ArrayList<Simbolo>();
		Iterator<Entry<String, Simbolo>> it = tabla.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Simbolo> e = (Map.Entry<String, Simbolo>) it.next();
			Simbolo s = tabla.get(e.getKey());
			if(!s.tipo.equals("reservada"))
				ides.add(s);
		}
		return ides;
	}
	
	public Simbolo getSimbolo(String nombre){
		return tabla.get(nombre);
	}
	public boolean esta(String nombre){
		return tabla.containsKey(nombre);
	}
}
