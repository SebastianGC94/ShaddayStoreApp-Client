package com.shaddaystore_mobileapp_client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.entity.service.Usuario;
import com.shaddaystore_mobileapp_client.utils.DateSerializer;
import com.shaddaystore_mobileapp_client.utils.TimeSerializer;
import com.shaddaystore_mobileapp_client.viewmodel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;

@ExperimentalBadgeUtils
public class RecuperarContrasenaActivity extends AppCompatActivity {

    private Button btnValidarCorreo;
    private EditText edtValidarCorreo;
    private TextInputLayout txtValidarCorreo;
    private UsuarioViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        this.init();
        this.initViewModel();


    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }


    private void init() {
        btnValidarCorreo = findViewById(R.id.btnValidarCorreo);
        edtValidarCorreo = findViewById(R.id.edtValidarCorreo);
        txtValidarCorreo = findViewById(R.id.txtValidarCorreo);

        btnValidarCorreo.setOnClickListener(v -> {
            try {
                if (validar()) {
                    viewModel.recovery(edtValidarCorreo.getText().toString()).observe(this, response -> {
                        if (response.getRpta() == 1) {
                            toastCorrecto(response.getMessage());
                            Usuario u = response.getBody();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = preferences.edit();
                            final Gson g = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer())
                                    .create();
                            editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Usuario>() {
                            }.getType()));
                            editor.apply();
                            edtValidarCorreo.setText("");
                            startActivity(new Intent(this, CambiarContraActivity.class));
                        } else {
                            toastIncorrecto("No se encontró el correo electrónico, por favor verifique e intente nuevamente.");
                        }
                    });
                } else {
                    toastIncorrecto("Por favor complete todos los campos.");
                }
            } catch (Exception e) {
                toastIncorrecto("Se ha producido un error al intentar ingresar : " + e.getMessage());
            }
        });

        edtValidarCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtValidarCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void toastCorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        /*toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);*/
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void toastIncorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        /*toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);*/
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    private boolean validar() {
        boolean retorno = true;
        String correo;
        correo = this.edtValidarCorreo.getText().toString().trim();
        if (correo.isEmpty()) {
            txtValidarCorreo.setError("Ingrese su correo electrónico");
            retorno = false;
        } else {
            txtValidarCorreo.setErrorEnabled(false);
        }
        return retorno;
    }

}



