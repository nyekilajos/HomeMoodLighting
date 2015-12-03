package com.nyekilajos.homemoodligtingclient.persitency;

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

    private final SharedPreferences bedroomStore;
    private final Gson gson;

    @Inject
    public ProgramStore(Context context) {
        bedroomStore = context.getSharedPreferences(BEDROOM_PROGRAM_STORE_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void storeBedroomProgram(BedroomProgram bedroomProgram) {
        bedroomStore.edit().putString(Integer.toString(bedroomProgram.getId()), gson.toJson(bedroomProgram)).apply();
    }

    public BedroomProgram loadBedroomProgram(int bedroomProgramId) {
        return gson.fromJson(bedroomStore.getString(Integer.toString(bedroomProgramId), null), BedroomProgram.class);
    }

    public List<BedroomProgram> loadAllBedroomPrograms() {
        List<BedroomProgram> programs = new ArrayList<>();
        for (Object programInJson : bedroomStore.getAll().values()) {
            programs.add(gson.fromJson((String) programInJson, BedroomProgram.class));
        }
        return programs;
    }

    public void removeBedroomProgram(BedroomProgram bedroomProgram) {
        bedroomStore.edit().remove(Integer.toString(bedroomProgram.getId())).commit();
    }
}
