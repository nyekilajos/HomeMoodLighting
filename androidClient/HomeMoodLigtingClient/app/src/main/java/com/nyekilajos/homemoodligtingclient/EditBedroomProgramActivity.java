package com.nyekilajos.homemoodligtingclient;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.inject.Inject;
import com.nyekilajos.homemoodligtingclient.fragment.BedroomProgram;
import com.nyekilajos.homemoodligtingclient.persitency.ProgramStore;

/**
 * Created by Lajos_Nyeki on 12/2/2015.
 */
public class EditBedroomProgramActivity extends RoboActionBarActivity {

    public static final String EXTRA_BEDROOM_PROGRAM_ID = "extraBedrromProgramId";

    @InjectView(R.id.name_of_the_bedroom_program)
    private EditText name;
    @InjectView(R.id.bedroom_program_type_spinner)
    private Spinner typeSpinner;
    @InjectView(R.id.color_1)
    private ColorIndicatorView colorIndicator1;
    @InjectView(R.id.color_2)
    private ColorIndicatorView colorIndicator2;
    @InjectView(R.id.color_3)
    private ColorIndicatorView colorIndicator3;
    @InjectView(R.id.color_4)
    private ColorIndicatorView colorIndicator4;
    @InjectView(R.id.color_5)
    private ColorIndicatorView colorIndicator5;

    @Inject
    private ProgramStore programStore;

    private BedroomProgram programToBeEdited;

    private int colorCount;
    private ArrayAdapter<BedroomProgram.BedroomProgramType> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bedroom_program);
        int programId = getIntent().getIntExtra(EXTRA_BEDROOM_PROGRAM_ID, -1);
        initUi();
        if (programId >= 0) {
            programToBeEdited = programStore.loadBedroomProgram(programId);
        }
        if (programToBeEdited != null) {
            initWithExistingProgram();
        }
    }

    private void initUi() {
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_bedroom_program_type_spinner, BedroomProgram.BedroomProgramType.values());
        typeSpinner.setAdapter(spinnerAdapter);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name.setError(null);
            }
        });
    }

    private void initWithExistingProgram() {
        name.setText(programToBeEdited.getName());
        typeSpinner.setSelection(spinnerAdapter.getPosition(programToBeEdited.getType()));
        colorCount = programToBeEdited.getColorCount();
        if (colorCount > 0) {
            colorIndicator1.setColor(programToBeEdited.getColor1());
        } else {
            colorIndicator1.setState(ColorIndicatorView.State.DISABLED);
        }
        if (colorCount > 1) {
            colorIndicator2.setColor(programToBeEdited.getColor2());
        } else {
            colorIndicator2.setState(ColorIndicatorView.State.DISABLED);
        }
        if (colorCount > 2) {
            colorIndicator3.setColor(programToBeEdited.getColor3());
        } else {
            colorIndicator3.setState(ColorIndicatorView.State.DISABLED);
        }
        if (colorCount > 3) {
            colorIndicator4.setColor(programToBeEdited.getColor4());
        } else {
            colorIndicator4.setState(ColorIndicatorView.State.DISABLED);
        }
        if (colorCount > 4) {
            colorIndicator5.setColor(programToBeEdited.getColor5());
        } else {
            colorIndicator5.setState(ColorIndicatorView.State.DISABLED);
        }
    }

    @Override
    public void onBackPressed() {
        if (name.getText().toString().equals("")) {
            name.setError(getString(R.string.error_name_empty));
        } else {
            countColors();
            BedroomProgram programToStore = new BedroomProgram(
                    (programToBeEdited != null ? programToBeEdited.getId() : (int) System.currentTimeMillis()), name.getText().toString(), colorCount,
                    colorIndicator1.getColor(), colorIndicator2.getColor(), colorIndicator3.getColor(), colorIndicator4.getColor(),
                    colorIndicator5.getColor(), BedroomProgram.BedroomProgramType.FADE_IN_OUT);
            programStore.storeBedroomProgram(programToStore);
            super.onBackPressed();
        }
    }

    private void countColors() {
        colorCount = 0;
        if (colorIndicator1.getState() == ColorIndicatorView.State.SELECTED) {
            colorCount++;
        }
        if (colorIndicator2.getState() == ColorIndicatorView.State.SELECTED) {
            colorCount++;
        }
        if (colorIndicator3.getState() == ColorIndicatorView.State.SELECTED) {
            colorCount++;
        }
        if (colorIndicator4.getState() == ColorIndicatorView.State.SELECTED) {
            colorCount++;
        }
        if (colorIndicator5.getState() == ColorIndicatorView.State.SELECTED) {
            colorCount++;
        }
    }
}
