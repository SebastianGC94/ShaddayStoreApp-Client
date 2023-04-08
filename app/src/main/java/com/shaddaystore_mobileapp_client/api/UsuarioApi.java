package com.shaddaystore_mobileapp_client.api;

import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioApi {
    //RUTA DEL CONTROLADOR USUARIO
    String base = "api/usuario";

    //RUTA DEL CONTROLADOR USUARIO + LA RUTA DEL MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("email") String email, @Field("pass") String contrasenia);


    @FormUrlEncoded
    @POST(base + "/recovery")
    Call<GenericResponse<Usuario>> recovery(@Field("email") String email);

    @POST(base)
    Call<GenericResponse<Usuario>> save (@Body Usuario u);


}
