package com.shaddaystore_mobileapp_client.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.api.UsuarioApi;
import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static UsuarioRepository getInstance(){
        if(repository == null){
            repository = new UsuarioRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String contrasenia){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.login(email, contrasenia).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se produjo un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }



    public LiveData<GenericResponse<Usuario>> save (Usuario u){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.save(u).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se produjo un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Usuario>> recovery(String email){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.recovery(email).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se produjo un error al recuperar: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
