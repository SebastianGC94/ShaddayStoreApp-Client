package com.shaddaystore_mobileapp_client.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.adapter.ProductosPorCategoriaAdapter;
import com.shaddaystore_mobileapp_client.communication.MostrarBadgeCommunication;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.shaddaystore_mobileapp_client.utils.Carrito;
import com.shaddaystore_mobileapp_client.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@ExperimentalBadgeUtils
public class ListarProductosPorCategoriaActivity extends AppCompatActivity implements MostrarBadgeCommunication {
    private ProductoViewModel productoViewModel;
    private ProductosPorCategoriaAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private RecyclerView rcvProductoPorCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_por_categoria);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.productoViewModel = vmp.get(ProductoViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductosPorCategoriaAdapter(productos, this);
        rcvProductoPorCategoria = findViewById(R.id.rcvProductosPorCategoria);
        rcvProductoPorCategoria.setAdapter(adapter);
        rcvProductoPorCategoria.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0);//Recibimos el idCategoria
        productoViewModel.listarProductosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarProductos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Buen Trabajo!")
                .setContentText(message).show();
    }
}