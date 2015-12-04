package com.nyekilajos.homemoodlightingclient.persitency;

import com.nyekilajos.homemoodlightingclient.R;

/**
 * Model class for storing bedroom programs.
 * 
 * @author Lajos_Nyeki
 */
public class BedroomProgram {

    private final int id;
    private final String name;
    private final int colorCount;
    private final int color1;
    private final int color2;
    private final int color3;
    private final int color4;
    private final int color5;
    private final BedroomProgramType type;

    public BedroomProgram(int id, String name, int colorCount, int color1, int color2, int color3, int color4, int color5, BedroomProgramType type) {
        this.id = id;
        this.name = name;
        this.colorCount = colorCount;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.color5 = color5;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getColor1() {
        return color1;
    }

    public int getColor2() {
        return color2;
    }

    public int getColor3() {
        return color3;
    }

    public int getColor4() {
        return color4;
    }

    public int getColor5() {
        return color5;
    }

    public BedroomProgramType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BedroomProgram program = (BedroomProgram) o;

        return id == program.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public enum BedroomProgramType {
        SIMPLE_LIGHTING(R.string.simple_lighting_program, R.drawable.bulb), FADE_IN_OUT(R.string.fade_in_out_program, R.drawable.wave);

        private final int nameResId;
        private final int iconResId;

        BedroomProgramType(int nameResId, int iconResId) {
            this.nameResId = nameResId;
            this.iconResId = iconResId;
        }

        public int getNameResId() {
            return nameResId;
        }

        public int getIconResId() {
            return iconResId;
        }
    }

}
