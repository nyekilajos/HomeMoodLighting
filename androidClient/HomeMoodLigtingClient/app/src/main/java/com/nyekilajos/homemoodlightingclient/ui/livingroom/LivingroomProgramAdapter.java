package com.nyekilajos.homemoodlightingclient.ui.livingroom;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.R;
import com.nyekilajos.homemoodlightingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodlightingclient.persitency.LivingroomProgram;
import com.nyekilajos.homemoodlightingclient.persitency.ProgramStore;

/**
 * Adapter for recyclerview to show livingroom programs.
 * 
 * @author Lajos_Nyeki
 */
public class LivingroomProgramAdapter extends RecyclerView.Adapter<LivingroomProgramAdapter.LivingroomProgramViewHolder> {

    private List<LivingroomProgram> programList;
    private int activeProgramId;

    private final Context context;
    private final ProgramStore programStore;
    private final LightingPreferencesHelper lightingPreferencesHelper;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private LivingroomProgramSelectedListener selectionListener;

    @Inject
    public LivingroomProgramAdapter(Context context, ProgramStore programStore, LightingPreferencesHelper lightingPreferencesHelper) {
        this.context = context;
        this.programStore = programStore;
        this.lightingPreferencesHelper = lightingPreferencesHelper;
        loadProgramList();
    }

    private void loadProgramList() {
        programList = programStore.loadAllLivingroomPrograms();
        activeProgramId = lightingPreferencesHelper.getActiveLivingroomProgramId();
    }

    public void refreshProgramList() {
        loadProgramList();
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setSelectionListener(LivingroomProgramSelectedListener listener) {
        this.selectionListener = listener;
    }

    @Override
    public LivingroomProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LivingroomProgramViewHolder(LayoutInflater.from(context).inflate(R.layout.item_livingroom_program, parent, false));
    }

    public LivingroomProgram getItemFromPosition(int position) {
        return programList.get(position);
    }

    @Override
    public void onBindViewHolder(final LivingroomProgramViewHolder holder, int position) {
        final LivingroomProgram program = getItemFromPosition(position);

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onLivingroomProgramSelected(holder.itemContainer, program);
            }
        });

        holder.itemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectionListener.onLivingroomProgramContextMenu(holder.itemContainer, program);
                return false;
            }
        });

        holder.programName.setText(program.getName());
        holder.frameCount.setText(Integer.toString(program.getFrameList().size()));
        holder.framesPerSec.setText(Integer.toString(program.getFramePerSec()));

        if (activeProgramId == program.getId()) {
            holder.programSelectedCheck.setVisibility(View.VISIBLE);
        } else {
            holder.programSelectedCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public interface LivingroomProgramSelectedListener {
        void onLivingroomProgramSelected(View view, LivingroomProgram livingroomProgram);

        void onLivingroomProgramContextMenu(View view, LivingroomProgram livingroomProgram);
    }

    class LivingroomProgramViewHolder extends RecyclerView.ViewHolder {

        final View itemContainer;
        final ImageView programSelectedCheck;
        final TextView programName;
        final TextView frameCount;
        final TextView framesPerSec;

        public LivingroomProgramViewHolder(View itemView) {
            super(itemView);
            itemContainer = itemView.findViewById(R.id.item_livingroom_program_container);
            programSelectedCheck = (ImageView) itemView.findViewById(R.id.program_selected_check);
            programName = (TextView) itemView.findViewById(R.id.livingroom_program_name);
            frameCount = (TextView) itemView.findViewById(R.id.frame_count);
            framesPerSec = (TextView) itemView.findViewById(R.id.frames_per_sec);
        }
    }
}
