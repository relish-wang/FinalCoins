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

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.ui.activity.QuestionActivity;

import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
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
                Collections.sort(questions);
                mData = questions;
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

    private class MyAdapter extends BaseAdapter {

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
            final ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_zone, viewGroup, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final Question q = mData.get(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionActivity.start(getActivity(), q);
                }
            });

            holder.tv_question.setText(q.getTitle());
            UserInfo u = UserInfo.findByUserId(q.getUserId());
            holder.tv_author.setText(u.getName());
            holder.iv_sex.setImageResource(u.isSex() ? R.drawable.male : R.drawable.female);
            final List<Option> options = Option.findByQuestionIdGroupByOptionId(q.getId());
            //取不到选项或者选项取不全
            if (options == null || options.size() != 2) {
                throw new NullPointerException("option must be two!");
            }
            Option option1 = options.get(0);
            Option option2 = options.get(1);
            long count1 = option1.getCount();
            long count2 = option2.getCount();
            holder.rb_option1.setText(option1.getName());
            holder.rb_option2.setText(option2.getName());

            final Answer answer = Answer.findAnswerByUserIdAndQuestionId(App.USER.getId(), q.getId());

            holder.iv_like.setImageResource(answer == null || !answer.isCollected() ?
                    R.drawable.ic_unlike : R.drawable.ic_like);
            holder.iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answer == null) {
                        Toast.makeText(getActivity(), "未回答的帖子不可收藏！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final boolean isCollected = answer.isCollected();
                    answer.setCollected(!isCollected);
                    answer.saveAsync().listen(new SaveCallback() {
                        @Override
                        public void onFinish(boolean success) {
                            holder.iv_like.setImageResource(isCollected ?
                                    R.drawable.ic_unlike : R.drawable.ic_like);
                            update();
                        }
                    });
                }
            });

            if (answer != null) {
                holder.rg_options.setClickable(false);
                holder.rg_options.setEnabled(false);
                holder.rb_option1.setEnabled(false);
                holder.rb_option1.setClickable(false);
                holder.rb_option2.setEnabled(false);
                holder.rb_option2.setClickable(false);
                if (options.get(0).getId() == answer.getOptionId()) {
                    holder.rb_option1.setChecked(true);
                    holder.rb_option1.append("(" + count1 + "/" + (count1 + count2) + ")");
                } else {
                    holder.rb_option2.setChecked(true);
                    holder.rb_option2.append("(" + count2 + "/" + (count1 + count2) + ")");
                }
                return view;
            }
            holder.rg_options.setClickable(true);
            holder.rg_options.setEnabled(true);
            holder.rb_option1.setEnabled(true);
            holder.rb_option1.setClickable(true);
            holder.rb_option1.setChecked(false);
            holder.rb_option2.setChecked(false);
            holder.rb_option2.setEnabled(true);
            holder.rb_option2.setClickable(true);
            holder.rg_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    Option option = null;
                    switch (checkedId) {
                        case R.id.rb_option1:
                            if (!holder.rb_option1.isEnabled()) {
                                return;
                            }
                            option = options.get(0);
                            break;
                        case R.id.rb_option2:
                            if (!holder.rb_option2.isEnabled()) {
                                return;
                            }
                            option = options.get(1);
                            break;
                    }
                    Answer answer = new Answer();
                    answer.setUserId(App.USER.getId());
                    answer.setQuestionId(q.getId());
                    answer.setCollected(false);
                    assert option != null;
                    answer.setOptionId(option.getId());
                    final Option finalOption = option;
                    answer.saveAsync().listen(new SaveCallback() {
                        @Override
                        public void onFinish(boolean success) {
                            if (!success) return;
                            long count = finalOption.getCount();
                            finalOption.setCount(count + 1);
                            finalOption.saveAsync().listen(new SaveCallback() {
                                @Override
                                public void onFinish(boolean success) {
                                    if (success) {
                                        q.setUpdateTime(System.currentTimeMillis());
                                        q.saveAsync().listen(new SaveCallback() {
                                            @Override
                                            public void onFinish(boolean success) {
                                                update();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            });
            return view;
        }

        class ViewHolder {
            TextView tv_question, tv_author;
            ImageView iv_sex, iv_like;
            RadioGroup rg_options;
            RadioButton rb_option1, rb_option2;

            ViewHolder(View v) {
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
