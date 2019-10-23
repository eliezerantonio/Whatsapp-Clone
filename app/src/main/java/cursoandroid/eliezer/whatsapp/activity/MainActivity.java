package cursoandroid.eliezer.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cursoandroid.eliezer.whatsapp.R;

public class MainActivity extends Activity {
    private DatabaseReference referenciaFireBase = FirebaseDatabase.getInstance().getReference();
    private Button botaoIr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // referenciaFireBase.child("pontos").setValue(100);
        botaoIr.findViewById(R.id.button);
        botaoIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
