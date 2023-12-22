package com.ifsul.deputados_api.requests;

import com.ifsul.deputados_api.entities.Deputado;
import com.ifsul.deputados_api.entities.Link;

import java.util.List;

public class ApiDeputados {

    // Lista de objetos do tipo Deputado, representando os dados dos deputados políticos.
    private List<Deputado> dados;

    // Lista de links relacionados aos deputados, pode conter links para informações adicionais.
    private List<Link> links;

    // Método getter para obter a lista de deputados
    public List<Deputado> getDados() {
        return dados;
    }

    // Método setter para definir a lista de deputados
    public void setDados(List<Deputado> deputados) {
        this.dados = deputados;
    }

    // Método getter para obter a lista de links
    public List<Link> getLinks() {
        return links;
    }

    // Método setter para definir a lista de links
    public void setLinks(List<Link> links) {
        this.links = links;
    }
}

