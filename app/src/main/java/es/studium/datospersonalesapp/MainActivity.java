package es.studium.datospersonalesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Locale locale = new Locale("en");
    Menu optionsMenu;
    private Configuration config = new Configuration();

    TextView textNombre;
    TextView textApellidos;
    TextView textEdad;

    EditText editTextNombre;
    EditText editTextApellidos;
    EditText editTextEdad;
    RadioGroup radioGenero;
    RadioButton radioHombre;
    RadioButton radioMujer;
    Switch switchHijos;
    Spinner spinnerEstadoCivil;
    TextView textMensaje;
    TextView textMensaje2;
    Button buttonEnviar;
    Button buttonBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNombre = findViewById(R.id.textNombre);
        textApellidos = findViewById(R.id.textApellidos);
        textEdad = findViewById(R.id.textEdad);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextEdad = findViewById(R.id.editTextEdad);
        radioGenero = findViewById(R.id.radioGroup);
        radioGenero.setOnClickListener(this);
        radioHombre = findViewById(R.id.radioHombre);
        radioMujer = findViewById(R.id.radioMujer);
        switchHijos = findViewById(R.id.switchHijos);
        spinnerEstadoCivil = findViewById(R.id.listaEstadoCivil);
        textMensaje = findViewById(R.id.textMensaje);
        textMensaje2 = findViewById(R.id.textMensaje2);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonEnviar.setOnClickListener(this);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonBorrar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == optionsMenu.getItem(0)) {
            locale = new Locale("es");
        }

        else if (item == optionsMenu.getItem(1)) {
            locale = new Locale("en");
        }
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == buttonBorrar.getId()) {
            editTextNombre.getText().clear();
            editTextApellidos.getText().clear();
            editTextEdad.getText().clear();
            radioGenero.clearCheck();
            spinnerEstadoCivil.setSelection(0);
            switchHijos.setChecked(false);
        }
        else if (v.getId() == buttonEnviar.getId()) {
            textMensaje.setText("");
            textMensaje2.setText("");
            if (editTextNombre.getText().toString().isEmpty() || editTextApellidos.getText().toString().isEmpty() || editTextEdad.getText().toString().isEmpty()) {
                textMensaje.setText(R.string.mensaje);
                StringBuilder str = new StringBuilder();
                if (editTextNombre.getText().toString().isEmpty()) {str.append(textNombre.getText() + "\n");}
                if (editTextApellidos.getText().toString().isEmpty()) {str.append(textApellidos.getText() + "\n");}
                if (editTextEdad.getText().toString().isEmpty()) {str.append(textEdad.getText() + "\n");}
                textMensaje2.setText(str.toString().replaceAll(":", ""));
            }
            else {
                // checking the age
                int age = Integer.valueOf(editTextEdad.getText().toString());
                String mensajeEdad = age>=18 ? getResources().getString(R.string.mensajeEdadMayor) : getResources().getString(R.string.mensajeEdadMinor);
                // checking the gender
                String gender = "";
                if (radioHombre.isChecked()) {gender = getResources().getString(R.string.mensajeHombre);}
                else if (radioMujer.isChecked()) {gender = getResources().getString(R.string.mensajeMujer);}

                // checking the marital status
                String estadoCivil;
                if (spinnerEstadoCivil.getSelectedItemPosition() == 0) {
                    estadoCivil = "";
                }
                else {
                    int pos = spinnerEstadoCivil.getSelectedItemPosition();
                    String[] strArray = getResources().getStringArray(R.array.listaEstadoCivil);
                    if (gender.equals("hombre")) {
                        estadoCivil = strArray[pos].toString().replace('@', 'o').toLowerCase();
                    }
                    else if (gender.equals("mujer")) {
                        estadoCivil = strArray[pos].toString().replace('@', 'a').toLowerCase();
                    }
                    else {
                        estadoCivil = strArray[pos].toString().toLowerCase();
                    }
                }
                // checking if they have kids
                String hijos = switchHijos.isChecked() ? getResources().getString(R.string.hijosSi) : getResources().getString(R.string.hijosNo);

                String y = getResources().getString(R.string.y);

                Toast toast = Toast.makeText(this, editTextApellidos.getText() + ", " + editTextNombre.getText() + ", "
                        + mensajeEdad + ", " + gender + ", " + estadoCivil + " " + y + " " + hijos, Toast.LENGTH_LONG);
                // cambiar la posición del toast según la orientación
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    toast.setGravity(Gravity.RIGHT, 30, 200);
                } else {
                    toast.setGravity(Gravity.LEFT, 30, 500);
                }
                toast.show();
            }
        }
    }
}