package com.example.spacegame;

import java.util.ArrayList;

public class SpecialSetForBlocks {
    public ArrayList<ArrayList<Integer>> set;

    public SpecialSetForBlocks(int lenx, int leny) {
        set = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < lenx; i++) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            for (int j = 0; j < leny; j++) {
                a.add(j, 0);
            }
            set.add(a);
        }
    }

    public void print_me() {
        for (int i = 0; i < this.set.size(); i++) {
            System.out.println(this.set.get(i));
        }
    }

    public void replace(int x, int y, int value) {
        ArrayList<Integer> a = this.set.get(x);
        a.set(y, value);
        this.set.set(x, a);
    }

}
