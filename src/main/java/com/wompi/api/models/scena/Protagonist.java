package com.wompi.api.models.scena;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.HashMap;
import java.util.Map;

public class Protagonist {

    private static final ThreadLocal<Protagonist> protagonist = ThreadLocal.withInitial(Protagonist::new);
    private NoteBook hisNotebook;

    private Map<String, String> tblGherkin;

    private Map<String, Object> objectsMap;

    public static Actor onStage(){
        return OnStage.theActorInTheSpotlight();
    }

    public static Protagonist review(){

        return protagonist.get();
    }

    public static void isPreparedForNextScene(){

        Protagonist.review().hisNotebook = null;
    }

    public NoteBook hisNotebook()
    {
        if  (   this.hisNotebook == null   )
        {
            this.hisNotebook = new NoteBook();
        }
        return hisNotebook;
    }

    public Map<String, String> getTblGherkin()
    {
        if  (   this.tblGherkin == null  )
        {
            tblGherkin = new HashMap<>();
        }
        return this.tblGherkin;
    }

    public Map<String, Object> getObjectsMap() {
        return objectsMap;
    }

    public void setObjectsMap(Map<String, Object> objectsMap) {
        this.objectsMap = objectsMap;
    }
}
