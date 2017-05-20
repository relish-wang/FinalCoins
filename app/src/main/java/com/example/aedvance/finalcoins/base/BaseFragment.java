package com.example.aedvance.finalcoins.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aedvance.finalcoins.R;

/**
 * Created by aedvance on 2017/5/7.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private static final String TAG = "BaseDialog";

    protected abstract int layoutId();

    protected abstract void initViews(View view);

    protected boolean isCreated = false;


    View loading;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.fragment_base, container, false);
        loading = rootView.findViewById(R.id.loading);
        view = inflater.inflate(layoutId(), container, false);
        rootView.addView(view);
        initViews(view);
        isCreated = true;
        return rootView;
    }

    @Override
    public void showLoading(boolean isShown) {
        view.setVisibility(!isShown ? View.VISIBLE : View.GONE);
        loading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(Object msg) {
        if (msg == null) {
            Log.e(TAG, "showMessage The incoming parameter is null！！！！");
            return;
        }
        if (msg instanceof Integer) {
            Toast.makeText(getActivity(), (Integer) msg, Toast.LENGTH_SHORT).show();
        } else if (msg instanceof CharSequence) {
            Toast.makeText(getActivity(), (CharSequence) msg, Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "showMessage The type of incoming param is " + msg.getClass().getSimpleName());
        }
    }

    @Override
    public void goBrowser(String url) {

    }
}
