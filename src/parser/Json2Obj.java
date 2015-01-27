package parser;

import util.Constant;
import model.TomcatAccess;

public class Json2Obj {
	
	public static TomcatAccess parse(String _jsonStr){
		
		TomcatAccess ta = new TomcatAccess();
		
		if(!_jsonStr.contains("@version")){			
			if(_jsonStr.contains("[cat]") || _jsonStr.contains("[acc]"))
				_jsonStr = _jsonStr.substring(5,_jsonStr.length());
			
			ta.setMessage(_jsonStr);
			ta.setVersion("1");

			ta.setHost("10.0.49.65");
		
			//ta.setTimestamp
			if(ta.getMessage().contains("- - [") && ta.getMessage().contains(" +0800]")){
				String timestamp = ta.getMessage();
				timestamp = timestamp.substring(timestamp.indexOf("- - [")+5,
						timestamp.indexOf(" +0800]") );
				timestamp = timestamp.trim();
				ta.setTimestamp(timestamp);
				
			}
			
			System.out.println("TOMCAT message"+ta.getMessage());			
			
		}
			
		return ta;
	}
	
	public static void main(String[] args){
	
		System.out.println(Constant.getUTCtime("2014-11-28 01:04:30",1));
	}

}
