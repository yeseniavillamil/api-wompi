package com.wompi.api.models.params;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagoParams {

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("moneda")
    private String moneda;

    @JsonProperty("email")
    private String email;

    @JsonProperty("referencia")
    private String referencia;

    @JsonProperty("cliente")
    private ClienteParams cliente;

    @JsonProperty("metodo")
    private MetodoPagoParams metodo;

}
