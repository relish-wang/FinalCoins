package com.example.aedvance.finalcoins.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.util.merge.MergeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class OptionsAdapter extends BaseAdapter {


    private List<String> mOptions;

    private List<String> cache;


    public OptionsAdapter(List<String> options) {
        mOptions = options;
        cache = new ArrayList<>();
        cache.addAll(options);
        if (mOptions == null) {
            mOptions = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public String getItem(int position) {
        return mOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
            holder = new ViewHolder();
            holder.et_option = (EditText) convertView.findViewById(R.id.et_option);
            holder.btn_add = (Button) convertView.findViewById(R.id.btn_add);
            holder.btn_sub = (Button) convertView.findViewById(R.id.btn_sub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.et_option.setHint("选项" + (position + 1));
        holder.et_option.setText(mOptions.get(position));
        OptionNameWatcher watcher = new OptionNameWatcher(holder.et_option, position);
        holder.et_option.addTextChangedListener(watcher);
        AddAndSubListener listener = new AddAndSubListener(position);
        holder.btn_add.setOnClickListener(listener);
        holder.btn_sub.setOnClickListener(listener);
        return convertView;
    }

    private class ViewHolder {
        EditText et_option;
        Button btn_add;
        Button btn_sub;
    }

    /**
     * 加减监听
     */
    private class AddAndSubListener implements View.OnClickListener {

        private int position;

        AddAndSubListener(int p) {
            position = p;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add:
                    mOptions.add(position, "");
                    notifyDataSetChanged();
                    break;
                case R.id.btn_sub:
                    mOptions.remove(position);
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    private class OptionNameWatcher implements TextWatcher {

        private int position;

        private EditText editText;

        OptionNameWatcher(EditText et, int p) {
            editText = et;
            position = p;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            List<String> temp = new ArrayList<>(position + 1);
//            temp.set(position, editText.getText().toString().trim());
//            if (cache == null) {
//                cache = new ArrayList<>();
//                cache.addAll(temp);
//            } else {
//                List<String> merge = new MergeUtil<String>().mergeTwo(cache, temp, "");
//                cache.clear();
//                cache.addAll(merge);
//            }
        }
    }

//    @Override
//    public void notifyDataSetChanged() {
//        mOptions.clear();
//        mOptions.addAll(cache);
//        super.notifyDataSetChanged();
//    }
}
