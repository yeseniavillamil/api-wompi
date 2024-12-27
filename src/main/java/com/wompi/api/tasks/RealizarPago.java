package com.wompi.api.tasks;

import com.wompi.api.models.params.PagoParams;
import com.wompi.api.models.requests.pago.ClientePagoRequest;
import com.wompi.api.models.requests.pago.MetodoPagoRequest;
import com.wompi.api.models.requests.pago.PagoRequest;
import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.utils.json.JSONUtils;
import com.wompi.api.utils.security.cipher.SHA256Util;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

@AllArgsConstructor
public class RealizarPago implements Task {

    private PagoParams params;
    private static final String ENDPOINT = "/transactions";

    public <T extends Actor> void performAs(T actor)
    {
        PagoRequest objRequest = PagoRequest.builder()
                .acceptanceToken(Protagonist.review().hisNotebook().getConsultarNegocioResponse().getData().getAcceptanceToken().getAcceptanceToken())
                .acceptPersonalAuth(Protagonist.review().hisNotebook().getConsultarNegocioResponse().getData().getPersonalToken().getAcceptanceToken())
                .amountInCents(Long.parseLong(this.params.getValor()))
                .currency(this.params.getMoneda())
                .signature(SHA256Util.cifrar(
                        this.params.getReferencia(),
                        this.params.getValor(),
                        this.params.getMoneda(),
                        Protagonist.review().hisNotebook().getParamsNegocio().getLlaveIntegracion()
                )).customerEmail(this.params.getEmail())
                .reference(this.params.getReferencia())
                .customerData(ClientePagoRequest.builder()
                        .legalId(this.params.getCliente().getDocumento())
                        .fullName(this.params.getCliente().getNombreCompleto())
                        .phoneNumber(this.params.getCliente().getTelefono())
                        .legalIdType(this.params.getCliente().getTipoDocumento())
                        .build())
                .paymentMethod(MetodoPagoRequest.builder()
                        .type(this.params.getMetodo().getTipo())
                        .installments(1)
                        .token(this.params.getMetodo().getTarjeta())
                        .build())
                        .build();

        actor.attemptsTo(

                Post.to(ENDPOINT)
                        .with(request -> request
                                .header(//"accept", "*/*",
                                        "Authorization","Bearer "+Protagonist.review().hisNotebook().getParamsNegocio().getLlavePublica()
                                )//.queryParam("id", this.params.getId())
                                .body(JSONUtils.pasarAJson(objRequest))
                        )
        );

    }

    public static RealizarPago aliado(PagoParams params){
        return Tasks.instrumented(
                RealizarPago.class,
                params
        );
    }
}