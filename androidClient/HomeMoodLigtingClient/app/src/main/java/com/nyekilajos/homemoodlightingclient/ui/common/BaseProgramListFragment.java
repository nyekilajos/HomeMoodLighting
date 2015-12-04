package com.nyekilajos.homemoodlightingclient.ui.common;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

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

import com.nyekilajos.homemoodlightingclient.R;

/**
 * Base abstract fragment for showing program list.
 * 
 * @author Lajos_Nyeki
 */
public abstract class BaseProgramListFragment extends RoboFragment {

    @InjectView(R.id.coordinator_layout_program_list)
    private CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.program_list)
    private RecyclerView programListRecyclerView;
    @InjectView(R.id.add_program_fab)
    private FloatingActionButton addProgram;

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
                    removeProgram(event, viewHolder.getAdapterPosition());
                }
            });
            snackbar.show();

        }
    };

    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);

    protected abstract void removeProgram(int event, int position);

    protected abstract void startEditProgramActivity();

    protected abstract void setupRecyclerView(RecyclerView recyclerView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_program_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        programListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        programListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        programListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setupRecyclerView(programListRecyclerView);

        addProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProgramActivity();
            }
        });
        itemTouchHelper.attachToRecyclerView(programListRecyclerView);
    }
}
