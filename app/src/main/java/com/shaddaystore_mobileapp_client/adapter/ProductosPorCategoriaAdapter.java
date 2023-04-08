package com.shaddaystore_mobileapp_client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.activity.InicioActivity;
import com.shaddaystore_mobileapp_client.activity.ListarProductosPorCategoriaActivity;
import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.communication.MostrarBadgeCommunication;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ProductosPorCategoriaAdapter extends RecyclerView.Adapter<ProductosPorCategoriaAdapter.ViewHolder> {
    private List<Producto> listadoProductoPorCategoria;
    private MostrarBadgeCommunication mostrarBadgeCommunication = null;

    public ProductosPorCategoriaAdapter(List<Producto> listadoProductoPorCategoria, ListarProductosPorCategoriaActivity mostrarBadgeCommunication) {
        this.listadoProductoPorCategoria = listadoProductoPorCategoria;
        this.mostrarBadgeCommunication = mostrarBadgeCommunication;
    }

    public ProductosPorCategoriaAdapter(List<Producto> productos, InicioActivity inicioActivity) {
        this.mostrarBadgeCommunication = mostrarBadgeCommunication;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_por_categoria, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.listadoProductoPorCategoria.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listadoProductoPorCategoria.size();
    }

    public void updateItems(List<Producto> productosByCategoria) {
        this.listadoProductoPorCategoria.clear();
        this.listadoProductoPorCategoria.addAll(productosByCategoria);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgProductoC;
        private final TextView nameProductoC, txtPriceProductoC;
        private final Button btnOrdenarPC;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgProductoC = itemView.findViewById(R.id.imgProductoC);
            this.nameProductoC = itemView.findViewById(R.id.nameProductoC);
            this.txtPriceProductoC = itemView.findViewById(R.id.txtPriceProductoC);
            this.btnOrdenarPC = itemView.findViewById(R.id.btnOrdenarPC);
        }

        public void setItem(final Producto p) {
            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE) //No lo almacena el la caché ni en el disco
                    .error(R.drawable.image_not_found)
                    .into(imgProductoC);
            nameProductoC.setText(p.getNombre());
            txtPriceProductoC.setText(String.format(Locale.ENGLISH, "S/%.2f", p.getPrecio()));
            btnOrdenarPC.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setProducto(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                mostrarBadgeCommunication.add(detallePedido);
            });
        }


    }
}

