package com.wompi.api.tasks;

import com.wompi.api.interactions.waits.Stop;
import com.wompi.api.models.params.PagoParams;
import com.wompi.api.models.responses.consultar.pago.ConsultarPagoResponse;
import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.utils.json.JSONUtils;
import lombok.AllArgsConstructor;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

@AllArgsConstructor
public class ConfirmarPago implements Task {

    private PagoParams params;
    private static final String ENDPOINT = "/transactions";

    public <T extends Actor> void performAs(T actor)
    {
        actor.attemptsTo(
                Stop.forA(5).seconds(),

                Get.resource(ENDPOINT)
                        .with(request -> request
                                .header(//"accept", "*/*",
                                        "Authorization","Bearer "+Protagonist.review().hisNotebook().getParamsNegocio().getLlavePrivada()
                                ).queryParam("reference", this.params.getReferencia())
                        )
        );

        ConsultarPagoResponse responseObj = JSONUtils.pasarAObjeto(
                SerenityRest.lastResponse().getBody().prettyPrint(),
                ConsultarPagoResponse.class
        );
        responseObj.setCodigoHttp(String.valueOf(SerenityRest.lastResponse().statusCode()));

        Protagonist.review().hisNotebook().setConsultarPagoResponse(responseObj);

    }

    public static ConfirmarPago aliado(PagoParams params){
        return Tasks.instrumented(
                ConfirmarPago.class,
                params
        );
    }
}