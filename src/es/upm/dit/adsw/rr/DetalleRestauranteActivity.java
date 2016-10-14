package es.upm.dit.adsw.rr;


import es.upm.dit.adsw.rr.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


/**
 * Clase que nos lanza la actividad DestalLeRestaurante. Esta nos da la posibilidad de visualizar 
 * o modificar los detalles del restaurante seleccionado y en el caso de que queramos añadir uno nuevo 
 * nos abre un restaurante con los paremetro vacios para que lo implementemos.
 * 
 *
 * @author Sergio Penavades
 * @version 26/5/2012
 */
public class DetalleRestauranteActivity extends Activity {
	
	private EditText et1,et2,et3;
	private RadioButton rb1,rb2,rb3;
	String tipo;
	
	/**
     * Es llamado cuando la actividad es creada por primera vez.
     * 
     * Obtenemos los datos que vamos a manejar en el layout mediante la posición del restaurate
     * en el arrayList y los asignamos a las variables de trabajo para así poder utilizarlas.
     *
     */
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et1=(EditText)findViewById(R.id.textoRestaurante);
        et2=(EditText)findViewById(R.id.textoDireccion);
        et3=(EditText)findViewById(R.id.textoTelefono);
        rb1=(RadioButton)findViewById(R.id.seleccionaTradicional);
        rb2=(RadioButton)findViewById(R.id.seleccionaInternacional);
        rb3=(RadioButton)findViewById(R.id.seleccionaComidaRapida);
        Bundle extras=getIntent().getExtras();
        
        if (extras!=null){
        	int posicion=extras.getInt(ListaRestaurantesActivity.POSICION);
        	Restaurante restaurante=ListaRestauranteSingleton.getInstancia().getList().get(posicion);
        	et1.setText(restaurante.getRestaurant());
        	et2.setText(restaurante.getDireccion());
        	et3.setText(restaurante.getTelefono());
        	String et4=restaurante.getTipo();
        	
        	if(et4.compareToIgnoreCase(" tradicional")==0){
        		rb1.setChecked(true);
        	} else {
        		if(et4.compareToIgnoreCase(" internacional")==0){
        			rb2.setChecked(true);
        		} else {
        			if(et4.compareToIgnoreCase(" comida rapida")==0){
        				rb3.setChecked(true);
        			} else{
        				Toast toast=Toast.makeText( this,"No se encuentra el tipo de Restaurante",2000);
                		toast.show();
                		rb1.setChecked(false);	
        			}
        		}
        	}
        } 
    }
    
    /**
     * Crea o modifica un restaurante y lo agraga a la lista bien en su posicion o en una nueva.
     * Cuando termina pone todos sus campos vacios y vuleve a la actividad proncipal.
     */
    public void creaRestaurante(View v){

    	String n1=et1.getText().toString();
    	if (n1.equals("")){
    		Toast toast=Toast.makeText( this,"Introduzca el nombre del restaurante por favor",2000);
       		toast.show();
       		return;
    	}
    	
   		String n2=et2.getText().toString();
   		if (n2.equals("")){
   			Toast toast=Toast.makeText( this,"Introduzca la dirección del Restaurante por favor",2000);
       		toast.show();        		return;
    	}
    	
    	String n3=et3.getText().toString();
   		if (n3.equals("")){
   			Toast toast=Toast.makeText( this,"Introduzca el número de telefono del Restaurante por favor",2000);
       		toast.show();
        		return;
    		}
    		
    	if(rb1.isChecked()==true){
    		tipo=" tradicional";
    	} else {
    		if(rb2.isChecked()==true){
    			tipo=" internacional";
    		} else {
    			if(rb3.isChecked()==true){
    				tipo=" comida rapida";
    			} else{
    				Toast toast=Toast.makeText( this,"Seleccione un tipo de Restaurante",2000);
            		toast.show();
            		return;			
    			}
    		}
    	}
    	if(getIntent().getExtras()==null){
    		Restaurante restaurante=new Restaurante(n1,n2,n3,tipo);
    		ListaRestauranteSingleton.getInstancia().addRestaurante(restaurante);
    		Log.d("","Añado restaurante nuevo: "+restaurante.toStringCompleto());
    	} else{
    		int posicion=getIntent().getExtras().getInt(ListaRestaurantesActivity.POSICION);
    		Restaurante restaurante=new Restaurante(n1,n2,n3,tipo);
    		ListaRestauranteSingleton.getInstancia().modificarRestaurante(posicion, restaurante);
    		Log.d("","Modifico restaurante: "+restaurante.toStringCompleto());
    	}
    	et1.setText(null);
    	et2.setText(null);
    	et3.setText(null);
    	//Todos los radio-botones a false para que no estén señalados.
    	rb1.setChecked(false);
    	rb2.setChecked(false);
    	rb3.setChecked(false);
    	onBackPressed();
    }
    
    /**
     * Vuelve a la actividad principal sin realizar ningun cambio.
     */
    public void salir(View v){
    	onBackPressed();
    	Log.d("","Salir de detaleRestaurante sin realizar cambios");
    }
    
  
}