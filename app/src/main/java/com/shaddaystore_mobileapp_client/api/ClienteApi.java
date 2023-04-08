package com.shaddaystore_mobileapp_client.api;

import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {

    String base = "api/cliente";
    @POST(base)
    Call<GenericResponse<Cliente>> guardarCliente(@Body Cliente c);
}
