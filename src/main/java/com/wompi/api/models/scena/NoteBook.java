package com.wompi.api.models.scena;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.wompi.api.models.params.NegocioParams;
import com.wompi.api.models.params.PagoParams;
import com.wompi.api.models.responses.consultar.negocio.ConsultarNegocioResponse;
import com.wompi.api.models.responses.consultar.pago.ConsultarPagoResponse;
import lombok.Data;

@Data
public class NoteBook {

    @JsonProperty("negocio")
    private NegocioParams paramsNegocio;

    @JsonProperty("pago")
    private PagoParams paramsPago;



    private ConsultarNegocioResponse consultarNegocioResponse;

    private ConsultarPagoResponse consultarPagoResponse;

}
