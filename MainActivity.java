// ----------- menu de la aplicacion -------------
package com.itl.feedback.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button siguiente;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siguiente = (Button)findViewById(R.id.button1);

        siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent siguiente =  new Intent(MainActivity.this , Main2Activity.class);
                startActivity(siguiente);

            }
        });
    }

}
