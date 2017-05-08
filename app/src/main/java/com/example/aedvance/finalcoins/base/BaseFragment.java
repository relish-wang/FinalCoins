package com.example.aedvance.finalcoins.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aedvance on 2017/5/7.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int layoutId();

    protected abstract void initViews(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        initViews(view);
        return view;
    }
}
