package com.nyekilajos.homemoodligtingclient.fragment;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.nyekilajos.homemoodligtingclient.EditBedroomProgramActivity;
import com.nyekilajos.homemoodligtingclient.R;
import com.nyekilajos.homemoodligtingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodligtingclient.persitency.ProgramStore;

/**
 * Created by Lajos_Nyeki on 11/30/2015.
 */
public class BedroomFragment extends RoboFragment {

    @InjectView(R.id.bedroom_programs)
    private RecyclerView bedroomRecyclerView;
    @InjectView(R.id.add_bedroom_program_fab)
    private FloatingActionButton addProgram;

    @Inject
    private BedroomProgramAdapter bedroomProgramAdapter;
    @Inject
    private ProgramStore programStore;
    @Inject
    private LightingPreferencesHelper lightingPreferencesHelper;

    private BedroomProgramAdapter.BedroomProgramSelectedListener programSelectedListener = new BedroomProgramAdapter.BedroomProgramSelectedListener() {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bedroom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        programStore.storeBedroomProgram(new BedroomProgram(0, "My First program", 3, 0xFF00FFFF, 0xFFFF00FF, 0xFFFFFF00, 0, 0,
                BedroomProgram.BedroomProgramType.SIMPLE_LIGHTING));
        programStore.storeBedroomProgram(new BedroomProgram(1, "An other test program", 5, 0xFFFF00FF, 0xFFFFFF00, 0xFF00FFFF, 0xFFAABBCC, 0xFFCCBBAA,
                BedroomProgram.BedroomProgramType.FADE_IN_OUT));

        bedroomProgramAdapter.setSelectionListener(programSelectedListener);

        bedroomRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        bedroomRecyclerView.setAdapter(bedroomProgramAdapter);
        bedroomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bedroomRecyclerView.setItemAnimator(new DefaultItemAnimator());

        addProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditBedroomProgramActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
