package com.wompi.api.models.params;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetodoPagoParams {

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("tarjeta")
    private String tarjeta;





}
