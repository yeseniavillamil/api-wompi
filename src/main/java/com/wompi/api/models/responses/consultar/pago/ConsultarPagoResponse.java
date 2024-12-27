package com.wompi.api.models.responses.consultar.pago;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ConsultarPagoResponse {

    @SerializedName("data")
    private List<DataResponse> data;

    private String codigoHttp;

}
