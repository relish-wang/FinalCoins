package com.example.aedvance.finalcoins.ui.fragment;

import android.os.AsyncTask;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.adapter.QuestionAdapter;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.util.EasyCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aedvance on 2017/5/6.
 */

public class QuestionFragment extends BaseFragment implements EasyCallback {

    List<Question> mData = new ArrayList<>();

    ListView lv;
    TextView tv_no_data;
    SearchView search_view;
    QuestionAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment1_zone;
    }

    @Override
    protected void initViews(View view) {
        search_view = (SearchView) view.findViewById(R.id.search_view);
        lv = (ListView) view.findViewById(R.id.listview);
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);

        adapter = new QuestionAdapter(mData, this, App.USER);
        lv.setAdapter(adapter);
        request();
    }


    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     * For 友盟统计的页面线性不交叉统计需求
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreated) {
            request();
        }
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
                return Question.findAll(Question.class);
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                super.onPostExecute(questions);
                showLoading(false);
                if (questions == null || questions.size() == 0) {
                    update();
                    return;
                }
                Collections.sort(questions);
                mData = questions;
                adapter.setData(mData);
                update();
            }
        }.execute();
    }

    private void update() {
        if (!isCreated) return;
        if (mData == null || mData.size() == 0) {
            lv.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            lv.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onSuccess(Object o) {
        update();
    }

    @Override
    public void onFailed(String message) {

    }
}
