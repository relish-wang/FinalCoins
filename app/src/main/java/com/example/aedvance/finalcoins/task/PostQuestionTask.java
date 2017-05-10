package com.example.aedvance.finalcoins.task;

import android.os.AsyncTask;

import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.util.EasyCallback;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PostQuestionTask extends AsyncTask<Void, Void, Boolean> {

    private Question q;
    private Option o1, o2;
    private EasyCallback callback;

    public PostQuestionTask(Question q, Option o1, Option o2, EasyCallback<Void> callback) {
        this.q = q;
        this.o1 = o1;
        this.o2 = o2;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return q.save();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            new PostTwoOptionTask().execute();
        } else {
            callback.onFailed("发布失败，请稍后重试");
        }
    }

    private class PostTwoOptionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Question newQ = Question.findByTitle(q.getTitle());
            if (newQ == null) {
                return false;
            }
            long maxId = Option.maxId();
            o1.setId(maxId + 1);
            o1.setOppoId(maxId + 2);
            o2.setId(maxId + 2);
            o2.setOppoId(maxId + 1);
            o1.setQuestionId(newQ.getId());
            o2.setQuestionId(newQ.getId());
            return o1.save() && o2.save();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                //noinspection unchecked
                callback.onSuccess(null);
            } else {
                callback.onFailed("发布失败，请稍后重试");
            }
        }
    }
}
