package com.example.aedvance.finalcoins.bean;

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
public class Interest extends DataSupportCompat<Interest> {

    private long masterId;
    private long slaveId;

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public long getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(long slaveId) {
        this.slaveId = slaveId;
    }

    public static int getInterestCountByUserId(long userId) {
        List<Interest> interests = where("masterId = ?", userId + "").find(Interest.class);
        return interests == null ? 0 : interests.size();
    }
}
