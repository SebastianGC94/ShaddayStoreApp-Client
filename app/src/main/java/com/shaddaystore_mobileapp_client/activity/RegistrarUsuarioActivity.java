package com.shaddaystore_mobileapp_client.activity;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shaddaystore_mobileapp_client.R;
import com.shaddaystore_mobileapp_client.entity.service.Cliente;
import com.shaddaystore_mobileapp_client.entity.service.DocumentoAlmacenado;
import com.shaddaystore_mobileapp_client.entity.service.Usuario;
import com.shaddaystore_mobileapp_client.viewmodel.ClienteViewModel;
import com.shaddaystore_mobileapp_client.viewmodel.DocumentoAlmacenadoViewModel;
import com.shaddaystore_mobileapp_client.viewmodel.UsuarioViewModel;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private File f;
    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private DocumentoAlmacenadoViewModel documentoAlmacenadoViewModel;
    private Button btnSubirImagen, btnGuardarDatos;
    private CircleImageView imageUser;
    private AutoCompleteTextView dropdownTipoDoc, dropdownDepartamento, dropdownCiudad, dropdownSector;
    private EditText edtNameUser, edtApellidoU, edtNumDocU, edtTelefonoU,
            edtDireccionU, edtPasswordUser, edtEmailUser;
    private TextInputLayout txtInputNameUser, txtInputApellidoU, txtInputTipoDoc, txtInputNumeroDocU, txtInputDepartamento, txtInputCiudad,
            txtInputSector, txtInputTelefonoU, txtInputDireccionU, txtInputEmailUser, txtInputPasswordUser;
    private final static int LOCATION_REQUEST_CODE = 23;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        this.init();
        this.initViewModel();
        this.spinners();
    }


    private void spinners() {
        //LISTA DE TIPOS DE DOCUMENTO
        String[] tipoDoc = getResources().getStringArray(R.array.tipoDoc);
        ArrayAdapter arrayTipoDoc = new ArrayAdapter(this, R.layout.dropdown_item, tipoDoc);
        dropdownTipoDoc.setAdapter(arrayTipoDoc);
        //LISTA DE DEPARTAMENTOS
        String[] departamentos = getResources().getStringArray(R.array.departamentos);
        ArrayAdapter arrayDepartamentos = new ArrayAdapter(this, R.layout.dropdown_item, departamentos);
        dropdownDepartamento.setAdapter(arrayDepartamentos);
        //LISTA DE CIUDADES
        String[] ciudades = getResources().getStringArray(R.array.ciudades);
        ArrayAdapter arrayCiudades = new ArrayAdapter(this, R.layout.dropdown_item, ciudades);
        dropdownCiudad.setAdapter(arrayCiudades);
        //LISTA DE SECTORES
        String[] sectores = getResources().getStringArray(R.array.sectores);
        ArrayAdapter arraySectores = new ArrayAdapter(this, R.layout.dropdown_item, sectores);
        dropdownSector.setAdapter(arraySectores);

    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.clienteViewModel = vmp.get(ClienteViewModel.class);
        this.usuarioViewModel = vmp.get(UsuarioViewModel.class);
        this.documentoAlmacenadoViewModel = vmp.get(DocumentoAlmacenadoViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        super.onRequestPermissionsResult(requestCode, permissions, Objects.requireNonNull(grantResults));
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder el permiso de " +
                            "lectura del almacenamiento, esto es necesario para lograr " +
                            "elegir su foto de perfil.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podremos realizar el registro si no se conceden los permisos de lectura del almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void init() {
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
        btnSubirImagen = findViewById(R.id.btnSubirImagen);
        imageUser = findViewById(R.id.imageUser);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtApellidoU = findViewById(R.id.edtApellidoU);
        edtNumDocU = findViewById(R.id.edtNumDocU);
        edtTelefonoU = findViewById(R.id.edtTelefonoU);
        edtDireccionU = findViewById(R.id.edtDireccionU);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        //AutoCompleteTextView
        dropdownTipoDoc = findViewById(R.id.dropdownTipoDoc);
        dropdownDepartamento = findViewById(R.id.dropdownDepartamento);
        dropdownCiudad = findViewById(R.id.dropdownCiudad);
        dropdownSector = findViewById(R.id.dropdownSector);
        //TextInputLayout
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputApellidoU = findViewById(R.id.txtInputApellidoU);
        txtInputTipoDoc = findViewById(R.id.txtInputTipoDoc);
        txtInputNumeroDocU = findViewById(R.id.txtInputNumeroDocU);
        txtInputDepartamento = findViewById(R.id.txtInputDepartamento);
        txtInputCiudad = findViewById(R.id.txtInputCiudad);
        txtInputSector = findViewById(R.id.txtInputSector);
        txtInputTelefonoU = findViewById(R.id.txtInputTelefonoU);
        txtInputDireccionU = findViewById(R.id.txtInputDireccionU);
        txtInputEmailUser = findViewById(R.id.txtInputEmailUser);
        txtInputPasswordUser = findViewById(R.id.txtInputPasswordUser);
        btnSubirImagen.setOnClickListener(v -> {
            this.cargarImagen();
        });
        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });
        ///ONCHANGE LISTENER A LOS EDITEXT
        edtNameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNameUser.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtApellidoU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputApellidoU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtNumDocU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNumeroDocU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTelefonoU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTelefonoU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtDireccionU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDireccionU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownTipoDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTipoDoc.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownDepartamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDepartamento.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownCiudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputCiudad.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownSector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputSector.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void guardarDatos() {
        Cliente c;
        if (validar()) {
            c = new Cliente();
            try {
                c.setNombre(edtNameUser.getText().toString());
                c.setApellido(edtApellidoU.getText().toString());
                c.setTipoDoc(dropdownTipoDoc.getText().toString());
                c.setNumDoc(edtNumDocU.getText().toString());
                c.setDepartamento(dropdownDepartamento.getText().toString());
                c.setCiudad(dropdownCiudad.getText().toString());
                c.setSector(dropdownSector.getText().toString());
                c.setTelefono(edtTelefonoU.getText().toString());
                c.setDireccionEnvio(edtDireccionU.getText().toString());
                c.setId(0);
                LocalDateTime ldt = LocalDateTime.now(); //Para generar el nombre al archivo en base a la fecha/hora/año
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")), somedata; //se envía un archivo (img) desde el formulario
                String filename = "" + ldt.getDayOfMonth() + (ldt.getMonthValue() + 1) +
                        ldt.getYear() + ldt.getHour()
                        + ldt.getMinute() + ldt.getSecond(); //Asignar un nombre al archivo (imagen)
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                somedata = RequestBody.create("profilePh" + filename, MediaType.parse("text/plain")); // se envía un nombre al archivo

                this.documentoAlmacenadoViewModel.save(part, somedata).observe(this, response -> {
                    if (response.getRpta() == 1) {
                        c.setFoto(new DocumentoAlmacenado());
                        c.getFoto().setId(response.getBody().getId());//Asignamos la foto al cliente
                        this.clienteViewModel.guardarCliente(c).observe(this, cResponse -> {
                            if (cResponse.getRpta() == 1) {
                                //Se podría mostrar este mensaje.
                                //Toast.makeText(this, response.getMessage() + " ...procederemos a registrar sus credenciales", Toast.LENGTH_SHORT).show();
                                int idc = cResponse.getBody().getId();//Obtener id del cliente

                                Usuario u = new Usuario();
                                u.setEmail(edtEmailUser.getText().toString());
                                u.setClave(edtPasswordUser.getText().toString());
                                u.setVigencia(true);
                                u.setCliente(new Cliente(idc));
                                this.usuarioViewModel.save(u).observe(this, uResponse -> {
                                    //Toast.makeText(this, uResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    if (uResponse.getRpta() == 1) {
                                        //Toast.makeText(this, "Las credenciales fueron generadas correctamente", Toast.LENGTH_SHORT).show();
                                        successMessage("¡Genial! " + "Su información ha sido guardada con éxito.");
                                        this.limpiarCampos();
                                    } else {
                                        toastIncorrecto("No se guardaron los datos, intentelo de nuevo.");
                                    }
                                });
                            } else {
                                toastIncorrecto("No se guardaron los datos, intentelo de nuevo.");
                            }
                        });
                    } else {
                        toastIncorrecto("No se guardaron los datos, intentelo de nuevo.");
                    }
                });
            } catch (Exception e) {
                /// esto es de la librería sweet alert
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        } else {
            errorMessage("Por favor complete todos los campos del formulario.");
        }
    }

    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Seleccione la aplicación."), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.f = new File(realPath);
            this.imageUser.setImageURI(uri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        ///obtener ruta de la imagen
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private boolean validar() {
        boolean retorno = true;
        String nombre, apellido, numDoc, telefono, direccion, correo, clave,
                dropTipoDoc, dropDepartamento, dropCiudad, dropSector;
        nombre = edtNameUser.getText().toString();
        apellido = edtApellidoU.getText().toString();
        numDoc = edtNumDocU.getText().toString();
        telefono = edtTelefonoU.getText().toString();
        direccion = edtDireccionU.getText().toString();
        correo = edtEmailUser.getText().toString().trim();
        clave = edtPasswordUser.getText().toString();
        dropTipoDoc = dropdownTipoDoc.getText().toString();
        dropDepartamento = dropdownDepartamento.getText().toString();
        dropCiudad = dropdownCiudad.getText().toString();
        dropSector = dropdownSector.getText().toString();
        if (this.f == null) {
            errorMessage("Debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombre.isEmpty()) {
            txtInputNameUser.setError("Ingrese su nombre");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (apellido.isEmpty()) {
            txtInputApellidoU.setError("Ingrese su apellido");
            retorno = false;
        } else {
            txtInputApellidoU.setErrorEnabled(false);
        }
        if (numDoc.isEmpty()) {
            txtInputNumeroDocU.setError("Ingrese su número de documento");
            retorno = false;
        } else {
            txtInputNumeroDocU.setErrorEnabled(false);
        }
        if (telefono.isEmpty()) {
            txtInputTelefonoU.setError("Ingrese su número telefónico");
            retorno = false;
        } else {
            txtInputTelefonoU.setErrorEnabled(false);
        }
        if (direccion.isEmpty()) {
            txtInputDireccionU.setError("Ingrese su dirección de residencia");
            retorno = false;
        } else {
            txtInputDireccionU.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            txtInputEmailUser.setError("Ingrese su correo electrónico");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            txtInputPasswordUser.setError("Ingrese una clave para el inicio de sesión");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }
        if (dropTipoDoc.isEmpty()) {
            txtInputTipoDoc.setError("Seleccione su tipo de documento");
            retorno = false;
        } else {
            txtInputTipoDoc.setErrorEnabled(false);
        }
        if (dropDepartamento.isEmpty()) {
            txtInputDepartamento.setError("Seleccione su departamento");
            retorno = false;
        } else {
            txtInputDepartamento.setErrorEnabled(false);
        }
        if (dropCiudad.isEmpty()) {
            txtInputCiudad.setError("Seleccione su ciudad");
            retorno = false;
        } else {
            txtInputCiudad.setErrorEnabled(false);
        }
        if (dropSector.isEmpty()) {
            txtInputSector.setError("Seleccione su sector");
            retorno = false;
        } else {
            txtInputSector.setErrorEnabled(false);
        }
        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del sistema.")
                .setContentText(message).setConfirmText("Ok").show();
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

    private void limpiarCampos() {
        edtNameUser.setText("");
        edtApellidoU.setText("");
        dropdownTipoDoc.setText("");
        edtNumDocU.setText("");
        dropdownDepartamento.setText("");
        dropdownCiudad.setText("");
        dropdownSector.setText("");
        edtTelefonoU.setText("");
        edtDireccionU.setText("");
        edtEmailUser.setText("");
        edtPasswordUser.setText("");
        edtNameUser.requestFocus();
        imageUser.setImageResource(R.drawable.image_not_found5);
    }
}