package com.jesusn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtuser;
    EditText edtPassword;
    Button btn_login;
    String usuario,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtuser = findViewById(R.id.edtUsuario);

        edtPassword = findViewById(R.id.edtPassword);
        btn_login = findViewById(R.id.btnLogin);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = edtuser.getText().toString();
                password = edtPassword.getText().toString();
                if (!usuario.isEmpty() && !password.isEmpty()) {


                    validarUsuario("http://proyecto1.atspace.cc/validar_usuario.php");
                }
                else{
                    Toast.makeText(MainActivity.this,"No se permiten campos vacios",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void validarUsuario (String URL){
        StringRequest StringRequest = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),PrincipalActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(MainActivity.this,"Usuario o Contraseña incorrecta",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        })   {
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",edtuser.getText().toString());
                parametros.put("password",edtPassword.getText().toString());
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


}