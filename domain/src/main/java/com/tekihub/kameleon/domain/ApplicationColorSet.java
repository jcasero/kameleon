package com.tekihub.kameleon.domain;

import java.util.ArrayList;

public class ApplicationColorSet {
    private ArrayList<Integer> colorSet;

    public ApplicationColorSet(ArrayList<Integer> colorSet) {
        this.colorSet = colorSet;
    }

    public ArrayList<Integer> getColorSet() {
        return colorSet;
    }
}
