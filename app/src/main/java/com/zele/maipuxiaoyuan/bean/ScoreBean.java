package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      我的积分
 * Autour：          LF
 * Date：            2018/11/21 11:17
 */

public class ScoreBean extends BaseBean {

    /**
     {
         "result":"100",
         "datas":{
             "gold":"47.0",
             "integral":"13"
        }
     }
     */

    private Score datas;

    public Score getDatas() {
        return datas;
    }

    public void setDatas(Score datas) {
        this.datas = datas;
    }

    public static class Score{
        private String gold;
        private String integral;

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
