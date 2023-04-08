package com.shaddaystore_mobileapp_client.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shaddaystore_mobileapp_client.entity.GenericResponse;
import com.shaddaystore_mobileapp_client.entity.service.DocumentoAlmacenado;
import com.shaddaystore_mobileapp_client.repository.DocumentoAlmacenadoRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DocumentoAlmacenadoViewModel extends AndroidViewModel {
    private final DocumentoAlmacenadoRepository repository;

    public DocumentoAlmacenadoViewModel(@NonNull Application application) {
        super(application);
        this.repository = DocumentoAlmacenadoRepository.getInstance();
    }
    public LiveData<GenericResponse<DocumentoAlmacenado>> save(MultipartBody.Part part, RequestBody requestBody){
        return this.repository.savePhoto(part, requestBody);
    }
}
