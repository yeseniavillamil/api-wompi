package com.wompi.api.models.requests.pago;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientePagoRequest {

    @SerializedName("legal:id")
    private String legalId;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("legal_id_type")
    private String legalIdType;


}
