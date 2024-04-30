package com.example.proyecto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProfileFragment extends Fragment {
    private User user;
    private TextView email;

    private TextView tlf;

    private TextView nombre;

    private TextView apellidos;

    private TextView codigoPos;

    private Button changePhoto;

    ImageButton btn;

    private Button updateUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        // Recoge el objeto usuario del Bundle
        email = rootView.findViewById(R.id.email);
        tlf = rootView.findViewById(R.id.phone);
        nombre = rootView.findViewById(R.id.first_name);
        apellidos = rootView.findViewById(R.id.last_name);
        codigoPos = rootView.findViewById(R.id.postal_code);
        updateUser = rootView.findViewById(R.id.change_password_button);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("usuario");
            if (user != null) {
                try {
                    email.setText(user.getEmail());
                    tlf.setText(user.getTelefono());
                    nombre.setText(user.getNombre());
                    apellidos.setText(user.getApellidos());
                    codigoPos.setText(user.getCiudad());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        // Configurar el evento onClick para el botón de actualizar usuario
        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a la función para actualizar usuario
                updateUser();
            }
        });
        btn = btn.findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                assitencia();
            }
        });

        return rootView;



    }
    private void assitencia(){
        Intent intent = new Intent(this.getContext(), atencioncliente2.class);
        intent.putExtra("usuario", user);
        startActivity(intent);
    }
    public void updateUser(){
        JSONObject json = new JSONObject();
        try {
            json.put("nombre", nombre.getText().toString());
            json.put("apellidos", apellidos.getText().toString());
            json.put("email", email.getText().toString());
            json.put("telefono", tlf.getText().toString());
            json.put("ciudad", codigoPos.getText().toString());
            json.put("id_usuario", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private class InsertDataTask extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected String doInBackground(JSONObject... users) {
            JSONObject inc = users[0];
            try {
                // Create the URL connection
                URL url = new URL("http://20.90.95.76/updUser.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // Send JSON data to the server
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
                String datos = inc.toString();
                Log.d("fdasd",datos);
                writer.write(datos);
                writer.flush();
                writer.close();
                os.close();

                // Read the server response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                conn.disconnect();

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("KO")) {
                    Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_LONG).show();
                }
            } else {
                // Handle error if any
                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_LONG).show();
            }
        }

    }




}