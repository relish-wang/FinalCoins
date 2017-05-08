package com.example.aedvance.finalcoins.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.ui.activity.LoginActivity;
import com.example.aedvance.finalcoins.util.SPUtil;

/**
 * Created by aedvance on 2017/5/7.
 */

public class InfoFragment extends BaseFragment {

    @Override
    protected int layoutId() {
        return R.layout.fragment3_info;
    }

    Button btn_logout;

    @Override
    protected void initViews(View view) {
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(getActivity());
                SPUtil.setAutoLogin(false);
                getActivity().finish();
            }
        });
    }
}
