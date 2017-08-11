package com.example.justgo.LogineCadastro;


        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;
        import java.lang.reflect.Method;
        import java.util.HashMap;
        import java.util.Map;


public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String nome, String email,String senha, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nomeEntrada", nome);
        params.put("emailEntrada", email);
        params.put("senhaEntrada", senha);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}