package javapack;

import java.util.*;

public class Simbolo {
	String nombre;
	String tipo;
	String valor;
	int fila;
	int columna;
	boolean inicializada;
	ArrayList<String> usadaEn = new ArrayList<String>();
	
	public Simbolo(String nombre, int fila, int columna){
		this.nombre = nombre;	
		tipo = "null";
		valor = "null";
		inicializada = false;
		this.fila = fila;
		this.columna = columna;
	}
	
	public Simbolo(String nombre, int fila, int columna, String tipo){
		this.nombre = nombre;
		this.tipo = tipo;
		valor = "undefined";
		inicializada = false;
		this.fila = fila;
		this.columna = columna;
	}
	
	public Simbolo(String nombre, int fila, int columna, String tipo, String valor){
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		inicializada = true;
		this.fila = fila;
		this.columna = columna;
	}
	
	public String toString(){
		String tab = "\t\t\t";
		String tab2 = "\t\t";
		if(nombre.length() > 14){
			tab = "\t";
		}else
			if(nombre.length() > 7){
				tab = "\t\t";
			}
		if(tipo.equals("pseudo")){
			tab2 = "\t";
		}
		String in;
		if(inicializada) in = "Si"; else in = "No";
		String tab3;
		if(tipo.equals("reservada")) tab3 = "\t"; else tab3 = "\t\t";
		return nombre + tab+tipo+tab3+valor+tab2+fila+"\t"+columna+"\t"+in+"\t"+usadaEn.toString();
	}
	
	public String filaYColumna(){
		return "en la linea "+fila+", caracter "+columna;
	}
	
}
