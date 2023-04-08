package com.shaddaystore_mobileapp_client.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.adapter.CategoriaAdapter;
import com.shaddaystore_mobileapp_client.adapter.ProductosRecomendadosAdapter;
import com.shaddaystore_mobileapp_client.adapter.SliderAdapter;
import com.shaddaystore_mobileapp_client.communication.Communication;
import com.shaddaystore_mobileapp_client.communication.MostrarBadgeCommunication;
import com.shaddaystore_mobileapp_client.entity.SliderItem;
import com.shaddaystore_mobileapp_client.entity.service.DetallePedido;
import com.shaddaystore_mobileapp_client.entity.service.Producto;
import com.shaddaystore_mobileapp_client.utils.Carrito;
import com.shaddaystore_mobileapp_client.viewmodel.CategoriaViewModel;
import com.shaddaystore_mobileapp_client.viewmodel.ProductoViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@ExperimentalBadgeUtils public class InicioFragment extends Fragment implements Communication, MostrarBadgeCommunication {
    private CategoriaViewModel categoriaViewModel;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductosRecomendados;
    private ProductosRecomendadosAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v){
        svCarrusel = v.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);
        //Categorías
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = v.findViewById(R.id.gvCategorias);
        //Productos
        rcvProductosRecomendados = v.findViewById(R.id.rcvProductosRecomendados);
        rcvProductosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        productoViewModel = vmp.get(ProductoViewModel.class);

    }
    private void initAdapter() {
        //Carrusel de Imágenes
        sliderAdapter = new SliderAdapter(getContext());
        svCarrusel.setSliderAdapter(sliderAdapter);
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarrusel.startAutoCycle();
        //Categorías
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);
        //Productos
        adapter = new ProductosRecomendadosAdapter(productos, this, this);
        rcvProductosRecomendados.setAdapter(adapter);
    }
    private void loadData() {

        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.item_agenda_tornasol, ""));
        lista.add(new SliderItem(R.drawable.item_bolso_mario, ""));
        lista.add(new SliderItem(R.drawable.item_llavero_gato, ""));
        lista.add(new SliderItem(R.drawable.item_termos, ""));
        lista.add(new SliderItem(R.drawable.item_toalla, ""));
        lista.add(new SliderItem(R.drawable.item_sacapuntas_elefante, ""));
        lista.add(new SliderItem(R.drawable.item_monedero_conejo, ""));
        lista.add(new SliderItem(R.drawable.item_tapete_cerdito, ""));
        lista.add(new SliderItem(R.drawable.item_lapiz_comida_rapida, ""));
        lista.add(new SliderItem(R.drawable.item_lapicero_negro_gato_caritas, ""));
        lista.add(new SliderItem(R.drawable.item_libreta_argollas, ""));
        lista.add(new SliderItem(R.drawable.item_papelera_sacapuntas_osito, ""));
        lista.add(new SliderItem(R.drawable.item_sacapuntas_astronauta, ""));
        lista.add(new SliderItem(R.drawable.item_reloj_sacapunta, ""));
        lista.add(new SliderItem(R.drawable.item_reloj_pinguino, ""));
        lista.add(new SliderItem(R.drawable.item_gafas_azul, ""));
        lista.add(new SliderItem(R.drawable.item_kit_colores_disney, ""));
        lista.add(new SliderItem(R.drawable.item_estuche_cosmeticos, ""));
        lista.add(new SliderItem(R.drawable.item_lampara_noche_calendario, ""));
        lista.add(new SliderItem(R.drawable.item_calculadora_hellokitty, ""));
        lista.add(new SliderItem(R.drawable.item_cartuchera_grande_astronauta, ""));
        lista.add(new SliderItem(R.drawable.item_bisturi, ""));
        lista.add(new SliderItem(R.drawable.item_borrador_donut, ""));
        lista.add(new SliderItem(R.drawable.item_caja_accesorios, ""));
        lista.add(new SliderItem(R.drawable.item_botilito_anime_transparente, ""));
        lista.add(new SliderItem(R.drawable.item_esfero_luz_unicornio, ""));

        sliderAdapter.updateItem(lista);
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() == 1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorías activas.");
            }
        });
        productoViewModel.listarProductosRecomendados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });

    }


    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void exportInvoice(int idCli, int idOrden, String fileName) {

    }

  /* */ @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarProductos(dp));
        BadgeDrawable badgeDrawables = BadgeDrawable.create(this.getContext());
        badgeDrawables.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawables, getActivity().findViewById(R.id.toolbar), R.id.bolsaCompras);
    }
    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Buen Trabajo!")
                .setContentText(message).show();
    }
}