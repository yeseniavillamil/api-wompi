package com.wompi.api.models.responses.consultar.pago;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataResponse {

    @SerializedName("status")
    private String status;

}
