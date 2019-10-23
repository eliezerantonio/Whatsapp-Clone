package cursoandroid.eliezer.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import cursoandroid.eliezer.whatsapp.Helper.Permisao;
import cursoandroid.eliezer.whatsapp.Helper.Preferencias;
import cursoandroid.eliezer.whatsapp.R;

public class LoginActivity extends Activity {
    private EditText telefone;
    private EditText nome;
    private Button botaoCadastrar;
    private EditText codigoPais;

    private String[] permisoesNecesssarias = new String[]{
         Manifest.permission_group.SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permisao.validaPermissoes(1, LoginActivity.this, permisoesNecesssarias);

        telefone = findViewById(R.id.edit_telefone);
        nome = findViewById(R.id.edit_nome);
        botaoCadastrar = findViewById(R.id.bt_cadastrar);
        codigoPais = findViewById(R.id.edit_cod_pais);


//Definir mascara
        SimpleMaskFormatter simpleMaskCodigoPais = new SimpleMaskFormatter("+NNN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNN-NNN-NNN");

        MaskTextWatcher maskCodigoPais = new MaskTextWatcher(codigoPais, simpleMaskCodigoPais);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        codigoPais.addTextChangedListener(maskCodigoPais);
        telefone.addTextChangedListener(maskTelefone);//responsavel por formatar a caixa de texto

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (
                        nome.getText().toString().isEmpty() ||
                                telefone.getText().toString().isEmpty() ||
                                codigoPais.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Deve preencher os Campos", Toast.LENGTH_SHORT).show();

                } else {
                    String nomeUsuario = nome.getText().toString();
                    String telefoneCompleto = codigoPais.getText().toString() + "  " + telefone.getText().toString();

                    //substituicao de String

                    // telefoneCompleto="8135"
                    String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                    telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                    //gerar token

                    Random randomico = new Random();
                    int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;

                    String token = String.valueOf(numeroRandomico);
                    String mensagemEnvio = "WhatsApp Código de Confirmação: " + token;


                    //salvar dados para validacao

                    // Preferencias preferencias = new Preferencias(getApplication());

                    Preferencias preferencias = new Preferencias(LoginActivity.this);

                    preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                    HashMap<String, String> usuario = preferencias.getDadosUsuario();

                    //Envio do SMS

                    boolean enviodoSms = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);


                }


            }
        });


    }

    private boolean enviaSMS(String telefone, String mensagem) {


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    // metodo vai se manifestar quando o o usuario negar  a permisao
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults) {

            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }

        }

    }

    private void alertaValidacaoPermissao() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
