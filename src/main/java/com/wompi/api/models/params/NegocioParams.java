package com.wompi.api.models.params;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NegocioParams {

    @JsonProperty("publica")
    private String llavePublica;

    @JsonProperty("integracion")
    private String llaveIntegracion;

    @JsonProperty("privada")
    private String llavePrivada;

}
