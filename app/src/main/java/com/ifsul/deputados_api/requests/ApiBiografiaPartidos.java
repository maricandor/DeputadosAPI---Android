package com.ifsul.deputados_api.requests;

import com.ifsul.deputados_api.entities.Deputado;
import com.ifsul.deputados_api.entities.Link;
import com.ifsul.deputados_api.entities.Partido;

import java.util.List;

public class ApiBiografiaPartidos {

    // Representa os dados do partido, como nome, sigla, líder, etc.
    private Partido dados;

    // Lista de links relacionados ao partido, pode conter links para informações adicionais.
    private List<Link> links;

    // Método getter para obter os dados do partido
    public Partido getDados() {
        return dados;
    }

    // Método setter para definir os dados do partido
    public void setDados(Partido dados) {
        this.dados = dados;
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
