package com.wompi.api.tasks;

import com.wompi.api.models.params.NegocioParams;
import com.wompi.api.models.responses.consultar.negocio.ConsultarNegocioResponse;
import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.utils.json.JSONUtils;
import lombok.AllArgsConstructor;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

@AllArgsConstructor
public class ConsultarNegocio implements Task {

    private NegocioParams params;
    private static final String ENDPOINT = "/merchants";

    public <T extends Actor> void performAs(T actor)
    {
        actor.attemptsTo(

                Get.resource(ENDPOINT+"/"+this.params.getLlavePublica())

        );
        ConsultarNegocioResponse responseObj = JSONUtils.pasarAObjeto(
                SerenityRest.lastResponse().getBody().prettyPrint(),
                ConsultarNegocioResponse.class
        );
        responseObj.setCodigoHttp(String.valueOf(SerenityRest.lastResponse().statusCode()));

        Protagonist.review().hisNotebook().setConsultarNegocioResponse(responseObj);
    }

    public static ConsultarNegocio aliado(NegocioParams params){
        return Tasks.instrumented(
                ConsultarNegocio.class,
                params
        );
    }
}