package com.matiasaravena.sistemareportes.network;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import java.io.IOException;

public class MediaHelper {
	
	private MediaRecorder recorder;
	private MediaPlayer player;
	
	// Iniciar Grabación
	public void grabarAudio(String path) {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(path);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try {
			recorder.prepare();
			recorder.start();
		} catch (IOException e) {
			Log.e("MediaHelper", "Fallo grabación", e);
		}
	}
	
	// Detener Grabación
	public void detenerGrabacion() {
		if (recorder != null) {
			recorder.stop();
			recorder.release();
			recorder = null;
		}
	}
	
	// Reproducir Audio
	public void reproducirAudio(String path) {
		player = new MediaPlayer();
		try {
			player.setDataSource(path);
			player.prepare();
			player.start();
		} catch (IOException e) {
			Log.e("MediaHelper", "Fallo reproducción", e);
		}
	}
	
	// Limpiar memoria
	public void liberarRecursos() {
		if (player != null) {
			player.release();
			player = null;
		}
		detenerGrabacion();
	}
}