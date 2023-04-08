package com.shaddaystore_mobileapp_client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.api.ConfigApi;
import com.shaddaystore_mobileapp_client.communication.MostrarBadgeCommunication;
import com.shaddaystore_mobileapp_client.databinding.ActivityInicioBinding;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.shaddaystore_mobileapp_client.entity.service.Usuario;
import com.shaddaystore_mobileapp_client.utils.Carrito;
import com.shaddaystore_mobileapp_client.utils.DateSerializer;
import com.shaddaystore_mobileapp_client.utils.TimeSerializer;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


@ExperimentalBadgeUtils
public class InicioActivity extends AppCompatActivity implements MostrarBadgeCommunication {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private List<Producto> producto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicio.toolbar);
        binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Conectar enlace a WhatsApp...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_mis_compras, R.id.nav_configuracion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                this.logout();
                break;
            case R.id.bolsaCompras:
                this.mostrarBolsa();
                break;
            case R.id.buscarProducto:
                this.buscarProducto();
                break;
            case R.id.cat_configuracion:
                this.mostrarConfiguracion();
                break;
            case R.id.cat_accesorios:
                this.mostrarAccesorios();
                break;
            case R.id.cat_bolsos:
                this.mostrarBolsos();
                break;
            case R.id.cat_jugueteria:
                this.mostrarJugueteria();
                break;
            case R.id.cat_lamparas:
                this.mostrarLamparas();
                break;
            case R.id.cat_monederos:
                this.mostrarMonederos();
                break;
            case R.id.cat_papeleria:
                this.mostrarPapeleria();
                break;
            case R.id.cat_termos:
                this.mostrarTermos();
                break;
            case R.id.cat_toallas:
                this.mostrarToallas();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buscarProducto() {
    }


    private void mostrarBolsa() {
        Intent i = new Intent(this, ProductosCarritoActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    private void mostrarCompras() {

    }


    private void mostrarConfiguracion() {
    }

    private void mostrarAccesorios() {
    }

    private void mostrarBolsos() {
    }

    private void mostrarJugueteria() {
    }

    private void mostrarLamparas() {
    }

    private void mostrarMonederos() {
    }

    private void mostrarPapeleria() {
    }

    private void mostrarTermos() {
    }

    private void mostrarToallas() {
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
    private void loadData() {
        /// Para cargar los datos en el main-drawer del usuario
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView tvNombre = vistaHeader.findViewById(R.id.tvNombre),
                    tvCorreo = vistaHeader.findViewById(R.id.tvCorreo);
            final CircleImageView imgFoto = vistaHeader.findViewById(R.id.imgFotoPerfil);
            tvNombre.setText(u.getCliente().getNombreCompleto());
            tvCorreo.setText(u.getEmail());
            // Para descargar la imagen del usuario
            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + u.getCliente().getFoto().getFileName();
            final Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(imgFoto);
        }
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    //Método para cerrar sesión
    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void add(DetallePedido dp) {

    }
}