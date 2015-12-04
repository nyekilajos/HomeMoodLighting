package com.nyekilajos.homemoodlightingclient.persitency;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * Helper class to store and load programs.
 * 
 * @author Lajos_Nyeki
 */
public class ProgramStore {

    private static final String BEDROOM_PROGRAM_STORE_NAME = "bedroomProgramStore";
    private static final String LIVINGROOM_PROGRAM_STORE_NAME = "livingroomProgramStore";

    private final SharedPreferences bedroomStore;
    private final SharedPreferences livingroomStore;
    private final Gson gson;

    @Inject
    public ProgramStore(Context context) {
        bedroomStore = context.getSharedPreferences(BEDROOM_PROGRAM_STORE_NAME, Context.MODE_PRIVATE);
        livingroomStore = context.getSharedPreferences(LIVINGROOM_PROGRAM_STORE_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void storeBedroomProgram(BedroomProgram bedroomProgram) {
        bedroomStore.edit().putString(Integer.toString(bedroomProgram.getId()), gson.toJson(bedroomProgram)).apply();
    }

    public BedroomProgram loadBedroomProgram(int bedroomProgramId) {
        return gson.fromJson(bedroomStore.getString(Integer.toString(bedroomProgramId), null), BedroomProgram.class);
    }

    public List<BedroomProgram> loadAllBedroomPrograms() {
        return convertValuesToList(bedroomStore, BedroomProgram.class);
    }

    public void removeBedroomProgram(BedroomProgram bedroomProgram) {
        bedroomStore.edit().remove(Integer.toString(bedroomProgram.getId())).commit();
    }

    public void storeLivingroomProgram(LivingroomProgram livingroomProgram) {
        livingroomStore.edit().putString(Integer.toString(livingroomProgram.getId()), gson.toJson(livingroomProgram)).apply();
    }

    public LivingroomProgram loadLivingroomProgram(int livingroomProgramId) {
        return gson.fromJson(livingroomStore.getString(Integer.toString(livingroomProgramId), null), LivingroomProgram.class);
    }

    public List<LivingroomProgram> loadAllLivingroomPrograms() {
        return convertValuesToList(livingroomStore, LivingroomProgram.class);
    }

    public void removeLivingroomProgram(LivingroomProgram livingroomProgram) {
        livingroomStore.edit().remove(Integer.toString(livingroomProgram.getId())).commit();
    }

    private <T> List<T> convertValuesToList(SharedPreferences preferences, Class<T> classObject) {
        List<T> programs = new ArrayList<>();
        for (Object programInJson : preferences.getAll().values()) {
            programs.add(gson.fromJson((String) programInJson, classObject));
        }
        return programs;
    }
}
