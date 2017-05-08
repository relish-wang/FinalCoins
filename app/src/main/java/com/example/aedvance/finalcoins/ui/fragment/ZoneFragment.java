package com.example.aedvance.finalcoins.ui.fragment;

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

public class ZoneFragment extends BaseFragment {

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


    @Override
    public void onResume() {
        super.onResume();
        mData = Question.findAll(Question.class);
        update();
    }

    private void update() {
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
            final List<Option> options = Option.findByQuestionId(q.getId());
            int count = 0;
            for (Option option : options) {
                RadioButton button = new RadioButton(getActivity());
                button.setId(count++);
                button.setText(option.getName());
                holder.rg_options.addView(button);
            }
            holder.rg_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    Option option = options.get(checkedId);
                    Toast.makeText(getActivity(), option.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        class ViewHolder {
            TextView tv_question, tv_author;
            ImageView iv_love;
            RadioGroup rg_options;

            public ViewHolder(View v) {
                tv_question = (TextView) v.findViewById(R.id.tv_question);
                tv_author = (TextView) v.findViewById(R.id.tv_author);
                rg_options = (RadioGroup) v.findViewById(R.id.rg_options);
                iv_love = (ImageView) v.findViewById(R.id.iv_love);
            }

        }
    }
}
