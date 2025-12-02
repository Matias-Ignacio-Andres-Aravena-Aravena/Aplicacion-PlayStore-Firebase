package com.matiasaravena.sistemareportes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;

public class PerfilUsuarioActivity extends AppCompatActivity {
	
	TextView lblNombre, lblEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_usuario);
		
		lblNombre = findViewById(R.id.lblNombrePerfil);
		lblEmail = findViewById(R.id.lblEmailPerfil);
		Button btnLogout = findViewById(R.id.btnCerrarSesion);
		
		// 1. Cargamos los datos reales del usuario
		cargarDatosUsuario();
		
		btnLogout.setOnClickListener(v -> {
			// Al cerrar sesión, borramos la memoria
			SharedPreferences preferencias = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferencias.edit();
			editor.clear();
			editor.apply();
			
			Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});
	}
	
	private void cargarDatosUsuario() {
		// 1. Recuperamos el correo de la sesión guardada
		SharedPreferences preferencias = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
		String emailGuardado = preferencias.getString("email_activo", "No encontrado");
		
		// 2. Buscamos en la BD los datos de ese correo
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getReadableDatabase();
		
		Cursor fila = bd.rawQuery("SELECT nombre, email FROM usuarios WHERE email='" + emailGuardado + "'", null);
		
		if (fila.moveToFirst()) {
			// Si lo encontramos, ponemos los datos en los TextView
			lblNombre.setText(fila.getString(0)); // Nombre real
			lblEmail.setText(fila.getString(1));  // Correo real
		} else {
			lblNombre.setText("Usuario Desconocido");
			lblEmail.setText("-");
		}
		
		fila.close();
		bd.close();
	}
}
