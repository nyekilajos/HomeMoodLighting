package com.nyekilajos.homemoodligtingclient.ui.bedroom;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.nyekilajos.homemoodligtingclient.R;
import com.nyekilajos.homemoodligtingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodligtingclient.persitency.ProgramStore;
import com.nyekilajos.homemoodligtingclient.ui.common.DividerItemDecoration;

/**
 * Created by Lajos_Nyeki on 11/30/2015.
 */
public class BedroomFragment extends RoboFragment {

    @InjectView(R.id.cordinator_layout_bedroom_fragment)
    private CoordinatorLayout coordinatorLayout;
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

    private final ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.program_deleted, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    if (event != DISMISS_EVENT_ACTION) {
                        programStore.removeBedroomProgram(bedroomProgramAdapter.getItemFromPosition(viewHolder.getAdapterPosition()));
                    }
                    bedroomProgramAdapter.refreshProgramList();
                }
            });
            snackbar.show();

        }
    };

    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bedroom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        itemTouchHelper.attachToRecyclerView(bedroomRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        bedroomProgramAdapter.refreshProgramList();
    }
}
