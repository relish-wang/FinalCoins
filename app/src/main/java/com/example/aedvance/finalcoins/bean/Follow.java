package com.example.aedvance.finalcoins.bean;

import com.example.aedvance.finalcoins.App;

import java.util.List;

import wang.relish.litepalcompat.DataSupportCompat;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Follow extends DataSupportCompat<Follow> {

    private long masterId;
    private long followerId;

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public static int getInterestCountByUserId(long userId) {
        List<Follow> follows = where("followerId = ?", userId + "").find(Follow.class);
        return follows == null ? 0 : follows.size();
    }

    public static boolean amIFollowing(long userId) {
        List<Follow> follows = where("masterId = ? and followerId = ?", userId + "", App.USER.getId() + "").find(Follow.class);
        return follows != null && follows.size() > 0;
    }

    public static Follow findFollowByMasterIdAndUser(long userId) {
        List<Follow> follows = where("masterId = ? and followerId = ?", userId + "", App.USER.getId() + "").find(Follow.class);
        if (follows == null || follows.size() == 0) {
            return null;
        }
        Follow result = follows.get(0);
        for (int i = 1; i < follows.size(); i++) {
            follows.get(i).delete();
        }
        return result;
    }

    public static int unFollow(long userId) {
        Follow f = findFollowByMasterIdAndUser(userId);
        if (f != null) {
            return f.delete();
        }
        return 1;
    }
}
