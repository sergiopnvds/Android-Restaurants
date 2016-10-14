package es.upm.dit.adsw.rr;

/**
 * Un restaurante de la lista.
 *
 * @author Sergio Penavades
 * @version 26/5/2012
 */

public class Restaurante {
	
	String restaurant;
	String direccion;
	String telefono;
	String tipo;
	
	/**
     * Constructor.
     *
     * @param restaurant nombre del Restaurante.
     * @param direccion direccion del Restaurante.
     * @param telefono numero de telefono del Restaurante.
     * @param tipo clase de comida que sirve el Restaurante(tradicional,internacional,comida rapida).
     */
	public Restaurante(String restaurant,String direccion,String telefono,String tipo){
		
		this.restaurant=restaurant;
		this.direccion=direccion;
		this.telefono=telefono;
		this.tipo=tipo;
	}
	
	 /**
     * Getter.
     *
     * @return nombre del Restaurante.
     */
	public String getRestaurant() {
		return restaurant;
	}
	
	/**
     * Setter.
     *
     * @param restaurant nombre del Restaurante.
     */
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	
	 /**
     * Getter.
     *
     * @return direccion del Restaurante.
     */
	public String getDireccion() {
		return direccion;
	}

	/**
     * Setter.
     *
     * @param direccion direccion del Restaurante.
     */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	 /**
     * Getter.
     *
     * @return telefono del Restaurante.
     */
	public String getTelefono() {
		return telefono;
	}

	/**
     * Setter.
     *
     * @param telefono numero de telefono del Restaurante.
     */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	 /**
     * Getter.
     *
     * @return tipo de restaurante(tradiocional,internacional,comida rapida).
     */
	public String getTipo() {
		return tipo;
	}
	
	/**
     * Setter.
     *
     * @param tipo tipo clase de comida que sirve el Restaurante(tradicional,internacional,comida rapida).
     */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
     * Para imprimir todos los parametos del Restaurante.
     *
     * @return mensaje con el Restaurante a imprimir.
     */
	public String toStringCompleto(){
		return getRestaurant() + "-" + getDireccion() + "-" + getTelefono() + "-"+ getTipo();
	}
	
	/**
     * Para imprimir el nombre y direccion de Restaurante.
     *
     * @return mensaje con el Restaurante a imprimir.
     */
	public String toString(){
		return getRestaurant() + "-" + getDireccion();
	}

}
