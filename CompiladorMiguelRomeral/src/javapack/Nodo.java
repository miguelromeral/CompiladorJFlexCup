package javapack;

public class Nodo {
	 
    /* Declaraciones de variables */
    public String valor;
    public boolean term;
    public Nodo padre;
    public Nodo hijo;
    public Nodo derecho;
    public Nodo izquierdo;
    //public String tipo;
 
    public Nodo(Nodo n) {
        this.valor = n.valor;
        term = n.term;
        padre = n.padre;
        derecho = n.derecho;
        izquierdo = n.izquierdo;
        hijo = n.hijo;
    }
    
    public Nodo(String valor) {
        this.valor = valor;
        term = false;
        padre = null;
        derecho = null;
        izquierdo = null;
        hijo = null;
    }
    
  /*  public Nodo(String valor, boolean t) {
        this.valor = valor;
        term = t;
        padre = null;
        derecho = null;
        izquierdo = null;
        hijo = null;
    }
    
  /*  public Nodo(String valor, boolean t, Nodo padre) {
        this.valor = valor;
        this.padre = padre;
        term = t;
        derecho = null;
        izquierdo = null;
        hijo = null;
    }
    
  /*  public Nodo(String valor, boolean t, Nodo padre, Nodo izq, Nodo der) {
        this.valor = valor;
        this.padre = padre;
        derecho = izq;
        izquierdo = der;
        term = t;
        hijo = null;
    }    
    
 /*   public static void ImprimirNivel(Nodo n){
    	if(n.izquierdo == null){
    		imp(n);
    	}else{
    		ImprimirNivel(n.izquierdo);
    	}
    }
    
    private static void imp(Nodo n){
    	if(n.term)
    		System.out.println(n.valor);
    	if(n.derecho != null)
    		imp(n.derecho);
    }
    */
  
    public String toString(){
    	String r = valor;
    	if(term){
    		r += valor;
    	}
    	/*if(hijo != null){
    		r += " - "+hijo.toString();
    	}
    	if(derecho != null){
    		r += " - "+derecho.toString();
    	}*/
    	return r;
    }
}