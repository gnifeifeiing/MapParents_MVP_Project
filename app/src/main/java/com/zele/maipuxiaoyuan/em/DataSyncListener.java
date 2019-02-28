package com.zele.maipuxiaoyuan.em;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/12/8 14:14
 */

public interface DataSyncListener {
    /**
     * sync complete
     * @param success true：data sync successful，false: failed to sync data
     */
    void onSyncComplete(boolean success);
}
