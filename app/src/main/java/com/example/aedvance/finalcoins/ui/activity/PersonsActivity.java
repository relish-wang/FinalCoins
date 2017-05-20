package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseActivity;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Follow;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/20
 *     desc   : xxx的关注的人的列表
 *     version: 1.0
 * </pre>
 */
public class PersonsActivity extends BaseActivity{


    private UserInfo mUser;

    public static void start(Context context, UserInfo u){
        Intent intent = new Intent(context,PersonsActivity.class);
        intent.putExtra("userInfo",u);
        context.startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_persons;
    }

    @Override
    protected void parseIntent(Intent intent) {
        mUser = (UserInfo) intent.getSerializableExtra("userInfo");
        if(mUser==null){
            finish();
        }
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState, Toolbar mToolbar) {
        mToolbar.setTitle("我的关注");
    }

    List<UserInfo> mData = new ArrayList<>();
    UserAdapter mAdapter;
    ListView lv_users;
    TextView tv_no_data;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        lv_users = (ListView) findViewById(R.id.lv_users);
        mAdapter = new UserAdapter();
        lv_users.setAdapter(mAdapter);
        request();
    }

    private void request() {
        new AsyncTask<Void, Void, List<UserInfo>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoading(true);
            }

            @Override
            protected List<UserInfo> doInBackground(Void... params) {
                return UserInfo.findMasterUsersByFollowerId(mUser.getId());
            }

            @Override
            protected void onPostExecute(List<UserInfo> userInfos) {
                super.onPostExecute(userInfos);
                showLoading(false);
                if(userInfos==null){
                    userInfos=new ArrayList<>();
                }
                mData = userInfos;
                mAdapter.notifyDataSetChanged();
                update();
            }
        }.execute();
    }

    private void update() {
        boolean noData = mData==null||mData.size()==0;
        lv_users.setVisibility(!noData?View.VISIBLE:View.GONE);
        tv_no_data.setVisibility(noData?View.VISIBLE:View.GONE);
    }

    private class UserAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public UserInfo getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final UserInfo user = mData.get(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonActivity.start(parent.getContext(), user);
                }
            });

            holder.tv_name.setText(user.getName());
            holder.iv_sex.setImageResource(user.isSex() ? R.drawable.male : R.drawable.female);
            holder.tv_question_count.setText(markUpCount("帖子", Question.getQuestionCountById(user.getId())));
            holder.tv_collect_count.setText(markUpCount("收藏", Answer.getCollectCountByUserId(user.getId())));
            holder.tv_follow_count.setText(markUpCount("关注", Follow.getFollowingCountByUserId(user.getId())));
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            ImageView iv_sex;
            TextView tv_question_count;
            TextView tv_collect_count;
            TextView tv_follow_count;

            public ViewHolder(View v) {
                tv_name = (TextView) v.findViewById(R.id.tv_name);
                iv_sex = (ImageView) v.findViewById(R.id.iv_sex);
                tv_question_count = (TextView) v.findViewById(R.id.tv_question_count);
                tv_collect_count = (TextView) v.findViewById(R.id.tv_collect_count);
                tv_follow_count = (TextView) v.findViewById(R.id.tv_follow_count);
            }

        }
    }

    private static String markUpCount(String tag, int count) {
        return tag + "(" + count + ")";
    }
}
