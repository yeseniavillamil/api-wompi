package com.wompi.api.constans.enums.config;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public enum SerenityConf {

    URL_API_WOMPI("base.url.api"),
    ;

    private final String property;

    private static EnvironmentVariables properties = SystemEnvironmentVariables.createEnvironmentVariables();

    private static EnvironmentSpecificConfiguration setupFile = EnvironmentSpecificConfiguration.from(properties);

    SerenityConf(String property)
    {
        this.property = property;
    }

    public String getProperty()
    {
        return this.property;
    }

    public String getValue()
    {
        return setupFile.getProperty(this.getProperty());
    }


}
