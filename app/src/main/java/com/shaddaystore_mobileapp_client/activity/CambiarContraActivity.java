package com.shaddaystore_mobileapp_client.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.viewmodel.UsuarioViewModel;


public class CambiarContraActivity extends AppCompatActivity {

    private EditText edtConfirmarContra, edtCambiarContra;
    private TextInputLayout txtCambiarContra, txtConfirmarContra;
    private Button btnGuardarNuevaContra;

    public CambiarContraActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);

        this.init();
        this.initViewModel();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);

    }

    private UsuarioViewModel changePassword() {
        String newPassword = edtCambiarContra.getText().toString().trim();
        String confirmNewPassword = edtConfirmarContra.getText().toString().trim();

        if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese una nueva contraseña y confírmela.", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return null;
        }
        Toast.makeText(this, "¡Buen trabajo! Se cambió la contraseña.", Toast.LENGTH_SHORT).show();

        return changePassword();
    }


    private void init() {
        btnGuardarNuevaContra = findViewById(R.id.btnGuardarNuevaContra);
        edtConfirmarContra = findViewById(R.id.edtConfirmarContra);
        edtCambiarContra = findViewById(R.id.edtCambiarContra);
        txtCambiarContra = findViewById(R.id.txtCambiarContra);
        txtConfirmarContra = findViewById(R.id.txtConfirmarContra);

        btnGuardarNuevaContra.setOnClickListener(v -> {
            this.changePassword();
        });
        ///ONCHANGE LISTENER A LOS EDITEXT
        edtCambiarContra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtCambiarContra.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtConfirmarContra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtConfirmarContra.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private boolean validar() {
        boolean retorno = true;
        String clave;
        clave = edtCambiarContra.getText().toString();
        if (clave.isEmpty()) {
            txtCambiarContra.setError("Ingrese una clave para el inicio de sesión");
            retorno = false;
        } else {
            txtCambiarContra.setErrorEnabled(false);
        }

        return retorno;
    }

}


