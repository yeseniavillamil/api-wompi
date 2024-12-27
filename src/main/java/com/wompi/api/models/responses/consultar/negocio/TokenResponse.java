package com.wompi.api.models.responses.consultar.negocio;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenResponse {

    @SerializedName("acceptance_token")
    private String acceptanceToken;


}
