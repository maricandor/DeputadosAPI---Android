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
import com.ifsul.deputados_api.requests.ApiBiografia;
import com.ifsul.deputados_api.services.RestService;
import com.ifsul.deputados_api.entities.Deputado;
import com.ifsul.deputados_api.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BiografiaDeputado extends AppCompatActivity {

    // IDs para os itens do BottomNavigationView
    private static final int PARTIDO_NAV_ID = R.id.partidoNav;
    private static final int DEPUTADO_NAV_ID = R.id.deputadoNav;
    private static final int PERFIL_NAV_ID = R.id.perfilNav;

    // Elementos da UI
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;
    private Button btVoltar;
    private TextView txtNomeDeputado;
    private TextView txtNascimento;
    private TextView txtPartidoDeputado;
    private TextView txtUfDeputado;
    private TextView txtMunicipio;
    private ImageView imgDeputado;

    // URL base para a API
    private final static String URL = "https://dadosabertos.camara.leg.br/api/v2/";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biografia_deputado);

        // Inicialização dos elementos da UI
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.deputadoNav);
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
        btVoltar = findViewById(R.id.btVoltarDeputado);
        imgDeputado = findViewById(R.id.imgDeputado);
        txtNomeDeputado = findViewById(R.id.txtNomeDeputado);
        txtNascimento = findViewById(R.id.txtNascimento);
        txtPartidoDeputado = findViewById(R.id.txtSiglaPartido);
        txtUfDeputado = findViewById(R.id.txtUfDeputado);
        txtMunicipio = findViewById(R.id.txtMunicipio);
        progressBar = findViewById(R.id.progressBarBiografiaDeputado);

        // Obtém o ID do deputado passado como extra
        String id = getIntent().getStringExtra("id");

        // Inicia a consulta à API para obter a biografia do deputado
        consultaResposta(id);

        // Configuração do botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retorna à atividade de listagem de deputados
                Intent i = new Intent(getApplicationContext(), ListandoDeputados.class);
                startActivity(i);
                finish();
            }
        });
    }

    // Método para realizar a consulta à API para obter a biografia do deputado
    private void consultaResposta(String id) {
        RestService restService = retrofit.create(RestService.class);

        // Cria a chamada à API para obter a biografia do deputado
        Call<ApiBiografia> call = restService.consultarBiografia(id);

        // Torna a ProgressBar visível durante a consulta
        progressBar.setVisibility(View.VISIBLE);

        // Executa a chamada à API assincronamente
        call.enqueue(new Callback<ApiBiografia>() {
            @Override
            public void onResponse(Call<ApiBiografia> call, Response<ApiBiografia> response) {
                if (response.isSuccessful()) {
                    // Se a resposta for bem-sucedida, obtém os dados do deputado
                    ApiBiografia resposta = response.body();
                    Deputado deputado = resposta.getDados();

                    // Torna a ProgressBar invisível
                    progressBar.setVisibility(View.GONE);

                    // Preenche os elementos da UI com os dados obtidos
                    txtNomeDeputado.setText(deputado.getNomeCivil());
                    txtNascimento.setText(deputado.getDataNascimento());
                    txtPartidoDeputado.setText(deputado.getUltimoStatus().getSiglaPartido());
                    txtUfDeputado.setText(deputado.getUltimoStatus().getSiglaUf());
                    txtMunicipio.setText(deputado.getMunicipioNascimento());
                    Picasso.get().load(deputado.getUltimoStatus().getUrlFoto()).into(imgDeputado);
                }
            }

            @Override
            public void onFailure(Call<ApiBiografia> call, Throwable t) {
                // Em caso de falha na requisição, exibe mensagem de erro no Log
                progressBar.setVisibility(View.GONE);
                Log.e("Erro", "Falha na requisição: " + t.getMessage());
            }
        });
    }
}
