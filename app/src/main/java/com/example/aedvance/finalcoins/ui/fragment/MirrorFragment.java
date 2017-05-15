package com.example.aedvance.finalcoins.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.task.PostQuestionTask;
import com.example.aedvance.finalcoins.ui.activity.MainActivity;
import com.example.aedvance.finalcoins.util.EasyCallback;

/**
 * Created by aedvance on 2017/5/7.
 */

public class MirrorFragment extends BaseFragment implements OnClickListener {
    @Override
    protected int layoutId() {
        return R.layout.fragment2_mirror;
    }

    EditText et_title;
    EditText et_option1, et_option2;

    FloatingActionButton fab;


    @Override
    protected void initViews(View view) {
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_option1 = (EditText) view.findViewById(R.id.et_option1);
        et_option2 = (EditText) view.findViewById(R.id.et_option2);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                String title = et_title.getText().toString().trim();
                String option1 = et_option1.getText().toString().trim();
                String option2 = et_option2.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(getActivity(), "标题不得为空！", Toast.LENGTH_SHORT).show();
                    et_title.setError("标题不得为空！");
                    et_title.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(option1)) {
                    Toast.makeText(getActivity(), "选项不得为空！", Toast.LENGTH_SHORT).show();
                    et_option1.setError("选项不得为空！");
                    et_option1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(option2)) {
                    Toast.makeText(getActivity(), "标题不得为空！", Toast.LENGTH_SHORT).show();
                    et_option2.setError("选项不得为空！");
                    et_option2.requestFocus();
                    return;
                }
                Question q = new Question();
                q.setUpdateTime(System.currentTimeMillis());
                q.setTitle(title);
                q.setUserId(App.USER.getId());
                Option o1 = new Option();
                o1.setName(option1);
                o1.setCount(0);
                Option o2 = new Option();
                o2.setName(option2);
                o2.setCount(0);
                new PostQuestionTask(q, o1, o2, new EasyCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "发布成功！", Toast.LENGTH_SHORT).show();
                        et_title.setText("");
                        et_option1.setText("");
                        et_option2.setText("");
                        ((MainActivity) getActivity()).mViewPager.setCurrentItem(0);
                    }

                    @Override
                    public void onFailed(String message) {
                        Toast.makeText(getActivity(), "发布失败！", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
                break;
        }
    }


}
