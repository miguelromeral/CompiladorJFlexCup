package javapack;

public class Arbol {
	public Nodo raiz;
	public Arbol(){
		raiz = null;
	}
	
	public static void unirHermanos(Nodo nd, Nodo ni){
		nd.derecho = ni;
		ni.izquierdo = nd;
	}
	
	public static void unirParientes(Nodo np, Nodo nh){
		np.hijo = nh;
		nh.padre = np;
	}
	
	public String toString(){
		return raiz.toString();
	}
}
