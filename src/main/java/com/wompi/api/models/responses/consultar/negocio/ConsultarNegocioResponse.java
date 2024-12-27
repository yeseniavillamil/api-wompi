package com.wompi.api.models.responses.consultar.negocio;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConsultarNegocioResponse {

    @SerializedName("data")
    private DataResponse data;

    private String codigoHttp;

}
