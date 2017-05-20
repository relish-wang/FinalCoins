package com.example.aedvance.finalcoins.ui.activity.define;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.adapter.QuestionAdapter;
import com.example.aedvance.finalcoins.base.BaseActivity;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.util.EasyCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseQuestionsActivity extends BaseActivity implements EasyCallback {


    protected abstract List<Question> findQuestions();

    protected abstract String title();

    protected UserInfo mUser;

    @Override
    protected void parseIntent(Intent intent) {
        mUser = (UserInfo) intent.getSerializableExtra("userInfo");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_questions;
    }


    @Override
    protected void initToolbar(Bundle savedInstanceState, Toolbar mToolbar) {
        mToolbar.setTitle(title());
    }

    TextView tv_no_data;
    ListView lv_questions;
    QuestionAdapter mAdapter;
    List<Question> mData = new ArrayList<>();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        lv_questions = (ListView) findViewById(R.id.lv_questions);
        mAdapter = new QuestionAdapter(mData, this,mUser);
        lv_questions.setAdapter(mAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        request();
    }

    public void request() {
        new AsyncTask<Void, Void, List<Question>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoading(true);
            }

            @Override
            protected List<Question> doInBackground(Void... params) {
                return findQuestions();
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                super.onPostExecute(questions);
                showLoading(false);
                if (questions == null) {
                    questions = new ArrayList<>();
                }
                Collections.sort(questions);
                mData = questions;
                mAdapter.setData(mData);
                update();
            }
        }.execute();
    }

    @Override
    public void onSuccess(Object o) {
        request();
    }

    @Override
    public void onFailed(String message) {

    }

    protected void update() {
        if (mData == null || mData.size() == 0) {
            lv_questions.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            lv_questions.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }
}
