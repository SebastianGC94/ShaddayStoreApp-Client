package com.shaddaystore_mobileapp_client.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.activity.DetalleProductoActivity;
import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.communication.Communication;
import com.shaddaystore_mobileapp_client.communication.MostrarBadgeCommunication;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.shaddaystore_mobileapp_client.utils.DateSerializer;
import com.shaddaystore_mobileapp_client.utils.TimeSerializer;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductosRecomendadosAdapter extends RecyclerView.Adapter<ProductosRecomendadosAdapter.ViewHolder> {
    private List<Producto> productos;
    private final Communication communication;
    private final MostrarBadgeCommunication mostrarBadgeCommunication;

    public ProductosRecomendadosAdapter(List<Producto> productos, Communication communication, MostrarBadgeCommunication mostrarBadgeCommunication) {
        this.productos = productos;
        this.communication = communication;
        this.mostrarBadgeCommunication = mostrarBadgeCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.productos.get(position));
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

    public void updateItems(List<Producto> producto) {
        this.productos.clear();
        this.productos.addAll(producto);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Producto p) {
            ImageView imgProducto = itemView.findViewById(R.id.imgProducto);
            TextView nameProducto = itemView.findViewById(R.id.nameProducto);
            Button btnOrdenar = itemView.findViewById(R.id.btnOrdenar);

            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(R.drawable.image_not_found)
                    .into(imgProducto);
            nameProducto.setText(p.getNombre());
            btnOrdenar.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setProducto(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                mostrarBadgeCommunication.add(detallePedido);
                //successMessage(Carrito.agregarProductos(detallePedido));
            });

            //Inicializar la vista del detalle del producto
            itemView.setOnClickListener(v -> {
                final Intent i = new Intent(itemView.getContext(), DetalleProductoActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                i.putExtra("detalleProducto", g.toJson(p));
                communication.showDetails(i);
            });
        }

        public void successMessage(String message) {
            new SweetAlertDialog(itemView.getContext(),
                    SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Buen Trabajo!")
                    .setContentText(message).show();
        }
    }
}
