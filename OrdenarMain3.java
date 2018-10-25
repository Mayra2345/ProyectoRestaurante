// ---------- Menu de ordenar y mandar correo --------------
// --------- cuestionario ----------
package com.itl.feedback.feedback;


import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class OrdenarMain3 extends AppCompatActivity {

    private Button enviar;
    private String correo;
    private String pass;
    private Session sesion;
    private EditText nombre, imagen, escuela, encuesta;
    private RadioGroup genero, estudiar;
    private ArrayList<String> dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_main3);

        correo = "mayrav867@gmail.com";
        pass = "MAYra0926";
        dias = new ArrayList<String>();
        enviar = (Button) findViewById(R.id.enviar);
        nombre = (EditText) findViewById(R.id.nombre);
        genero = (RadioGroup) findViewById(R.id.genero);
        imagen = (EditText) findViewById(R.id.imagen);
        estudiar = (RadioGroup) findViewById(R.id.estudiar);
        escuela = (EditText) findViewById(R.id.escuela);
        encuesta = (EditText) findViewById(R.id.encuesta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();
            }
        });
    }

    public String crearMensaje () {
        String mensaje = "";

        mensaje += "Nombre: " + nombre.getText().toString() + "\n";

        if (genero.getCheckedRadioButtonId() == R.id.hombre) {
            mensaje += "Genero: Masculino" + "\n";
        } else {
            mensaje += "Genero: Femenino" + "\n";
        }

        mensaje += "Comidas Especiales " + imagen.getText().toString() + "\n";

        mensaje += "¿Que desea ordenar? ";
        for (String dia : dias) {
            mensaje += dia + ", ";
        }
        mensaje += "\n";

        if (estudiar.getCheckedRadioButtonId() == R.id.si) {
            mensaje += "Te gusto el servicio?: Si" + "\n";
        } else if (estudiar.getCheckedRadioButtonId() == R.id.no) {
            mensaje += "Te gusto el servicio?: No" + "\n";
        } else {
            mensaje += "Te gusto el servicio?: Más o menos" + "\n";
        }

        mensaje += "¿Te gusto el restaurante?: " + escuela.getText().toString() + "\n";

        mensaje += "Recomendaciones: " + encuesta.getText().toString() + "\n";

        return mensaje;
    }

    public void enviarCorreo() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try {
            sesion = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo, pass);
                }
            });

            if (sesion != null) {
                String filename = "respuestas.txt";
                FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(crearMensaje().getBytes());
                fos.close();

                MimeBodyPart archivoAdjunto = new MimeBodyPart();
                DataSource source = new FileDataSource(this.getFilesDir().getPath() + "/" + filename);
                archivoAdjunto.setDataHandler(new DataHandler(source));
                archivoAdjunto.setFileName(filename);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(archivoAdjunto);

                Message message = new MimeMessage(sesion);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Email de prueba");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
//                message.setContent(crearMensaje(), "text/html; charset=utf-8");
                message.setContent(multipart);
                Transport.send(message);
            }

            Toast.makeText(this, "Se ha enviado el correo correctamente.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    public void listaDias(Boolean checked, String dia) {
        if (checked) {
            dias.add(dia);
        } else {
            dias.remove(dia);
        }
    }

    public void seleccionarCheck(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkLunes: listaDias(checked, "Enchiladas"); break;
            case R.id.checkMartes: listaDias(checked, "Pozole"); break;
            case R.id.checkMiercoles: listaDias(checked, "Coca-Cola"); break;
            case R.id.checkJueves: listaDias(checked, "Pepsi"); break;
            case R.id.checkViernes: listaDias(checked, "Pastel"); break;
            case R.id.checkSabado: listaDias(checked, "Pay"); break;
            case R.id.checkDomingo: listaDias(checked, "Propina"); break;
        }
    }
}
