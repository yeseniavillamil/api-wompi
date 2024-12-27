package com.wompi.api.setups.hooks.datadefinitions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wompi.api.models.scena.NoteBook;
import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.utils.formats.FormatUtil;
import com.wompi.api.utils.objects.MapperUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ParameterDefinitions {

    @ParameterType(".*")
    public Actor actor(String actorName)
    {
        return OnStage.theActorCalled(actorName);
    }

    @Before
    public void setTheStage()
    {
        OnStage.setTheStage(new OnlineCast());
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.useRelaxedHTTPSValidation();
    }

    @After
    public void stop(Scenario scenario)
    {
        Protagonist.isPreparedForNextScene();

        Serenity.getDriver().quit();
        Serenity.getDriver().close();
    }

    @DataTableType()
    public NoteBook noteBook(DataTable table) throws Exception {

        for (Map<String, String> tblGherkinInput : table.entries())
        {
            Map<String, String> tblGherkin = new HashMap<>(tblGherkinInput);

            replaceFormat(tblGherkin);

            Map<String, Object> objectsMap = MapperUtil.convertToObjectsMap(tblGherkin);
            //String jacksonData = new ObjectMapper().writeValueAsString(objectsMap);
            updateNoteBook(Protagonist.review().hisNotebook(),objectsMap);
        }

        return Protagonist.review().hisNotebook();
    }

    private static void replaceFormat(Map<String, String> tblGherkin)
    {
        for (Map.Entry<String, String> entry : tblGherkin.entrySet())
        {
            FormatUtil.validateTblGherkin(tblGherkin,entry.getKey(),entry.getValue());
        }
        Protagonist.review().getTblGherkin().putAll(tblGherkin);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void updateNoteBook(Object targetObject, Map<String, Object> updates) throws Exception {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String jsonPropertyName = entry.getKey();
            Object value = entry.getValue();

            Field field = findFieldByJsonProperty(targetObject.getClass(), jsonPropertyName);
            if (field == null) {
                throw new NoSuchFieldException("No field found for JSON property: " + jsonPropertyName);
            }

            String fieldName = field.getName();
            Method getterMethod = findGetterMethod(targetObject.getClass(), fieldName);
            Object currentValue = getterMethod != null ? getterMethod.invoke(targetObject) : null;

            Method setterMethod = findSetterMethod(targetObject.getClass(), fieldName);
            if (setterMethod == null) {
                throw new NoSuchMethodException("No setter found for field: " + fieldName);
            }

            Class<?> paramType = setterMethod.getParameterTypes()[0];

            if (value instanceof Map) {
                if (currentValue == null) {
                    currentValue = paramType.getDeclaredConstructor().newInstance();
                }
                updateNoteBook(currentValue, (Map<String, Object>) value);
                setterMethod.invoke(targetObject, currentValue);
            } else {

                if (currentValue == null || value != null) {
                    Object convertedValue = objectMapper.convertValue(value, paramType);
                    setterMethod.invoke(targetObject, convertedValue);
                }
            }
        }
    }

    private Field findFieldByJsonProperty(Class<?> clazz, String jsonPropertyName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation.value().equals(jsonPropertyName)) {
                    return field;
                }
            }
        }
        return null;
    }

    private Method findSetterMethod(Class<?> clazz, String fieldName) {
        String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            return clazz.getDeclaredMethod(setterName, clazz.getDeclaredField(fieldName).getType());
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            return null;
        }
    }

    private Method findGetterMethod(Class<?> clazz, String fieldName) {
        String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            return clazz.getDeclaredMethod(getterName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    public static void mergeMapIntoNoteBook(Map<String, Object> objectsMap, Object target) throws IllegalAccessException, InvocationTargetException {
        for (Map.Entry<String, Object> entry : objectsMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String[] keyParts = key.split("\\.");

            Object currentTarget = target;
            Field field = null;

            try {

                for (int i = 0; i < keyParts.length; i++) {
                    field = currentTarget.getClass().getDeclaredField(keyParts[i]);
                    field.setAccessible(true);

                    if (i < keyParts.length - 1) {
                        Object nestedObject = field.get(currentTarget);
                        if (nestedObject == null) {
                            nestedObject = field.getType().getDeclaredConstructor().newInstance();
                            field.set(currentTarget, nestedObject);
                        }
                        currentTarget = nestedObject;
                    } else {

                        field.set(currentTarget, value);
                    }
                }
            } catch (NoSuchFieldException e) {
                System.err.println("No such field: " + key);
            } catch (InstantiationException | NoSuchMethodException e) {
                throw new RuntimeException("Error al instanciar el campo: " + key, e);
            }
        }
    }


}
