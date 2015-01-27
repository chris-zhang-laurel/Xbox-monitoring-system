package model;

import util.Constant;

public class Access {
	
	private String ip;
	private String method;
	private String respCode;
	private String time;
	private String url;
	private String message;
	
	public Access(String _message){
		
		if(_message.contains("[cat]") || _message.contains("[acc]")){
			_message = _message.substring(5,_message.length());
			this.setMessage(_message);
		}
		
		if(_message.contains("[") && _message.contains("+0800]")){
			String head = _message.substring(0,_message.indexOf("["));
			String tail = _message.substring(_message.indexOf("]"),_message.length());
			this.time = _message.substring(_message.indexOf("[")+1, 
					_message.indexOf("+0800]"));
			this.time = this.time.trim();
//			this.time = Constant.getUTCtime(this.time, 7);
//			this.time = Constant.getUTCtime(this.time, 6);
			
			String[] hs = head.split(" ");
			 if(hs.length>1){
				 if(head.startsWith("- ")){
					 this.setIp(hs[1].trim());//health check 
				 }else{
					 this.setIp(hs[0].trim());//real external visit
				 }				 
			 }
			
			String[] ts = tail.split(" ");
			 if(ts.length>4){
				 this.setMethod(ts[1].trim());
				 this.setRespCode(ts[4].trim());
				 this.setUrl(ts[2].trim());
				 System.out.println("method="+ts[1]);
				 System.out.println("url="+ts[2]);
				 System.out.println("respcode="+ts[4]);
				 System.out.println("ip="+this.getIp());
				System.out.println("time="+this.time);
			 }else
				 System.out.println("access log lack of column, pls inv...");
			
		}else{
			System.out.println("access logs data error, pls inv...");
			this.setRespCode("NULL");
			this.setTime(Constant.getNow());
		}
		
		
	}
	
	
	
	

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}



	public static void main(String[] args){
//		String a ="- 10.0.48.101 - - [28/Nov/2014:17:56:51 +0800] GET /TvPayWeb/order.jsp HTTP/1.1 200 3848";
		String a = "220.176.163.198 10.0.48.106 - - [19/Dec/2014:01:29:25 +0800] POST /TvPayWeb/api/sendAction.action HTTP/1.1 200 417";
		new Access(a);
	}
}
