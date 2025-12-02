package com.matiasaravena.sistemareportes.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;

public class RegistroActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		EditText txtNombre = findViewById(R.id.txtNombreReg);
		EditText txtEmail = findViewById(R.id.txtEmailReg);
		EditText txtPass = findViewById(R.id.txtPassReg);
		Button btnRegistrar = findViewById(R.id.btnRegistrar);
		
		btnRegistrar.setOnClickListener(v -> {
			String nombre = txtNombre.getText().toString();
			String email = txtEmail.getText().toString();
			String pass = txtPass.getText().toString();
			
			if(nombre.isEmpty() || email.isEmpty() || pass.isEmpty()){
				Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
			} else {
				registrarUsuario(nombre, email, pass);
			}
		});
	}
	
	private void registrarUsuario(String nombre, String email, String pass) {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getWritableDatabase();
		
		ContentValues registro = new ContentValues();
		registro.put("nombre", nombre);
		registro.put("email", email);
		registro.put("password", pass);
		
		// Guardamos en la tabla 'usuarios'
		long result = bd.insert("usuarios", null, registro);
		bd.close();
		
		if (result != -1) {
			Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show();
			finish(); // Volvemos al Login para que ingrese
		} else {
			Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
		}
	}
}
