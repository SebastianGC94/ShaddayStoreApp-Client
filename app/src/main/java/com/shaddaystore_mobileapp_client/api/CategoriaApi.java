package com.shaddaystore_mobileapp_client.api;

import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
