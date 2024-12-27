package com.wompi.api.stepdefinitions.features;


import com.wompi.api.constans.enums.config.SerenityConf;
import com.wompi.api.models.scena.NoteBook;
import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.tasks.ConfirmarPago;
import com.wompi.api.tasks.ConsultarNegocio;
import com.wompi.api.tasks.RealizarPago;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;


public class PagoStepDefinitions {

    @Given("que el {actor} se conecta al sistema")
    public void elUsuarioSeConectaAlSistema(Actor actor) {

        actor.whoCan(CallAnApi.at(SerenityConf.URL_API_WOMPI.getValue()));
    }

    @When("el {actor} consulta los negocios")
    public void elUsuarioConsultaLosNegocios(Actor actor, NoteBook noteBook) {

        actor.attemptsTo(
                ConsultarNegocio.aliado(noteBook.getParamsNegocio())
        );
    }

    @When("el {actor} realiza una transacion")
    public void elUsuarioRealizaUnaTransacion(Actor actor, NoteBook noteBook) {

        actor.attemptsTo(
                RealizarPago.aliado(noteBook.getParamsPago())
        );
    }

    @When("el {actor} consulta el estado de la transacción")
    public void elUsuarioConsultaElEstadoDeLaTransacción(Actor actor, NoteBook noteBook) {

        actor.attemptsTo(
                ConfirmarPago.aliado(noteBook.getParamsPago())
        );
    }

    @Then("deberia haberse confirmado la transacción como: {string}")
    public void shouldSee(String estado) {

        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        Ensure.that(Protagonist.review().hisNotebook().getConsultarPagoResponse().getData().get(0).getStatus())
                                .isEqualToIgnoringCase(estado)
                );
    }
}
