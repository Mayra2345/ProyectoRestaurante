// ----------- menu de la aplicacion -------------
package com.itl.feedback.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button siguiente2;
    Button siguiente3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        siguiente2 = (Button)findViewById(R.id.button7);

        siguiente3 = (Button)findViewById(R.id.button6);



        siguiente2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent siguiente2 =  new Intent(Main2Activity.this , HistoriaMain3Activity.class);
                startActivity(siguiente2);

            }
        });

        siguiente3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent siguiente3 =  new Intent(Main2Activity.this , OrdenarMain3.class);
                startActivity(siguiente3);

            }
        });
    }
}
