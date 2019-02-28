package com.zele.maipuxiaoyuan.bean;

import java.util.List;


public class RelationVoBean extends BaseBean{

	public List<Relationo> relates;

	public List<Relationo> getRelates() {
		return relates;
	}

	public void setRelates(List<Relationo> relates) {
		this.relates = relates;
	}

	public static class Relationo {
		
		private String id;
		private String chosign;
		private String code;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getChosign() {
			return chosign;
		}
		public void setChosign(String chosign) {
			this.chosign = chosign;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}
