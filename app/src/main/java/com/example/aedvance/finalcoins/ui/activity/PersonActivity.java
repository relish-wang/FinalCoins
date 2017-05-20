package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseActivity;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Follow;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.util.SPUtil;
import com.example.aedvance.finalcoins.util.TimeUtil;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PersonActivity extends BaseActivity implements View.OnClickListener {

    public static void start(Context context, UserInfo u) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("userInfo", u);
        context.startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment3_info;
    }

    UserInfo mUser;

    @Override
    protected void parseIntent(Intent intent) {
        mUser = (UserInfo) intent.getSerializableExtra("userInfo");
        if (mUser == null) finish();

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState, Toolbar mToolbar) {
        mToolbar.setTitle(mUser.getName());
    }

    Button btn_logout;

    TextView tv_name, tv_question_count, tv_following_count, tv_collect_count;
    Button btn_address, btn_mail, btn_birth, btn_constellation;

    View ll_question, ll_collect, ll_follow;

    @Override
    protected void initViews(Bundle bundle) {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_question_count = (TextView) findViewById(R.id.tv_question_count);
        tv_collect_count = (TextView) findViewById(R.id.tv_collect_count);
        tv_following_count = (TextView) findViewById(R.id.tv_following_count);

        btn_address = (Button) findViewById(R.id.btn_address);
        btn_mail = (Button) findViewById(R.id.btn_mail);
        btn_birth = (Button) findViewById(R.id.btn_birth);
        btn_constellation = (Button) findViewById(R.id.btn_constellation);

        ll_question = findViewById(R.id.ll_question);
        ll_collect = findViewById(R.id.ll_collect);
        ll_follow = findViewById(R.id.ll_follow);
        ll_question.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_follow.setOnClickListener(this);

        tv_name.setText(mUser.getName());
        tv_question_count.setText(String.valueOf(Question.getQuestionCountById(mUser.getId())));
        tv_collect_count.setText(String.valueOf(Answer.getCollectCountByUserId(mUser.getId())));
        tv_following_count.setText(String.valueOf(Follow.getFollowingCountByUserId(mUser.getId())));
        btn_address.setText(mUser.getAddress());
        btn_mail.setText(mUser.getMail());
        btn_birth.setText(mUser.getBirth());
        btn_constellation.setText(TimeUtil.getConstellationByDatetime(mUser.getBirth()));

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setVisibility(View.GONE);
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
                PersonsActivity.start(getActivity(), mUser);
                break;
        }
    }
}
