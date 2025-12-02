package com.matiasaravena.sistemareportes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;

public class LoginActivity extends AppCompatActivity {
	
	EditText txtEmail, txtPass;
	Button btnLogin;
	TextView btnIrRegistro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		txtEmail = findViewById(R.id.txtEmail);
		txtPass = findViewById(R.id.txtPass);
		btnLogin = findViewById(R.id.btnLogin);
		btnIrRegistro = findViewById(R.id.btnIrRegistro);
		
		btnLogin.setOnClickListener(v -> validarLogin());
		
		btnIrRegistro.setOnClickListener(v ->
				startActivity(new Intent(LoginActivity.this, RegistroActivity.class))
		);
	}
	
	private void validarLogin() {
		String email = txtEmail.getText().toString();
		String pass = txtPass.getText().toString();
		
		if (email.isEmpty() || pass.isEmpty()) {
			Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getReadableDatabase();
		
		// Buscamos si el usuario existe
		Cursor fila = bd.rawQuery(
				"SELECT nombre FROM usuarios WHERE email='" + email + "' AND password='" + pass + "'",
				null
		);
		
		if (fila.moveToFirst()) {
			String nombreUsuario = fila.getString(0);
			
			// --- GUARDAMOS LA SESIÓN ---
			SharedPreferences preferencias = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferencias.edit();
			editor.putString("email_activo", email); // Guardamos el correo para usarlo en el perfil
			editor.apply();
			// -----------------------------------------------
			
			Toast.makeText(this, "Bienvenido " + nombreUsuario, Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
		}
		
		fila.close();
		bd.close();
	}
}
