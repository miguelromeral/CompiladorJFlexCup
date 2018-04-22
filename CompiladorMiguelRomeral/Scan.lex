package javapack;

import java.io.*;
import java.util.*;
import java_cup.runtime.*;
import javapack.Sym;
import static javapack.Sym.*;
import java.nio.file.*;

//DEFINICIoN DEL BLOQUE DE CONFIGURACIoN DEL ANALIZADOR
%%

%class Scan
%unicode
%line
%column
%cup

//En el constructor, creamos una lista de tokens nueva.
//Ademas, empezaremos a contar las lineas y caracteres desde 1.
%init{
	yyline = 1; 
   	yycolumn = 1; 
    this.tokensList = new ArrayList<String>();
%init}


//CoDIGO DE USUARIO
%{
	//Ruta del archivo que esta analizando.
	private String path;
	//Lista de tokens dinamica. Servira para imprimirla al usuario
	private ArrayList<String> tokensList;
	//Analizador sintactico generado en cup.
	//Lo guardamos en el lexico para que pueda enviar errores, puesto que sera Parser quien los muestre.
	private Parser p;
	
	//Anadimos el parser al analizador lexico.
	public void ponerParser(Parser pa){
		this.p = pa;
	}

	//Anadimos la ruta del archivo que vamos a analizar.
	public void ponerArchivo(String in){
		path = in;
	}

	//Muestra el listado de tokens analizados por el lexico.
	private void muestraLexico() throws IOException {
			System.out.println(
		"-------------------------------------------------------------------------------------------------------------------------------------------------");		
		 	System.out.println("Analisis lexico completado.\n");
			System.out.println(this.tokensList.toString()+"\n");
	}
	
	//Llama al compilador para que muestre por pantalla la linea exacta en la que ocurrio el error.
    public String fallo(int linea, int columna){
    	return Compilador.mostrarFallo(path, linea, columna);
    }
    
    //Anade un nuevo error de tipo lexico al analizador sintactico.
    public void mal(String mensaje) throws FileNotFoundException, IOException{
    	String err = "(LeXICO) "+mensaje+" en linea "+yyline+", columna "+yycolumn+"\n"+fallo(yyline, yycolumn);
    	p.newError(err);
    }
    
    //Anade un nuevo token a la lista de tokens.
    public void nuevoToken(String token){
    	this.tokensList.add(token);
    }
    
    //Devuelve el numero del simbolo que representa (Creado por el Parser) el texto leido.
    public int operadorMatematico(String operador){
    	int s = 0;
    	switch(operador){
	    	case "+":	nuevoToken("mas");
	    				s = Sym.tk_suma;
	    				break;
	    	case "-":	nuevoToken("menos");
	    				s = Sym.tk_resta;
	    				break;
	    	case "*":	nuevoToken("por");
	    				s = Sym.tk_multiplicacion;
	    				break;
	    	case "/":	nuevoToken("entre");
	    				s =  Sym.tk_division;
	    				break;
	    	default:	break;
    	}
    	return s;
    }
    
    //Devulve el numero de simbolo perteneciente a cualquiera de los siguientes operadores-punto.
    public int operadorPunto(String op){
    	int s = 0;
    	switch(op){
	    	case "(":	nuevoToken("paren_izq");
	    				s = Sym.tk_parenizq;
	    				break;
	    	case ")":	nuevoToken("paren_der");
	    				s = Sym.tk_parender;
	    				break;
	    
	    	case ",":	nuevoToken("coma");
	    				s = Sym.tk_coma;
	    				break;
	    	case ";":	nuevoToken("punto_coma");
	    				s = Sym.tk_puntoycoma;
	    				break;
	    	case ":":	nuevoToken("dos_puntos");
	    				s = Sym.tk_dospuntos;
	    				break;
	    	default:	break;
    	}
    	return s;
    }
    
    //Devuelve el texto perteneciente al tipo de comparacion que se esta realizando.
    //El nombre del token sera para todos "comp"
    public String operadorComparacion(String op){
    	String s = "nondefined";
    	switch(op){
	    	case "<":	s = "menor";
	    				break;
	    	case "<=":	s = "menorigual";
	    				break;
	    	case "=":	s = "igual";
	    				break;
	    	case ">=":	s = "mayorigual";
	    				break;
	    	case ">":	s = "mayor";
	    				break;
	    	default:	break;
    	}
    	return s;
    }
    
    //Devuelve el numero de simbolo perteneciente a cada identificador.
    //Si el identificador coincide con una de las palabras reservadas, devuelve le token correspondiente a esa palabra reservada.
    //Ademas, anade el token a la lista de tokens.
    public int tratarIDE(String res){
    	int s = Sym.tk_identificador;
    	res = res.toLowerCase();
    	switch(res){
	    	case "var":			s = Sym.tk_var; nuevoToken("var"); break;
	    	case "integer":		s = Sym.tk_integer; nuevoToken("integer"); break;
	    	case "true":		s = Sym.tk_true; nuevoToken("true"); break;
	    	case "false":		s = Sym.tk_false; nuevoToken("false"); break;
	    	case "boolean":		s = Sym.tk_boolean; nuevoToken("boolean"); break;
	    	case "then":		s = Sym.tk_then; nuevoToken("then"); break;
	    	case "program":		s = Sym.tk_program; nuevoToken("program"); break;
	    	case "is":			s = Sym.tk_is; nuevoToken("is"); break;
	    	case "begin":		s = Sym.tk_begin; nuevoToken("begin"); break;
	    	case "end":			s = Sym.tk_end; nuevoToken("end"); break;
	    	case "read":		s = Sym.tk_read; nuevoToken("read"); break;
	    	case "write":		s = Sym.tk_write; nuevoToken("write"); break;
	    	case "skip":		s = Sym.tk_skip; nuevoToken("skip"); break;
	    	case "while":		s = Sym.tk_while; nuevoToken("while"); break;
	    	case "do":			s = Sym.tk_do; nuevoToken("do"); break;
	    	case "if":			s = Sym.tk_if; nuevoToken("if"); break;
	    	case "else":		s = Sym.tk_else; nuevoToken("else"); break;
	    	case "and":			s = Sym.tk_and; nuevoToken("and"); break;
	    	case "or":			s = Sym.tk_or; nuevoToken("or"); break;
	    	case "not":			s = Sym.tk_not; nuevoToken("not"); break;
	    	default: 			nuevoToken("ide("+res+")"); break;
    	}
    	return s;
    }
%}
//Tratamiento del analizador al encontrarse con el final del archivo.
//Mostrara la lista de tokens analizados.
%eofval{
	muestraLexico();
	return new Symbol(Sym.EOF);
%eofval}

//REGLAS LEXICOGRaFICAS, EXPRESIONES REGULARES

Letra = [a-zA-Z]
Numero = [0-9]
Asignacion = :=
Comparacion = ((<|>)?=) | (<|>)
OperadorMatematico = \+ | - | \* | \/
Puntuacion = \( | \) | , | ; | :
IDE = {Letra} ({Letra}|{Numero})*
INTEGER = (\+ | \-)?[0-9]+
NoHacerNada = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085

 /* Expresiones regulares pertenecientes al no tratamiento del texto en los comentarios. */
 //Sacadas del propio manual de JFlex: http://jflex.de/manual.html
Comentario = {ComentarioTradicional} | {ComentarioFinLinea} | {ComentarioDocumentacion}
ComentarioTradicional   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
ComentarioFinLinea     = "//" [^\r\n]* (\r|\n|\r\n)?
ComentarioDocumentacion = "/**" {ContenidoComentario} "*"+ "/"
ContenidoComentario       = ( [^*] | \*+ [^/*] )*
NoReconocido = [^AsignacionComparacionOperadorMatematicoPuntuacionwINTEGERIDEComentarioNoHacerNada]

//En este programa no habra estados que definir ni transiciones entre ellos
%% 

//Acciones al encontrarse con las diferentes cadenas pertenecientes a las expresiones regulares.
[ \t]							{/* Nada */}
{Asignacion}			{nuevoToken("asign"); return new Symbol(Sym.tk_asignacion, yyline, yycolumn, yytext());}
{OperadorMatematico}	{return new Symbol(operadorMatematico(yytext()), yyline, yycolumn, yytext());}
{Comparacion}			{nuevoToken("comp");return new Symbol(Sym.tk_comparacion, yyline, yycolumn, operadorComparacion(yytext()));}
{Puntuacion}			{return new Symbol(operadorPunto(yytext()), yyline, yycolumn, yytext());}
{INTEGER}				{nuevoToken("num("+yytext()+")"); return new Symbol(Sym.tk_numeroentero, yyline, yycolumn, new Integer(yytext()));}
{IDE}					{
							int sim = tratarIDE(yytext());
							if(sim == (Sym.tk_identificador))
								return new Symbol(sim, yyline, yycolumn, new Simbolo(yytext(), yyline, yycolumn));
							else
								return new Symbol(sim, yyline, yycolumn, yytext());
						}
{Comentario}			{ /* Nada */ }
{NoReconocido}			{mal("lexema no reconocido");}
{NoHacerNada}			{ /* Regla lexica para evitar estrellarse cuando y se encuentra EOL, en realidad, no hacemos nada */}           
