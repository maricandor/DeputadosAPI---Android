package com.ifsul.deputados_api.requests;

import com.ifsul.deputados_api.entities.Deputado;
import com.ifsul.deputados_api.entities.Link;

import java.util.List;

public class ApiBiografia {

    // Representa os dados do deputado, como nome, partido, foto, etc.
    private Deputado dados;

    // Lista de links relacionados ao deputado, pode conter links para informações adicionais, por exemplo.
    private List<Link> links;

    // Método getter para obter os dados do deputado
    public Deputado getDados() {
        return dados;
    }

    // Método setter para definir os dados do deputado
    public void setDados(Deputado dados) {
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
