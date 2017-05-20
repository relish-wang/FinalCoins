package com.example.aedvance.finalcoins.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Follow;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.ui.activity.CollectsActivity;
import com.example.aedvance.finalcoins.ui.activity.LoginActivity;
import com.example.aedvance.finalcoins.ui.activity.PersonsActivity;
import com.example.aedvance.finalcoins.ui.activity.QuestionsActivity;
import com.example.aedvance.finalcoins.util.SPUtil;
import com.example.aedvance.finalcoins.util.TimeUtil;

/**
 * Created by aedvance on 2017/5/7.
 */

public class InfoFragment extends BaseFragment implements View.OnClickListener {

    UserInfo mUser;

    @Override
    protected int layoutId() {
        return R.layout.fragment3_info;
    }

    Button btn_logout;

    TextView tv_name, tv_question_count, tv_interest_count, tv_collect_count;
    Button btn_address, btn_mail, btn_birth, btn_constellation;

    View ll_question, ll_collect, ll_follow;

    @Override
    protected void initViews(View view) {
        mUser = App.USER;
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_question_count = (TextView) view.findViewById(R.id.tv_question_count);
        tv_interest_count = (TextView) view.findViewById(R.id.tv_following_count);
        tv_collect_count = (TextView) view.findViewById(R.id.tv_collect_count);

        btn_address = (Button) view.findViewById(R.id.btn_address);
        btn_mail = (Button) view.findViewById(R.id.btn_mail);
        btn_birth = (Button) view.findViewById(R.id.btn_birth);
        btn_constellation = (Button) view.findViewById(R.id.btn_constellation);

        ll_question = view.findViewById(R.id.ll_question);
        ll_collect = view.findViewById(R.id.ll_collect);
        ll_follow = view.findViewById(R.id.ll_follow);
        ll_question.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_follow.setOnClickListener(this);

        tv_name.setText(mUser.getName());
        tv_question_count.setText(String.valueOf(Question.getQuestionCountById(mUser.getId())));
        tv_interest_count.setText(String.valueOf(Follow.getFollowingCountByUserId(mUser.getId())));
        tv_collect_count.setText(String.valueOf(Answer.getCollectCountByUserId(mUser.getId())));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_question:
                QuestionsActivity.start(getActivity(),mUser);
                break;
            case R.id.ll_collect:
                CollectsActivity.start(getActivity(),mUser);
                break;
            case R.id.ll_follow:
                PersonsActivity.start(getActivity(),mUser);
                break;
        }
    }
}
