package com.example.aedvance.finalcoins.ui.fragment;

import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aedvance on 2017/5/6.
 */

public class QuestionFragment extends BaseFragment {

    List<Question> mData = new ArrayList<>();

    ListView lv;
    TextView tv_no_data;
    SearchView search_view;
    MyAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment1_zone;
    }

    @Override
    protected void initViews(View view) {
        search_view = (SearchView) view.findViewById(R.id.search_view);
        lv = (ListView) view.findViewById(R.id.listview);
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);

        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }


    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     * For 友盟统计的页面线性不交叉统计需求
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            request();
        }
    }
    public void request() {
        new AsyncTask<Void, Void, List<Question>>() {

            @Override
            protected List<Question> doInBackground(Void... params) {
                return Question.findAll(Question.class);
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                super.onPostExecute(questions);
                if (questions == null || questions.size() == 0) {
                    update();
                    return;
                }
                mData = questions;
                update();
            }
        }.execute();
    }

    private void update() {
        if(!isCreated)return;
        if (mData == null || mData.size() == 0) {
            lv.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            lv.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {
            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_zone, viewGroup, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Question q = mData.get(i);
            holder.tv_question.setText(q.getTitle());
            UserInfo u = UserInfo.findByUserId(q.getUserId());
            holder.tv_author.setText(u.getName());
            holder.iv_sex.setImageResource(u.isSex() ? R.drawable.male : R.drawable.female);
            final List<Option> options = Option.findByQuestionId(q.getId());
            int count = 0;
            if (options == null || options.size() != 2) {
                return view;
            }
            holder.rb_option1.setText(options.get(0).getName());
            holder.rb_option2.setText(options.get(1).getName());

            holder.rg_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    Option option = null;
                    switch (checkedId) {
                        case R.id.rb_option1:
                            option = options.get(0);
                            break;
                        case R.id.rb_option2:
                            option = options.get(1);
                            break;
                    }
                    Toast.makeText(getActivity(), option.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        class ViewHolder {
            TextView tv_question, tv_author;
            ImageView iv_sex, iv_like;
            RadioGroup rg_options;
            RadioButton rb_option1, rb_option2;

            public ViewHolder(View v) {
                tv_question = (TextView) v.findViewById(R.id.tv_question);
                tv_author = (TextView) v.findViewById(R.id.tv_author);
                rg_options = (RadioGroup) v.findViewById(R.id.rg_options);
                rb_option1 = (RadioButton) v.findViewById(R.id.rb_option1);
                rb_option2 = (RadioButton) v.findViewById(R.id.rb_option2);
                iv_like = (ImageView) v.findViewById(R.id.iv_like);
                iv_sex = (ImageView) v.findViewById(R.id.iv_sex);
            }

        }
    }
}
