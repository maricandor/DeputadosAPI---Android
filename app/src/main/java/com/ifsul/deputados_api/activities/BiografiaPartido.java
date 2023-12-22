package com.ifsul.deputados_api.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ifsul.deputados_api.R;
import com.ifsul.deputados_api.services.RestService;
import com.ifsul.deputados_api.entities.Partido;
import com.ifsul.deputados_api.requests.ApiBiografiaPartidos;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BiografiaPartido extends AppCompatActivity {

    // IDs para os itens do BottomNavigationView
    private static final int PARTIDO_NAV_ID = R.id.partidoNav;
    private static final int DEPUTADO_NAV_ID = R.id.deputadoNav;
    private static final int PERFIL_NAV_ID = R.id.perfilNav;

    // Elementos da UI
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;
    private Button btVoltar;
    private TextView txtNomePartido;
    private TextView txtData;
    private TextView txtSigla;
    private TextView txtNumeroMembros;
    private TextView txtLider;
    private ImageView imgPartido;

    // URL base para a API
    private final static String URL = "https://dadosabertos.camara.leg.br/api/v2/";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biografia_partido);

        // Inicialização dos elementos da UI
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.partidoNav);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Navegação entre as atividades ao selecionar itens do BottomNavigationView
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

        // Configuração do Retrofit para chamadas à API
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Inicialização dos elementos da UI
        btVoltar = findViewById(R.id.btVoltarPartido);
        imgPartido = findViewById(R.id.imgPartido);
        txtNomePartido = findViewById(R.id.txtNomePartido);
        txtData = findViewById(R.id.txtData);
        txtSigla = findViewById(R.id.txtSigla);
        txtNumeroMembros = findViewById(R.id.txtNumMembros);
        txtLider = findViewById(R.id.txtLider);
        progressBar = findViewById(R.id.progressBarBiografiaPartido);

        // Obtém o ID do partido passado como extra
        String id = getIntent().getStringExtra("id");

        // Inicia a consulta à API para obter a biografia do partido
        consultaRespostaPartido(id);

        // Configuração do botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retorna à atividade de listagem de partidos
                Intent i = new Intent(getApplicationContext(), ListandoPartidos.class);
                startActivity(i);
                finish();
            }
        });
    }

    // Método para realizar a consulta à API para obter a biografia do partido
    private void consultaRespostaPartido(String id) {
        RestService restService = retrofit.create(RestService.class);

        // Cria a chamada à API para obter a biografia do partido
        Call<ApiBiografiaPartidos> call = restService.consultarBiografiaPartido(id);

        // Torna a ProgressBar visível durante a consulta
        progressBar.setVisibility(View.VISIBLE);

        // Executa a chamada à API assincronamente
        call.enqueue(new Callback<ApiBiografiaPartidos>() {
            @Override
            public void onResponse(Call<ApiBiografiaPartidos> call, Response<ApiBiografiaPartidos> response) {
                if (response.isSuccessful()) {
                    // Se a resposta for bem-sucedida, obtém os dados do partido
                    ApiBiografiaPartidos resposta = response.body();
                    Partido partido = resposta.getDados();

                    // Torna a ProgressBar invisível
                    progressBar.setVisibility(View.GONE);

                    // Preenche os elementos da UI com os dados obtidos
                    txtNomePartido.setText(partido.getNome());
                    txtData.setText(partido.getStatus().getData());
                    txtSigla.setText(partido.getSigla());
                    txtNumeroMembros.setText("Nº de membros: " + partido.getStatus().getTotalMembros());
                    txtLider.setText("Líder: " + partido.getStatus().getLider().getNome());

                    // Usa a biblioteca Picasso para carregar a imagem do partido
                    Picasso.get().load(partido.getUrlLogo()).into(imgPartido);
                }
            }

            @Override
            public void onFailure(Call<ApiBiografiaPartidos> call, Throwable t) {
                // Em caso de falha na requisição, exibe mensagem de erro no Log
                progressBar.setVisibility(View.GONE);
                Log.e("Erro", "Falha na requisição: " + t.getMessage());
            }
        });
    }
}
