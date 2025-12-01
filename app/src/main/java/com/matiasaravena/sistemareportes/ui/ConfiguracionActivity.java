package com.matiasaravena.sistemareportes.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;

public class ConfiguracionActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		
		Button btnGuardar = findViewById(R.id.btnGuardarConfig);
		Switch swNoti = findViewById(R.id.swNotificaciones);
		
		btnGuardar.setOnClickListener(v -> {
			String estado = swNoti.isChecked() ? "ACTIVADAS" : "DESACTIVADAS";
			Toast.makeText(this, "Preferencias guardadas: " + estado, Toast.LENGTH_SHORT).show();
			finish();
		});
	}
}