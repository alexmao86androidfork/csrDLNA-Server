package cl.zeteginea.csrdlnaserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button boton2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        boton2 = (Button) findViewById(R.id.listado);
        boton2.setText("Cargar Listado");
        boton2.setOnClickListener(new OnClickListener() {  
        	public void onClick(View v) { 
        		
        		Intent intent = new Intent(v.getContext(), VideoList.class);
        		//EditText mEdit = (EditText)findViewById(R.id.editText1);
        		//intent.putExtra("MENSAJE", mEdit.getText().toString());
        		startActivity(intent);
        	}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
