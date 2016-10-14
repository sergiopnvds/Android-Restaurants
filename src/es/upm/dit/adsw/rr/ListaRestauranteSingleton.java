package es.upm.dit.adsw.rr;

import java.util.ArrayList;


import android.util.Log;

/**
 * Clase Singleton que gestiona el acceso a la lista de restaurantes. De manera que solo
 * puede acceder a ella quien obtiene el permiso.
 *
 * @author Sergio Penavades
 * @version 26/5/2012
 */
public class ListaRestauranteSingleton {
	
	private static ListaRestauranteSingleton instancia=null;
	ArrayList<Restaurante> listaDeRestaurantes;
	
	/**
     * Constructor.Me crea un lista de restaurantes.
     *
     * 
     */
	private ListaRestauranteSingleton() {
		listaDeRestaurantes=new ArrayList<Restaurante>();
	}
	
	 /**
     * Getter.
     *
     * @return la instancia que permite el acceso a la lista de restaurantes.
     */
	public static synchronized ListaRestauranteSingleton getInstancia(){
		Log.d("", "tarea accediendo a lista mediante Singleton");
		if(instancia==null){
			instancia=new ListaRestauranteSingleton();
		}
		return instancia;
	}
	
	 /**
     * Getter.
     *
     * @return devuelve la lista de restaurantes.
     */
	public ArrayList<Restaurante> getList(){
		return listaDeRestaurantes;
	}
	
	/**
     * Agrega un restaurante a la lista.
     *
     * @param restaurante restaurante a agregar.
     */
	public void addRestaurante(Restaurante restaurante){
		listaDeRestaurantes.add(restaurante);
		
	}
	
	/**
     * Modifica el restaurante de la posion dada en la lista.
     *
     * @param posicion posicion en la que metemos al restaurante.
     * @param restaurante restaurante que metemos en la lista.
     */
	public void modificarRestaurante(int posicion,Restaurante restaurante){
		
		listaDeRestaurantes.set(posicion,restaurante);	
	}
}
