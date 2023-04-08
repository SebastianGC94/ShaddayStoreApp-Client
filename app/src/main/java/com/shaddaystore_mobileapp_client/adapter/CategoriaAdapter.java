package com.shaddaystore_mobileapp_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.activity.ListarProductosPorCategoriaActivity;
import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.entity.service.Categoria;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

@ExperimentalBadgeUtils public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private final String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/";

    public CategoriaAdapter(@NonNull Context context, int resource, @NonNull List<Categoria> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_categorias, parent, false);
        }
        Categoria c = this.getItem(position);
        ImageView imgCategoria = convertView.findViewById(R.id.imgCategoria);
        TextView txtNombreCategoria= convertView.findViewById(R.id.txtNombreCategoria);

        Picasso picasso = new Picasso.Builder(convertView.getContext())
                .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                .build();
        picasso.load(url + c.getFoto().getFileName())
                //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .error(R.drawable.image_not_found)
                .into(imgCategoria);
        txtNombreCategoria.setText(c.getNombre());
        convertView.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ListarProductosPorCategoriaActivity.class);
            i.putExtra("idC", c.getId());//Obtenenmos el id de la Categoria
            getContext().startActivity(i);
        });
        return convertView;
    }
}
