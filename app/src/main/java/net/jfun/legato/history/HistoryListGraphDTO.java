package net.jfun.legato.history;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class HistoryListGraphDTO {


    private int colorIndex;
    private boolean selected;
    private ArrayList<Entry> roastEntry;
    private ArrayList<Entry> ROREntry;
    private ArrayList<Entry> ADEntry;


    public HistoryListGraphDTO(int colorIndex, boolean selected, ArrayList<Entry> roastEntry, ArrayList<Entry> ROREntry, ArrayList<Entry> ADEntry) {
        this.colorIndex = colorIndex;
        this.selected = selected;
        this.roastEntry = roastEntry;
        this.ROREntry = ROREntry;
        this.ADEntry = ADEntry;
    }


    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<Entry> getRoastEntry() {
        return roastEntry;
    }

    public void setRoastEntry(ArrayList<Entry> roastEntry) {
        this.roastEntry = roastEntry;
    }

    public ArrayList<Entry> getROREntry() {
        return ROREntry;
    }

    public void setROREntry(ArrayList<Entry> ROREntry) {
        this.ROREntry = ROREntry;
    }

    public ArrayList<Entry> getADEntry() {
        return ADEntry;
    }

    public void setADEntry(ArrayList<Entry> ADEntry) {
        this.ADEntry = ADEntry;
    }
}
