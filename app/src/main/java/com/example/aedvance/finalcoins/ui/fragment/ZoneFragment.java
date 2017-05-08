package com.example.aedvance.finalcoins.ui.fragment;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseFragment;
import com.example.aedvance.finalcoins.bean.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aedvance on 2017/5/6.
 */

public class ZoneFragment extends BaseFragment {

    List<Questions> mData = new ArrayList<>();

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

        mData = new ArrayList<Questions>() {
            {
                for (int i = 0; i < 16; i++) {
                    Questions q = new Questions();
                    q.setAuthor("Author " + i);
                    q.setQuestion("Question " + i);
                    add(q);
                }
            }
    };

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
            Questions q = mData.get(i);
            holder.tv_question.setText(q.getQuestion());
            holder.tv_author.setText(q.getAuthor());
            return view;
        }

        class ViewHolder {
            TextView tv_question, tv_author;
            Button btn_eat, btn_uneat;
            ImageView iv_love;

            public ViewHolder(View v) {
                tv_question = (TextView) v.findViewById(R.id.tv_question);
                tv_author = (TextView) v.findViewById(R.id.tv_author);
                btn_eat = (Button) v.findViewById(R.id.btn_eat);
                btn_uneat = (Button) v.findViewById(R.id.btn_uneat);
                iv_love = (ImageView) v.findViewById(R.id.iv_love);
            }

        }
    }
}
