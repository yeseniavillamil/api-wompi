package com.wompi.api.models.params;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClienteParams {

    @JsonProperty("documento")
    private String documento;

    @JsonProperty("nombre-completo")
    private String nombreCompleto;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("tipo-documento")
    private String tipoDocumento;



}
