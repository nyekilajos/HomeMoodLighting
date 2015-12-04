package com.nyekilajos.homemoodlightingclient.ui.livingroom;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodlightingclient.persitency.LivingroomProgram;
import com.nyekilajos.homemoodlightingclient.persitency.ProgramStore;
import com.nyekilajos.homemoodlightingclient.ui.common.BaseProgramListFragment;

/**
 * Fragment to show the list of livingroom programs.
 * 
 * @author Lajos_Nyeki
 */
public class LivingroomFragment extends BaseProgramListFragment {

    @Inject
    private LivingroomProgramAdapter livingroomProgramAdapter;
    @Inject
    private ProgramStore programStore;
    @Inject
    private LightingPreferencesHelper lightingPreferencesHelper;

    private final LivingroomProgramAdapter.LivingroomProgramSelectedListener programSelectedListener = new LivingroomProgramAdapter.LivingroomProgramSelectedListener() {
        @Override
        public void onLivingroomProgramSelected(View view, LivingroomProgram livingroomProgram) {
            lightingPreferencesHelper.setActiveLivingroomProgramId(livingroomProgram.getId());
            livingroomProgramAdapter.refreshProgramList();
        }

        @Override
        public void onLivingroomProgramContextMenu(View view, LivingroomProgram bedroomProgram) {
            //TODO Open edit activity for livingroom program.
        }
    };

    @Override
    protected void removeProgram(int event, int position) {
        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
            programStore.removeLivingroomProgram(livingroomProgramAdapter.getItemFromPosition(position));
        }
        livingroomProgramAdapter.refreshProgramList();

    }

    @Override
    protected void startEditProgramActivity() {
        //TODO Open edit activity for livingroom program.
    }

    @Override
    protected void setupRecyclerView(RecyclerView recyclerView) {
        LivingroomProgram livingroomProgram1 = new LivingroomProgram(1, "My first livingroom program", 45);
        LivingroomProgram.LivingroomFrame frame00 = new LivingroomProgram.LivingroomFrame(0);
        frame00.addLedColor(new LivingroomProgram.LedColor(0, (byte) 255, (byte) 255, (byte) 255));
        frame00.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        frame00.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        LivingroomProgram.LivingroomFrame frame01 = new LivingroomProgram.LivingroomFrame(1);
        frame01.addLedColor(new LivingroomProgram.LedColor(0, (byte) 255, (byte) 255, (byte) 255));
        frame01.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        frame01.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        LivingroomProgram.LivingroomFrame frame02 = new LivingroomProgram.LivingroomFrame(2);
        frame02.addLedColor(new LivingroomProgram.LedColor(0, (byte) 255, (byte) 255, (byte) 255));
        frame02.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        frame02.addLedColor(new LivingroomProgram.LedColor(0, (byte) 15, (byte) 55, (byte) 25));
        livingroomProgram1.addFrame(frame00);
        livingroomProgram1.addFrame(frame01);
        livingroomProgram1.addFrame(frame02);
        programStore.storeLivingroomProgram(livingroomProgram1);

        livingroomProgramAdapter.refreshProgramList();

        LivingroomProgram livingroomProgram2 = new LivingroomProgram(2, "My second livingroom program", 12);
        livingroomProgram2.addFrame(frame00);
        livingroomProgram2.addFrame(frame01);
        programStore.storeLivingroomProgram(livingroomProgram2);

        livingroomProgramAdapter.setSelectionListener(programSelectedListener);
        recyclerView.setAdapter(livingroomProgramAdapter);
    }
}
