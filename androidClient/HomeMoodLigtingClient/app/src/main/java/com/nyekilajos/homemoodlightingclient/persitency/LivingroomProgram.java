package com.nyekilajos.homemoodlightingclient.persitency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for storing Livingroom programs.
 * 
 * @author Lajos_Nyeki
 */
public class LivingroomProgram {

    private final int id;
    private final String name;
    private final int framePerSec;
    private final Map<Integer, LivingroomFrame> frames;

    public LivingroomProgram(int id, String name, int framePerSec) {
        this.id = id;
        this.name = name;
        this.framePerSec = framePerSec;
        frames = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFramePerSec() {
        return framePerSec;
    }

    public List<LivingroomFrame> getFrameList() {
        return new ArrayList<>(frames.values());
    }

    public void addFrame(LivingroomFrame frame) {
        frames.put(frame.frameId, frame);
    }

    public void removeFrame(int frameId) {
        frames.remove(frameId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LivingroomProgram that = (LivingroomProgram) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class LivingroomFrame {
        private final int frameId;
        private final Map<Integer, LedColor> colors;

        public LivingroomFrame(int frameId) {
            this.frameId = frameId;
            this.colors = new HashMap<>();
        }

        public int getFrameId() {
            return frameId;
        }

        public void addLedColor(LedColor ledColor) {
            colors.put(ledColor.ledId, ledColor);
        }

        public List<LedColor> getColorList() {
            return new ArrayList<>(colors.values());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            LivingroomFrame that = (LivingroomFrame) o;

            return frameId == that.frameId;

        }

        @Override
        public int hashCode() {
            return frameId;
        }
    }

    public static class LedColor {
        private final int ledId;
        private final byte red;
        private final byte green;
        private final byte blue;

        public LedColor(int ledId, byte red, byte green, byte blue) {
            this.ledId = ledId;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public int getLedId() {
            return ledId;
        }

        public byte getRed() {
            return red;
        }

        public byte getGreen() {
            return green;
        }

        public byte getBlue() {
            return blue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            LedColor ledColor = (LedColor) o;

            return ledId == ledColor.ledId;

        }

        @Override
        public int hashCode() {
            return ledId;
        }
    }

}
