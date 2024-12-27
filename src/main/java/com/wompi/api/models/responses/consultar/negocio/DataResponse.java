package com.wompi.api.models.responses.consultar.negocio;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataResponse {

    @SerializedName("presigned_acceptance")
    private TokenResponse acceptanceToken;


    @SerializedName("presigned_personal_data_auth")
    private TokenResponse personalToken;

}
