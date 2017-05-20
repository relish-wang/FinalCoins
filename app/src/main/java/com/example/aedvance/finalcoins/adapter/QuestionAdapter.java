package com.example.aedvance.finalcoins.adapter;

import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.ui.activity.QuestionActivity;
import com.example.aedvance.finalcoins.util.EasyCallback;

import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends BaseAdapter {

    private List<Question> mData;

    private EasyCallback callback;

    private UserInfo mUser;
    private boolean isCurrentUser = false;

    public QuestionAdapter(List<Question> data, EasyCallback callback,UserInfo u) {
        this.mData = data;
        this.callback = callback;
        this.mUser = u;
        isCurrentUser = mUser.getId()==App.USER.getId();
    }

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
    public View getView(int i, View view, final ViewGroup viewGroup) {
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
                QuestionActivity.start(viewGroup.getContext(), q);
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
                    Toast.makeText(viewGroup.getContext(), "未回答的帖子不可收藏！", Toast.LENGTH_SHORT).show();
                    return;
                }
                final boolean isCollected = answer.isCollected();
                answer.setCollected(!isCollected);
                answer.saveAsync().listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        holder.iv_like.setImageResource(isCollected ?
                                R.drawable.ic_unlike : R.drawable.ic_like);
                        if (callback != null) callback.onSuccess(null);
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
                                            if (callback != null) callback.onSuccess("");
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

    public void setData(List<Question> questions) {
        mData = questions;
        if(mData==null){
            mData=new ArrayList<>();
        }
        notifyDataSetChanged();
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