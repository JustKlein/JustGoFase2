package com.example.justgo.LogineCadastro;

        import android.app.AlertDialog;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;
        import com.example.justgo.R;
        import com.example.justgo.Requests.RegisterRequest;

        import org.json.JSONException;
        import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       final EditText nomeEditText = (EditText) findViewById(R.id.nomeCadastro);
        final EditText emailEditText = (EditText) findViewById(R.id.emailCadastro);
        final EditText senhaEditText = (EditText) findViewById(R.id.senhaCadastro);
        final Button bRegister = (Button) findViewById(R.id.botaoCadastrar);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = nomeEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String senha = senhaEditText.getText().toString();
                if(!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() && email.contentEquals("@")) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Usuário cadastrado com sucesso")
                                            .setNegativeButton("Ok", null)
                                            .create()
                                            .show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Erro ao cadastrar usuário")
                                            .setNegativeButton("Tentar novamente", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(nome, email, senha, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });

    }
}