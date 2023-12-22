package com.ifsul.deputados_api.requests;

import com.ifsul.deputados_api.entities.Link;
import com.ifsul.deputados_api.entities.Partido;

import java.util.List;

public class ApiPartidos {

    // Lista de objetos do tipo Partido, representando os dados dos partidos políticos.
    private List<Partido> dados;

    // Lista de links relacionados aos partidos, pode conter links para informações adicionais.
    private List<Link> links;

    // Método getter para obter a lista de partidos
    public List<Partido> getDados() {
        return dados;
    }

    // Método setter para definir a lista de partidos
    public void setDados(List<Partido> partidos) {
        this.dados = partidos;
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

