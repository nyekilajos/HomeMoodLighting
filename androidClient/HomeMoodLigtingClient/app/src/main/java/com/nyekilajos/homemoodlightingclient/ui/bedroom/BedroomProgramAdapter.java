package com.nyekilajos.homemoodlightingclient.ui.bedroom;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.R;
import com.nyekilajos.homemoodlightingclient.persitency.BedroomProgram;
import com.nyekilajos.homemoodlightingclient.persitency.LightingPreferencesHelper;
import com.nyekilajos.homemoodlightingclient.persitency.ProgramStore;

/**
 * RecyclerView adapter for bedroom programs.
 * 
 * @author Lajos_Nyeki
 */
public class BedroomProgramAdapter extends RecyclerView.Adapter<BedroomProgramAdapter.BedroomProgramViewHolder> {

    private List<BedroomProgram> programList;
    private int activeProgramId;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Context context;
    private final ProgramStore programStore;
    private final LightingPreferencesHelper lightingPreferencesHelper;

    private BedroomProgramSelectedListener selectionListener;

    @Inject
    public BedroomProgramAdapter(Context context, ProgramStore programStore, LightingPreferencesHelper lightingPreferencesHelper) {
        this.context = context;
        this.programStore = programStore;
        this.lightingPreferencesHelper = lightingPreferencesHelper;
        loadProgramList();
    }

    private void loadProgramList() {
        programList = programStore.loadAllBedroomPrograms();
        activeProgramId = lightingPreferencesHelper.getActiveBedroomProgramId();
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

    public void setSelectionListener(BedroomProgramSelectedListener listener) {
        this.selectionListener = listener;
    }

    @Override
    public BedroomProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bedroom_program, parent, false);
        return new BedroomProgramViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public BedroomProgram getItemFromPosition(int position) {
        return programList.get(position);
    }

    @Override
    public void onBindViewHolder(final BedroomProgramViewHolder holder, int position) {
        final BedroomProgram program = programList.get(position);

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onBedroomProgramSelected(holder.itemContainer, program);
            }
        });

        holder.itemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectionListener.onBedroomProgramContextMenu(holder.itemContainer, program);
                return false;
            }
        });

        holder.programName.setText(program.getName());
        if (program.getColorCount() > 0) {
            holder.color1.setVisibility(View.VISIBLE);
            holder.color1.setBackgroundColor(program.getColor1());
        } else {
            holder.color1.setVisibility(View.INVISIBLE);
        }
        if (program.getColorCount() > 1) {
            holder.color2.setVisibility(View.VISIBLE);
            holder.color2.setBackgroundColor(program.getColor2());
        } else {
            holder.color2.setVisibility(View.INVISIBLE);
        }
        if (program.getColorCount() > 2) {
            holder.color3.setVisibility(View.VISIBLE);
            holder.color3.setBackgroundColor(program.getColor3());
        } else {
            holder.color3.setVisibility(View.INVISIBLE);
        }
        if (program.getColorCount() > 3) {
            holder.color4.setVisibility(View.VISIBLE);
            holder.color4.setBackgroundColor(program.getColor4());
        } else {
            holder.color4.setVisibility(View.INVISIBLE);
        }
        if (program.getColorCount() > 4) {
            holder.color5.setVisibility(View.VISIBLE);
            holder.color5.setBackgroundColor(program.getColor5());
        } else {
            holder.color5.setVisibility(View.INVISIBLE);
        }

        holder.programType.setImageDrawable(ContextCompat.getDrawable(context, program.getType().getIconResId()));

        if (activeProgramId == program.getId()) {
            holder.checkMark.setVisibility(View.VISIBLE);
        } else {
            holder.checkMark.setVisibility(View.INVISIBLE);
        }
    }

    public interface BedroomProgramSelectedListener {
        void onBedroomProgramSelected(View view, BedroomProgram bedroomProgram);

        void onBedroomProgramContextMenu(View view, BedroomProgram bedroomProgram);
    }

    class BedroomProgramViewHolder extends RecyclerView.ViewHolder {

        final View itemContainer;
        final ImageView checkMark;
        final TextView programName;
        final View color1;
        final View color2;
        final View color3;
        final View color4;
        final View color5;
        final ImageView programType;

        public BedroomProgramViewHolder(View itemView) {
            super(itemView);
            itemContainer = itemView.findViewById(R.id.item_bedroom_program_container);
            checkMark = (ImageView) itemView.findViewById(R.id.program_selected_check);
            programName = (TextView) itemView.findViewById(R.id.program_name);
            color1 = itemView.findViewById(R.id.color_1);
            color2 = itemView.findViewById(R.id.color_2);
            color3 = itemView.findViewById(R.id.color_3);
            color4 = itemView.findViewById(R.id.color_4);
            color5 = itemView.findViewById(R.id.color_5);
            programType = (ImageView) itemView.findViewById(R.id.program_icon);
        }
    }
}
