package cursoandroid.eliezer.whatsapp.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cursoandroid.eliezer.whatsapp.R;

public class MainActivity extends Activity {
    private DatabaseReference referenciaFireBase = FirebaseDatabase.getInstance().getReference();
private Button botao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  referenciaFireBase.child("pontos").setValue(200);

       botao= findViewById(R.id.irPara);

   botao.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           //startActivity(new Intent(MainActivity.this, ValidadorActivity.class));
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
       }
   });


    }
}
