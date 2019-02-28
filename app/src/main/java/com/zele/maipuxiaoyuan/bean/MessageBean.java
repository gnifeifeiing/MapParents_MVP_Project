package com.zele.maipuxiaoyuan.bean;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MessageBean {

    /**
     * claDate : 2016/09/09
     * claMsg : ............
     * claNoc : 0
     * jobDate : 2016/09/09
     * jobMsg : 语文
     * jobNoc : 0
     * schNoc : 0
     * sysNoc : 0
     */
	private String customs;
	private String custom;
	private ParmsgBean parmsg;
	
    public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}
    public String getCustoms() {
		return customs;
	}

	public void setCustoms(String customs) {
		this.customs = customs;
	}

	public ParmsgBean getParmsg() {
        return parmsg;
    }

    public void setParmsg(ParmsgBean parmsg) {
        this.parmsg = parmsg;
    }

    public static class ParmsgBean {
        private String claDate;
        private String claMsg;
        private int claNoc;
        private String jobDate;
        private String jobMsg;

        public String getSchDate() {
            return schDate;
        }

        public void setSchDate(String schDate) {
            this.schDate = schDate;
        }

        public String getSchMsg() {
            return schMsg;
        }

        public void setSchMsg(String schMsg) {
            this.schMsg = schMsg;
        }

        public String getSysDate() {
            return sysDate;
        }

        public void setSysDate(String sysDate) {
            this.sysDate = sysDate;
        }

        public String getSysMsg() {
            return sysMsg;
        }

        public void setSysMsg(String sysMsg) {
            this.sysMsg = sysMsg;
        }

        private int jobNoc;
        private int schNoc;
        private int sysNoc;
        private String schDate;
        private String schMsg;
        private String sysDate;
        private String sysMsg;

        public String getClaDate() {
            return claDate;
        }

        public void setClaDate(String claDate) {
            this.claDate = claDate;
        }

        public String getClaMsg() {
            return claMsg;
        }

        public void setClaMsg(String claMsg) {
            this.claMsg = claMsg;
        }

        public int getClaNoc() {
            return claNoc;
        }

        public void setClaNoc(int claNoc) {
            this.claNoc = claNoc;
        }

        public String getJobDate() {
            return jobDate;
        }

        public void setJobDate(String jobDate) {
            this.jobDate = jobDate;
        }

        public String getJobMsg() {
            return jobMsg;
        }

        public void setJobMsg(String jobMsg) {
            this.jobMsg = jobMsg;
        }

        public int getJobNoc() {
            return jobNoc;
        }

        public void setJobNoc(int jobNoc) {
            this.jobNoc = jobNoc;
        }

        public int getSchNoc() {
            return schNoc;
        }

        public void setSchNoc(int schNoc) {
            this.schNoc = schNoc;
        }

        public int getSysNoc() {
            return sysNoc;
        }

        public void setSysNoc(int sysNoc) {
            this.sysNoc = sysNoc;
        }
    }
}
