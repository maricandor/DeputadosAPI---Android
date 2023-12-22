package com.ifsul.deputados_api.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ifsul.deputados_api.R;

public class Perfil extends AppCompatActivity {

    private static final int PARTIDO_NAV_ID = R.id.partidoNav;
    private static final int DEPUTADO_NAV_ID = R.id.deputadoNav;
    private static final int PERFIL_NAV_ID = R.id.perfilNav;
    private BottomNavigationView bottomNavigationView;
    private TextView txtNome;
    private Button btLogout;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar variáveis
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btLogout = findViewById(R.id.btLogout);
        txtNome = findViewById(R.id.txtNomePerfil);

        // Inicializar o objeto FirebaseAuth
        auth = FirebaseAuth.getInstance();

        try {
            // Obter o usuário atual
            user = auth.getCurrentUser();

            // Exibir o email do usuário no TextView
            txtNome.setText(user.getEmail());

        } catch (NullPointerException ex) {
            // Tratar exceção caso o usuário seja nulo
            txtNome.setText("");
        }

        // Implementar o listener do item selecionado na BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == PARTIDO_NAV_ID) {
                    startActivity(new Intent(getApplicationContext(), ListandoPartidos.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == DEPUTADO_NAV_ID) {
                    startActivity(new Intent(getApplicationContext(), ListandoDeputados.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == PERFIL_NAV_ID) {
                    startActivity(new Intent(getApplicationContext(), Perfil.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        // Configurar o clique no botão de logout
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Realizar o logout do usuário
                FirebaseAuth.getInstance().signOut();

                // Direcionar para a tela de login
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();

                // Exibir mensagem de sucesso
                Toast.makeText(getApplicationContext(), "Saiu da conta com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
