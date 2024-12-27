
@Pago
Feature: Realizar transacciones en Wompi usando diferentes métodos de pago
  Yo como un cliente del sistema de pagos Wompi
  Necesito realizar transacciones utilizando diferentes métodos de pago
  Para poder completar pagos de forma exitosa o manejar errores, dependiendo del saldo y la transacción.


  @HP
  @Regression
  @ID-01
  Scenario Outline: Crear una transacción exitosa
    Given que el usuario se conecta al sistema
    When el usuario consulta los negocios
      | negocio.publica |
      | <llave_publica> |
    And el usuario realiza una transacion
      | negocio.integracion | pago.valor   | pago.moneda   | pago.email   | pago.referencia   | pago.cliente.documento | pago.cliente.nombre-completo | pago.cliente.telefono | pago.cliente.tipo-documento | pago.metodo.tipo   | pago.metodo.tarjeta  |
      | <llave_integracion> | <valor_pago> | <moneda_pago> | <email_pago> | <referencia_pago> | <documento_pago>       | <nombres_pago>               | <telefono_pago>       | <tipo_documento_pago>       | <tipo_metodo_pago> | <tarjeta_metodo_pago> |
    And el usuario consulta el estado de la transacción
      | negocio.privada |
      | <llave_privada> |
    Then deberia haberse confirmado la transacción como: "<resultado>"
    Examples:
      |resultado| llave_publica                                 | llave_integracion                                   | llave_privada                                 | valor_pago | moneda_pago | email_pago                | referencia_pago          | documento_pago | nombres_pago     | telefono_pago | tipo_documento_pago | tipo_metodo_pago | tarjeta_metodo_pago |
      |APPROVED| pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7 | stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp | prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg | 2490000    | COP         | yeseniavillamil@gmail.com | {yyMMdd}-{HHmmss}-{###}A | 1144132574     | yesenia villamil | 3128845562    | CC                  | CARD            | tok_stagtest_5113_B036c4356A7409b79Fb773456796f00a           |
      |DECLINED| pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7 | stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp | prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg | 2490000    | COP         | yeseniavillamil@gmail.com | {yyMMdd}-{HHmmss}-{###}A | 1144132574     | yesenia villamil | 3128845562    | CC                  | CARD            | tok_stagtest_5113_50b1b177b5e444c08405c11881d08008           |





