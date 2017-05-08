package com.example.aedvance.finalcoins.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Collect;
import com.example.aedvance.finalcoins.bean.Interest;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.ui.activity.LoginActivity;
import com.example.aedvance.finalcoins.util.SPUtil;
import com.example.aedvance.finalcoins.util.TimeUtil;

/**
 * Created by aedvance on 2017/5/7.
 */

public class InfoFragment extends BaseFragment {

    UserInfo mUser;

    @Override
    protected int layoutId() {
        return R.layout.fragment3_info;
    }

    Button btn_logout;

    TextView tv_name, tv_question_count, tv_interest_count, tv_collect_count;
    Button btn_address, btn_mail, btn_birth, btn_constellation;

    @Override
    protected void initViews(View view) {
        mUser = App.USER;
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_question_count = (TextView) view.findViewById(R.id.tv_question_count);
        tv_interest_count = (TextView) view.findViewById(R.id.tv_interest_count);
        tv_collect_count = (TextView) view.findViewById(R.id.tv_collect_count);

        btn_address = (Button) view.findViewById(R.id.btn_address);
        btn_mail = (Button) view.findViewById(R.id.btn_mail);
        btn_birth = (Button) view.findViewById(R.id.btn_birth);
        btn_constellation = (Button) view.findViewById(R.id.btn_constellation);

        tv_name.setText(mUser.getName());
        tv_question_count.setText(String.valueOf(Question.getQuestionCountById(mUser.getId())));
        tv_interest_count.setText(String.valueOf(Interest.getInterestCountByUserId(mUser.getId())));
        tv_collect_count.setText(String.valueOf(Collect.getCollectCountByUserId(mUser.getId())));
        btn_address.setText(mUser.getAddress());
        btn_mail.setText(mUser.getMail());
        btn_birth.setText(mUser.getBirth());
        btn_constellation.setText(TimeUtil.getConstellationByDatetime(mUser.getBirth()));

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
