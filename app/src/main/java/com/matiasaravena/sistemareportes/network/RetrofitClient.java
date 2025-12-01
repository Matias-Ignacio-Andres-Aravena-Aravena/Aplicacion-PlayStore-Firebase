package com.matiasaravena.sistemareportes.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
	
	private static Retrofit retrofit = null;
	// NOTA: 10.0.2.2 es la dirección "localhost" del emulador de Android.
	// Si usas tu celular real por USB, aquí iría la IP de tu PC (ej: "http://192.168.1.15:8000/api/")
	private static final String BASE_URL = "http://10.0.2.2:8000/api/";
	
	public static ApiService getApiService() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit.create(ApiService.class);
	}
}