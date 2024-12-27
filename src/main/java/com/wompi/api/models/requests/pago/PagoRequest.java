package com.wompi.api.models.requests.pago;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PagoRequest {

    @SerializedName("acceptance_token")
    private String acceptanceToken;

    @SerializedName("accept_personal_auth")
    private String acceptPersonalAuth;

    @SerializedName("amount_in_cents")
    private long amountInCents;

    @SerializedName("currency")
    private String currency;

    @SerializedName("signature")
    private String signature;

    @SerializedName("customer_email")
    private String customerEmail;

    @SerializedName("reference")
    private String reference;

    @SerializedName("customer_data")
    private ClientePagoRequest customerData;

    @SerializedName("payment_method")
    private MetodoPagoRequest paymentMethod;


}
