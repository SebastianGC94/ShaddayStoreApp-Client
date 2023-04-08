package com.shaddaystore_mobileapp_client.api;

import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoApi {
    String base = "api/producto";

    @GET(base)
    Call<GenericResponse<List<Producto>>> listarProductosRecomendados();

    @GET(base + "/{idC}")
    Call<GenericResponse<List<Producto>>> listarProductosPorCategoria(@Path("idC") int idC);
}
