package com.nyekilajos.homemoodlightingclient.ui.bedroom;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.persitency.BedroomProgram;
import com.nyekilajos.homemoodlightingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodlightingclient.persitency.ProgramStore;
import com.nyekilajos.homemoodlightingclient.ui.common.BaseProgramListFragment;

/**
 * Fragment to show the bedroom programs
 * 
 * @author Lajos_Nyeki
 */
public class BedroomFragment extends BaseProgramListFragment {

    @Inject
    private BedroomProgramAdapter bedroomProgramAdapter;
    @Inject
    private ProgramStore programStore;
    @Inject
    private LightingPreferencesHelper lightingPreferencesHelper;

    private final BedroomProgramAdapter.BedroomProgramSelectedListener programSelectedListener = new BedroomProgramAdapter.BedroomProgramSelectedListener() {
        @Override
        public void onBedroomProgramSelected(View view, BedroomProgram bedroomProgram) {
            lightingPreferencesHelper.setActiveBedroomProgramId(bedroomProgram.getId());
            bedroomProgramAdapter.refreshProgramList();
        }

        @Override
        public void onBedroomProgramContextMenu(View view, BedroomProgram bedroomProgram) {
            Intent intent = new Intent(getActivity(), EditBedroomProgramActivity.class);
            intent.putExtra(EditBedroomProgramActivity.EXTRA_BEDROOM_PROGRAM_ID, bedroomProgram.getId());
            getActivity().startActivity(intent);
        }
    };

    @Override
    protected void removeProgram(int event, int position) {
        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
            programStore.removeBedroomProgram(bedroomProgramAdapter.getItemFromPosition(position));
        }
        bedroomProgramAdapter.refreshProgramList();
    }

    @Override
    protected void startEditProgramActivity() {
        Intent intent = new Intent(getActivity(), EditBedroomProgramActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    protected void setupRecyclerView(RecyclerView recyclerView) {
        bedroomProgramAdapter.setSelectionListener(programSelectedListener);
        recyclerView.setAdapter(bedroomProgramAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        bedroomProgramAdapter.refreshProgramList();
    }
}
