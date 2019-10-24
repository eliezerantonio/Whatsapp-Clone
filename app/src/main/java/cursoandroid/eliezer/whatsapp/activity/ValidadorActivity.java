package cursoandroid.eliezer.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import cursoandroid.eliezer.whatsapp.Helper.Preferencias;
import cursoandroid.eliezer.whatsapp.R;

public class ValidadorActivity extends Activity {

    private EditText codigoValidacao;
    private Button validar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao =findViewById(R.id.edit_cod_validacao);
        validar =findViewById(R.id.bt_validar);

        SimpleMaskFormatter simpleMaskCodigoValidacao= new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(codigoValidacao,simpleMaskCodigoValidacao);
        codigoValidacao.addTextChangedListener(mascaraCodigoValidacao);


        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // recuperar dados das e do usuario;

                Preferencias preferencias = new Preferencias(ValidadorActivity.this);

                HashMap<String,String> usuario= preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado= codigoValidacao.getText().toString();

                if (tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token Validado", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ValidadorActivity.this, "Token  nao Validado", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}
