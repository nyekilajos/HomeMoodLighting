package com.nyekilajos.homemoodligtingclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

/**
 * Created by Lajos_Nyeki on 12/2/2015.
 */
public class ColorIndicatorView extends View {

    private int color;
    private State state = State.UNSELECTED;

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (state == State.UNSELECTED) {
                final ColorPicker cp = new ColorPicker((Activity) getContext(), Color.red(color), Color.green(color), Color.blue(color));
                cp.show();
                cp.findViewById(R.id.okColorButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setColor(cp.getColor());
                        cp.dismiss();
                    }
                });
            }
        }
    };

    public ColorIndicatorView(Context context) {
        super(context);
        init();
    }

    public ColorIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnClickListener(onClickListener);
        setBackground(null);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.add));
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.state = State.SELECTED;
        setBackground(null);
        setBackgroundColor(color);
        invalidate();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        switch (state) {
        case SELECTED:
            setBackground(null);
            setBackgroundColor(color);
            setVisibility(VISIBLE);
            break;

        case UNSELECTED:
            setBackground(null);
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.add));
            setVisibility(VISIBLE);
            break;

        case DISABLED:
            setBackground(null);
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.add));
            setVisibility(INVISIBLE);
            break;
        }
        invalidate();
    }

    public enum State {
        SELECTED, UNSELECTED, DISABLED
    }

}
