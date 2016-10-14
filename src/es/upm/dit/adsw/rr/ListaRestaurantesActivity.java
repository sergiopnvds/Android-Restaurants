package es.upm.dit.adsw.rr;




import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;

import es.upm.dit.adsw.rr.R;


/**
 * Maneja la lista de restaurantes.Incluye un menu con tres funcionalidades(Añadir,
 * Oredenar y Cargar)
 *
 * @author Sergio Penavades
 * @version 26/5/2012
 */
public class ListaRestaurantesActivity extends ListActivity{
	
	private ArrayAdapter<Restaurante> adaptador;
	private ListaRestauranteSingleton singletonSergio;	
	public static final String POSICION="posicion";
	private refrescaListaTask tareaAsyncTask;
	private ProgressBar progressBar;
	private double progresoDouble;
	private double sumando=0;
	private int progreso;
	private final int segundos=2000;
	private boolean UnaVez=true;
	String[] dePaso;
	int contador=0;
		
	/** 
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main3);
	    progressBar=(ProgressBar)findViewById(R.id.progressBar);
	    singletonSergio = ListaRestauranteSingleton.getInstancia();
	    adaptador = new ArrayAdapter<Restaurante>(this,android.R.layout.simple_list_item_1,singletonSergio.getList());
	    setListAdapter(adaptador);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 * android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
		
		Status status=tareaAsyncTask.getStatus();
		
		if (status==AsyncTask.Status.FINISHED){
			Log.d("","Compruebo el estado de ejecución de la lista: "+status);
			super.onListItemClick(l, v, position, id);
			Intent intent=new Intent(getApplication(),DetalleRestauranteActivity.class);
			intent.putExtra(POSICION, position);
			startActivity(intent);
		}
		else return;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
	
	 /** 
	  * (non-Javadoc)
	  *  
	  * @see android.app.Activity# onOptionsItemSelected(android.view.MenuItem)
	  */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    		case R.id.anadir:
    			Log.d("","Boton añadir resturante pulsdo");
    			startActivity(new Intent(this,DetalleRestauranteActivity.class));
	    		return true;
	    	
	    	case R.id.cargar:
	    		if(UnaVez){
	    		Log.d("","Boton cargar lista de resturantes pulsdo");
	    		tareaAsyncTask=new refrescaListaTask();
	    		tareaAsyncTask.execute(); 
	    		}
	    		 return true;
	    		 
	    	case R.id.ordenar:
	    		Log.d("","Boton ordenar lista de resturantes pulsdo");
	    		if(ListaRestauranteSingleton.getInstancia().getList().size()>0){
	    			sort(ListaRestauranteSingleton.getInstancia().getList());
	    			return true;
	    		} else{
	    			Log.d("","Boton ordenar truncado. No hay elementos en la lista");
	    			Toast toast=Toast.makeText( this,"LISTA VACIA",2000);
            		toast.show();
	    		}
	    }
	    return false;    	
    }
	
    /** 
     * (non-Javadoc) 
     * 
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume(){
    	super.onResume();
    	adaptador.notifyDataSetChanged();
    }
	
    /**
     *  Controla que la lista solo se cargue una vez.
     * (Evita duplicidades)
     */
   	private void permisoCargaLista(){
   		UnaVez=false;
   		Log.d("","Bloqueamos la carga de la lista para evitar duplicidades");
   	}
   	
    /**
     * Extra la linea el fichero de carga y la procesa segun su contenido.
     * 
     * @param linea linea del fichero de carga. 
     */
   	private void recuperaLinea(String linea){	
		if(linea.length()<6){
				Double numero = Double.parseDouble(linea);
				sumando=100/(numero);
				Log.d("","El indicador del numero de restaurantes del txt es: "+ numero);
		}
		else{
			String[] separa = linea.split(";");				String s1=separa[0];
			String s2=separa[1];
			String s3=separa[2];
			String s4=separa[3];
			if(s4.compareToIgnoreCase(" tradicional")!=0 && s4.compareToIgnoreCase(" internacional")!=0 && s4.compareToIgnoreCase(" comida rapida")!=0){
			}
			else{
				Restaurante restaurante=new Restaurante(s1,s2,s3,s4);
				ListaRestauranteSingleton.getInstancia().addRestaurante(restaurante);
				progresoDouble=progresoDouble+sumando;
				progreso=(int)progresoDouble;
				DecimalFormat df = new DecimalFormat("#.##");//acoto la traza a dos decimales∫
				Log.d("","progreso: "+ df.format(progresoDouble)+"%");
			}
		}
	}
   	
    /**
     * Genera la lista con los restaurantes ordenados.
     * LLama a el método recursivo quicksort.
     * 
     * @param listaDeRestaurantes lista que contiene los restarantes. 
     */
   	private void sort(ArrayList<Restaurante> listaDeRestaurantes) {
   			
   		dePaso =new String[ListaRestauranteSingleton.getInstancia().getList().size()];
   		Log.d("","Ordenamos la lista ");
   		for(Restaurante restauranante: listaDeRestaurantes){		
   			String res=restauranante.toStringCompleto();		
   			dePaso[contador]=res;
   			contador=contador+1;			
   		}
  
   		quicksort(dePaso, 0,dePaso.length);
   		listaDeRestaurantes.clear();
   		for(int i=0;i<dePaso.length;i++){
   	        String[] separa = dePaso[i].split("-");
   	        String s1=separa[0];
   			String s2=separa[1];
   			String s3=separa[2];
   			String s4=separa[3];
   			Restaurante restaurante=new Restaurante(s1,s2,s3,s4);
   	    	ListaRestauranteSingleton.getInstancia().addRestaurante(restaurante);	
   		}
   		contador=0;
   		adaptador.notifyDataSetChanged();
   	}
   	
    /**
     * Ordena el array de restaurante por orden alfabético.
     * 
     * @param datos array con los restaurantes en cadena
     * @param a número de la celda en la que empieza el array
     * @param z número de la celda con la quertima el array
     */
   	 private void quicksort(String[] datos, int a, int z) {
   		
   		 String pivote = datos[(a + z) / 2];
   		 int inf = a;
   		 int sup = z;
   		 while (inf < sup) {
   			 while (datos[inf].compareToIgnoreCase(pivote)<0)
   				 inf++;
   			 while (0 < datos[sup - 1].compareToIgnoreCase(pivote))
   				 sup--;
   			 if (inf < sup) {
   				 String tmp = datos[inf];
   				 datos[inf] = datos[sup - 1];
   				 datos[sup - 1] = tmp;
   				 inf++;
   				 sup--;
   			 }
   		 }
   		 if (a < sup){
   			 quicksort(datos, a, sup);
   		 }
   		 if (inf < z){
   			 quicksort(datos, inf, z);
   		 }
   	 }
   	 
   	/**
      * Tarea de carga de la lista en segundo plano.
      * 
      * @author Sergio Penavades
      * @version 26/5/2012
      */
     public class refrescaListaTask extends AsyncTask<Void, Integer, Void> {
    	 
     /**
      * (non-Javadoc)
      * 
      * @see android.os.AsyncTask#onPreExecute()
      */
  	   @Override
  	   protected void onPreExecute(){
  		   super.onPreExecute();
  		   progressBar.setVisibility(View.VISIBLE);
  		   progreso=0; 
  	   }
  		
  	  /**
  	   *  (non-Javadoc)
  	   *  
  	   * @see android.os.AsyncTask#doInBackground(Params[])
  	   */
  	   @Override
  		protected Void doInBackground(Void... unused){	
  			AssetManager am = getResources().getAssets();
  			InputStream entrada = null;
  			
  			try {
  				entrada = am.open("restaurantes.txt");
  				InputStreamReader ir = new InputStreamReader(entrada);	
  				BufferedReader bf = new BufferedReader(ir);
  				String linea = bf.readLine();		
  					
  				while (linea != null) {
  					recuperaLinea(linea); 					
  					publishProgress(progreso);
  					linea = bf.readLine();
  					
  					try {
  						Thread.sleep(segundos);
  					} catch (InterruptedException e) {
  						// TODO Auto-generated catch block
  						e.printStackTrace();
  					}	
  				}
  				entrada.close();
  			   
  			} catch (IOException e) {
  					Log.d("ListaRestaurantes", "Imposible abrir el fichero");
  			} 
  			permisoCargaLista();
  			Log.d("","Permiso para cargar la lista cancelado para evitar duplic ");
  			return null;	
  		}
  		
  	   /**
  	    * (non-Javadoc)
  	    * 
  	    * @see android.os.AsyncTask#onProgressUpdate(Progress[])
  	    */
  		@Override 
  		protected void onProgressUpdate(Integer... progress){
  			super.onProgressUpdate(progress[0]);
  			adaptador.notifyDataSetChanged();
  			progressBar.setProgress(progress[0]);
  		}
  		
  		/**
  		 * (non-Javadoc)
  		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
  		@Override
  		protected void onPostExecute(Void unused){
  			progressBar.setVisibility(View.INVISIBLE);
  			Log.d("","desaparece la barra de progreso");
  			
  		} 		
     }
}
