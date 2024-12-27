package com.wompi.api.models.requests.pago;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetodoPagoRequest {

    @SerializedName("type")
    private String type;

    @SerializedName("installments")
    private int installments;

    @SerializedName("token")
    private String token;




}
