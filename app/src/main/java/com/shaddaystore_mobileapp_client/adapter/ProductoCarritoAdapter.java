package com.shaddaystore_mobileapp_client.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.communication.CarritoCommunication;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductoCarritoAdapter extends RecyclerView.Adapter<ProductoCarritoAdapter.ViewHolder> {
    private final List<DetallePedido> detalles;
    private final CarritoCommunication c;

    public ProductoCarritoAdapter(List<DetallePedido> detalles, CarritoCommunication c) {
        this.detalles = detalles;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_carrito, parent, false);
        return new ViewHolder(v, this.c);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.detalles.get(position));
    }

    @Override
    public int getItemCount() {
        return this.detalles.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgProductoDC, btnDecrease, btnAdd, btnEliminarPCarrito;
        private final EditText edtCantidad;
        private final TextView tvNombreProductoDC, tvPrecioPDC;
        private final CarritoCommunication c;

        public ViewHolder(@NonNull View itemView, CarritoCommunication c) {
            super(itemView);
            this.c = c;
            this.imgProductoDC = itemView.findViewById(R.id.imgProductoDC);
            this.btnEliminarPCarrito = itemView.findViewById(R.id.btnEliminarPCarrito);
            this.btnAdd = itemView.findViewById(R.id.btnAdd);
            this.btnDecrease = itemView.findViewById(R.id.btnDecrease);
            this.edtCantidad = itemView.findViewById(R.id.edtCantidad);
            this.tvNombreProductoDC = itemView.findViewById(R.id.tvNombreProductoDC);
            this.tvPrecioPDC = itemView.findViewById(R.id.tvPrecioPDC);
        }

        public void setItem(final DetallePedido dp) {
            this.tvNombreProductoDC.setText(dp.getProducto().getNombre());
            this.tvPrecioPDC.setText(String.format(Locale.ENGLISH, "S/%.2f", dp.getPrecio()));
            int cant = dp.getCantidad();
            this.edtCantidad.setText(Integer.toString(cant));
            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + dp.getProducto().getFoto().getFileName();
            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(this.imgProductoDC);

            //-------------Actualizar cantidad del carrito-------------------------
            btnAdd.setOnClickListener(v -> {
                if (dp.getCantidad() != 10) {//Si el valor todavía no llega a 10, sige aumentando
                    dp.addOne();
                    ProductoCarritoAdapter.this.notifyDataSetChanged();
                }
            });
            btnDecrease.setOnClickListener(v -> {
                if (dp.getCantidad() != 1) {
                    dp.removeOne();
                    ProductoCarritoAdapter.this.notifyDataSetChanged();
                }
            });

            //------------------Eliminar item del carrito-----------------------
            this.btnEliminarPCarrito.setOnClickListener(v -> {
                showMsg(dp.getProducto().getId());
            });
        }

        public void toastCorrecto(String texto) {
            LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
            View layouView = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) itemView.findViewById(R.id.ll_custom_toast_ok));
            TextView textView = layouView.findViewById(R.id.txtMensajeToast1);
            textView.setText(texto);

            Toast toast = new Toast(itemView.getContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layouView);
            toast.show();
        }
        private void showMsg(int idProducto) {
            new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("¡Aviso del sistema!")
                    .setContentText("¿Está seguro de eliminar el producto de la bolsa de compras?")
                    .setCancelText("No, cancelar.").setConfirmText("Sí, confirmar.")
                    .showCancelButton(true).setCancelClickListener(sDialog -> {
                sDialog.dismissWithAnimation();
                new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("¡Operación cancelada!")
                        .setContentText("No eliminó ningún producto de la bolsa de compras.")
                        .show();
            }).setConfirmClickListener(sweetAlertDialog -> {
                c.eliminarDetalle(idProducto);
                sweetAlertDialog.dismissWithAnimation();
                new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("!Buen Trabajo!")
                        .setContentText("¡Excelente!, el producto se ha eliminado de la bolsa de compras.")
                        .show();
            }).show();
        }
    }
}
