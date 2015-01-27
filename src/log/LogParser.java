package log;

import java.util.HashMap;
import java.util.Set;

import parser.ILogParser;

public class LogParser implements ILogParser{

	public int typeid = 0;
	
	public LogParser(String _log){
		this.setTypeID(_log);			
	}
	
	private void setTypeID(String _log){
		if(_log!=null){
			if(_log.contains("|charge order from MS|"))
				this.typeid = 1;
			if(_log.contains("|query order from MS|"))
				this.typeid = 2;
			if(_log.contains("|refund order from MS|"))
				this.typeid = 3;
			if(_log.contains("|charge order result|"))
				this.typeid = 11;
			if(_log.contains("|query order result|"))
				this.typeid = 21;
			if(_log.contains("|refund order result|"))
				this.typeid = 31;
			
			if(_log.contains("GET /TvPayWeb/order.jsp"))
				this.typeid = 7;
			if(_log.contains("POST") && _log.contains(".action")){
				this.typeid = 9;
			}
			
		}else{
			this.typeid = 5;
		}
	}
	
	@Override
	public HashMap<String, Object> getLogObj(String _log, String _prefix) {
		
		if(_log.contains("[cat]") || _log.contains("[acc]")){
			_log = _log.substring(5,_log.length());
		}
		
		HashMap<String, Object> logObj = new HashMap<String, Object>();		
		if(this.typeid==1 || this.typeid==2 || this.typeid==3){
			String[] ele = _log.split("\\|");
//			for(String e:ele){
//				System.out.println("ele = "+ e);
//			}		
			logObj.put("time", "");
			logObj.put("type", this.typeid);
			logObj.put("orderNum", "");
			logObj.put("commName", "");
			logObj.put("exIP", "");	
			logObj.put("message", _log);
			if(ele.length==6){
				logObj.put("time", ele[0].trim());
				logObj.put("type", this.typeid);
				logObj.put("orderNum", ele[2].trim());
				logObj.put("commName", ele[4].trim());
				logObj.put("exIP", ele[5].trim());	
				logObj.put("message", _log);
				Set<String> keys = logObj.keySet();
				for(String key:keys)
					System.out.println("key="+key+", value="+logObj.get(key));
			}else
				System.out.println("_log(type=1) format error, pls inv...");			
		}
		
		if(this.typeid==11){
			//2014-12-16 09:49:12  |charge order result|YST75190659|01|000000000010|E7|10.0.49.61|621785*********5642|中国银行
			String[] ele = _log.split("\\|");
//			for(String e:ele){
//				System.out.println("ele = "+ e);
//			}
			logObj.put("amount", "");
			logObj.put("time", _log.substring(0, _log.indexOf("|")).trim());
			logObj.put("type", this.typeid);
			logObj.put("orderNum", "");				
			logObj.put("respCode", "");
			logObj.put("inIP", "");
			logObj.put("cardNum", "");
			logObj.put("bank", "");		
			logObj.put("message", _log);
			if(ele.length==9){
				if(ele[4].equals("") || ele[4]==null || ele[4].equals("null") || ele[4].length()==0 ){
					logObj.put("amount", "0");
				}else{
					long amount = Long.parseLong(ele[4]);
					String sAmount = amount+"";
					if(amount<100)
						logObj.put("amount", "0."+amount);
					else
						logObj.put("amount", sAmount.substring(0, (sAmount.length()-2))+"."+sAmount.substring(sAmount.length()-2,sAmount.length()));
				}
					
			
				logObj.put("time", ele[0].trim());
				logObj.put("type", this.typeid);
				logObj.put("orderNum", ele[2].trim());				
				logObj.put("respCode", ele[5].trim());
				logObj.put("inIP", ele[6].trim());
				logObj.put("cardNum", ele[7].trim());
				logObj.put("bank", ele[8].trim());		
//				logObj.put("message", _log);
//				Set<String> keys = logObj.keySet();
//				for(String key:keys)
//					System.out.println("key="+key+", value="+logObj.get(key));
			}else
				System.out.println("_log(type=11) format error, pls inv...");
		}
		
		if(this.typeid==21||this.typeid==31){
			//	2014-12-16 09:47:52  |query order result|YST75190659|01||84|10.0.49.61
			//	2014-12-16 09:59:09  |refund order result|YST81834873|04|000000000010|00|10.0.49.61
			String[] ele = _log.split("\\|");
//			for(String e:ele){
//				System.out.println("ele = "+ e);
//			}			
			logObj.put("time", _log.substring(0, _log.indexOf("|")).trim());			
			logObj.put("type", "");
			logObj.put("orderNum", "");
			logObj.put("inIP", "");		
			logObj.put("respCode", "");	
			logObj.put("amount", "");
			logObj.put("cardNum", "");
			logObj.put("bank", "");	
			logObj.put("message", _log);
			if(ele.length==7){
				logObj.put("time", ele[0].trim());
				logObj.put("type", this.typeid);
				logObj.put("orderNum", ele[2].trim());
				logObj.put("inIP", ele[6].trim());		
				logObj.put("respCode", ele[5].trim());	
//				logObj.put("amount", "");
//				logObj.put("cardNum", "");
//				logObj.put("bank", "");	
//				logObj.put("message", _log);
				Set<String> keys = logObj.keySet();
				for(String key:keys)
					System.out.println("key="+key+", value="+logObj.get(key));
			}else
				System.out.println("_log(type=21||31) format error, pls inv...");
			
		}		
		
		return logObj;
	}

	@Override
	public String getIP(String _log, int _typeid) {
		// TODO Auto-generated method stub
		// typeid=1: getRealExternalUserIP from tomcat catalina log,
		// typeid=2: getRealExternalUserIP from tomcat access log,
		// typeid=3: getLocalServerIP from tomcat catalina log,
		
		String ip="";
		
		
		return ip;
	}

	public static void main(String[] args){
		String  log = "";
		//单笔消费成功
//		log = "2014-12-11 14:03:52  |charge order from MS|YST35762631|01|MICROSOFTBILLING|10.0.100.20";
//		log = "2014-12-11 14:06:57  |charge order result|YST35762631|000000000110|01|00|10.0.49.101|623058*********1669|平安银行";
//		log = "2014-12-11 14:19:58  |query order from MS|YST35762631|01||10.0.100.20";
//		log = "2014-12-11 14:23:11  |refund order from MS|YST03496406|04|MICROSOFT BILLING|10.0.100.20";
//		log = "2014-12-11 14:19:58  |query order result|YST35762631|01|10.0.49.101";
//		log = "2014-12-11 14:23:11  |refund order result|YST03496406|04|10.0.49.101";
//		log = "2014-12-16 09:59:09  |refund order result|YST81834873|04|000000000010|00|10.0.49.61";
		log = "2014-12-16 09:47:52  |query order result|YST75190659|01||84|10.0.49.61";
		
		LogParser lp = new LogParser(log);
		lp.getLogObj(log, "");
		
	}

}
