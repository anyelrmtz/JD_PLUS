package com.example.jdplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtContrasena;
    Button boton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuario = findViewById(R.id.usuario);
        edtContrasena =findViewById(R.id.contrasena);
        boton = findViewById(R.id.boton);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggin();
            }
        });

    }

    public Connection conexionBD(){
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.100.65;DatabaseName=junodoctor_cliente;user=DOCTORJUNIO;PASSWORD=junodoctor2020");


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }

    public void loggin (){
        try {
            Statement stm = conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("select usuario, contra from usuario where usuario = '"+  edtUsuario.getText().toString() +"' AND contra ='"+ edtContrasena.getText().toString()+"'");
            if(rs.next()){
                Intent intent = new Intent(this, barraNavegacion.class);
                startActivityForResult(intent,0);
            }
            else{
                Toast.makeText(getApplicationContext(),"Datos incorrectos",Toast.LENGTH_SHORT).show();
            }



        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();;
        }
    }

}