package com.shaddaystore_mobileapp_client.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.shaddaystore_mobileapp_client.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {
    private final ProductoRepository repository;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        repository = ProductoRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Producto>>> listarProductosRecomendados(){
        return this.repository.listarProductosRecomendados();
    }
    public LiveData<GenericResponse<List<Producto>>> listarProductosPorCategoria(int idC){
        return this.repository.listarProductosPorCategoria(idC);
    }
}
