package com.matiasaravena.sistemareportes.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;

public class CamaraCustomActivity extends AppCompatActivity {
	
	private ImageView imgPreview;
	private static final int REQUEST_IMAGE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camara_custom);
		
		imgPreview = findViewById(R.id.imgFullPreview);
		Button btnCamara = findViewById(R.id.btnTomarFotoCustom);
		
		btnCamara.setOnClickListener(v -> {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, REQUEST_IMAGE);
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
			Bitmap foto = (Bitmap) data.getExtras().get("data");
			imgPreview.setImageBitmap(foto);
		}
	}
}