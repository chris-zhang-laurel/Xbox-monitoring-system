package util;

import java.awt.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import redis.clients.jedis.Jedis;

public class Constant {
	
	public static String[] validLogSigns = {
		"微软传过来的订单",//获得订单号和开始时间
//		"客户端地址为",//获得客户IP
//		"后端返回的报文",//获得查询结果和时间
//		"退货后端返回",//获得退货结果和时间
		"消费后端返回"};//获得消费结果和时间
	
	
	public static int judgeLogType(String _logValue){
		//access=1,catalina=2;
		int type = 0;		
		if(_logValue.contains("GET /TvPayWeb/order.jsp"))
			type=0;	
		else{
			if(_logValue.contains("message") || _logValue.contains("[acc]") ){
				if(_logValue.contains("accesslog") || _logValue.contains("[acc]") )
					type = 1;
//				if(_logValue.contains("catalinalog")){
//					type = 2;
					if(_logValue.contains("微软传过来的订单"))
						type=21;					
					if(_logValue.contains("消费后端返回"))
						type=22;
					
//				}					
			}else{
				if(_logValue.contains("微软传过来的订单"))
					type=21;					
				if(_logValue.contains("消费后端返回"))
					type=22;
			}
		}
		System.out.println("!!!!!!　　　type="+type);
		return type;
	}
	
	public static String getNow(){
		Date date = new Date();
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -8);
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return format1.format(c.getTime());
	}
	public static String getUTCindexTime(String _closetime) {
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		try {
			date = format1.parse(_closetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -8);
		String time = format1.format(c.getTime());
		System.out.println("---timeStamp="+time);		
		return time;
	}
	public static String getUTCindexDate(String _closetime) {
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date date = new Date();
		try {
			date = format1.parse(_closetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -8);
		String timeStamp = format2.format(c.getTime());
		System.out.println("---timeStamp="+timeStamp);		
		return timeStamp;
	}
	public static String getUTCdate(String _closetime) {
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format3 = new SimpleDateFormat(
				"-yyyy.MM.dd");	
		Date date = new Date();
		try {
			date = format1.parse(_closetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -8);
		String utcdate = format3.format(c.getTime());
		System.out.println("---UTCdate="+utcdate);		
		return utcdate;
	}
	
	public static String getUTCindexDate(String _closetime, int _i){
		SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat format3 = new SimpleDateFormat(
				"-yyyy.MM.dd");		
		SimpleDateFormat format5 = new SimpleDateFormat(
				"dd/MMM/yyyy:HH:mm:ss",Locale.ENGLISH);
		if(_closetime.length()>10){
			Date date = new Date();
			try {
				date = format5.parse(_closetime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.util.Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, -8);
			String indexDate = format3.format(c.getTime());
			System.out.println("---IndexDate="+indexDate);
			String closeTime = format1.format(c.getTime());
			System.out.println("---closeTime="+closeTime);
			String timeStamp = format2.format(c.getTime());
			System.out.println("---timeStamp="+timeStamp);
			if(_i==1)
				return indexDate;
			else{
				if(_i==2)
					return closeTime;
				else
					return timeStamp;
			}
		}else
			return getNow();
		
		
			
	}
	
	public static String getUTCtime(String _closetime, int _i){
//		System.out.println("getUTCtime: _closetime"+_closetime);
		String utcTime = "";
		SimpleDateFormat format1 = new SimpleDateFormat(
//				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat(
//				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		SimpleDateFormat format3 = new SimpleDateFormat(
				"-yyyy.MM.dd");
		
		SimpleDateFormat format5 = new SimpleDateFormat(
				"dd/MMM/yyyy:HH:mm:ss",Locale.ENGLISH);
		
		Date date = new Date();
		try {
			if(_i<5)
				date = format1.parse(_closetime);
			else
				date = format5.parse(_closetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, -8);
		
		
		if(_i==5 || _i==1){
			String indexTime = format3.format(c.getTime());
//			System.out.println("---timeStamp="+indexTime);
			return indexTime;
		}
		if(_i==7){
			utcTime = format1.format(c.getTime());
			System.out.println("---timeUTC="+utcTime);
			return utcTime;
		}
		else{	
			String timeStamp = format2.format(c.getTime());
			return timeStamp;
		}
		
		
		
	}
	
//	public static void testInsertData(Jedis _jedis1){
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 00:11:50  微软传过来的订单：acqCode=&backEndUrl=https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx&charset=UTF-8&commodityDiscount=&commodityName=《无冬Online》 Zen&commodityQuantity=&commodityUnitPrice=&commodityUrl=&customerIp=127.0.0.1&customerName=&defaultBankNumber=&defaultPayType=&frontEndUrl=$RUPlaceholder$&merAbbr=Microsoft&merCode=&merId=839310048990017&merReserved=&orderAmount=12100&orderCurrency=156&orderNumber=SP000F4CQAB5&orderTime=20141128001224&origQid=&transTimeout=900000&transType=01&transferFee=&version=1.0.0&signMethod=MD5&signature=de1e748dc9bd6cdae9035c663eb2e7ed\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 00:13:27  消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP000F4CQAB5</orderNum><acctNum>439226******1648</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV186210799331648</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>708013</traceNum><date>1128001156</date><purchAmount>000000012100</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number/></ChInfo><CntInfo><mobile/></CntInfo><channelType>16</channelType><PubKeyIndex>001</PubKeyIndex><tranCheckMode>0100010010000000</tranCheckMode><SecRelContInfo></SecRelContInfo><Extension>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D12100%26orderCurrency%3D156%26orderNumber%3DSP000F4CQAB5%26qid%3D112835280411280011560%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128001156%26settleAmount%3D12100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D708681%26traceTime%3D1128001156%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D2c657caa4cd6d95c43ddde5c73924ce2</Extension><Resp><respCode>00</respCode><respInfo></respInfo></Resp><STPP><traceNum>708681</traceNum><date>1128001156</date></STPP><settledate>1128</settledate><ExtensionBack>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D12100%26orderCurrency%3D156%26orderNumber%3DSP000F4CQAB5%26qid%3D112835280411280011560%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128001156%26settleAmount%3D12100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D708681%26traceTime%3D1128001156%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D2c657caa4cd6d95c43ddde5c73924ce2</ExtensionBack><backEndUrl>https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx</backEndUrl></TVPaybody><SecureData/><Mac></Mac></TVPay>\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 01:03:42  微软传过来的订单：acqCode=&backEndUrl=https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx&charset=UTF-8&commodityDiscount=&commodityName=《无冬Online》 Zen&commodityQuantity=&commodityUnitPrice=&commodityUrl=&customerIp=127.0.0.1&customerName=&defaultBankNumber=&defaultPayType=&frontEndUrl=$RUPlaceholder$&merAbbr=Microsoft&merCode=&merId=839310048990017&merReserved=&orderAmount=3100&orderCurrency=156&orderNumber=SP000UQNFAFN&orderTime=20141128010416&origQid=&transTimeout=900000&transType=01&transferFee=&version=1.0.0&signMethod=MD5&signature=9a2ac6bdfd045aaac868678b3337c529\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 01:04:30  消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP000UQNFAFN</orderNum><acctNum>525746******4976</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV182998596934976</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>704939</traceNum><date>1128010349</date><purchAmount>000000003100</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number/></ChInfo><CntInfo><mobile/></CntInfo><channelType>16</channelType><PubKeyIndex>001</PubKeyIndex><tranCheckMode>0000010010000000</tranCheckMode><SecRelContInfo></SecRelContInfo><Extension>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D3100%26orderCurrency%3D156%26orderNumber%3DSP000UQNFAFN%26qid%3D112835331111280103490%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128010349%26settleAmount%3D3100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D711723%26traceTime%3D1128010349%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D63e51dccd4e8679a21f996576826611c</Extension><Resp><respCode>00</respCode><respInfo></respInfo></Resp><STPP><traceNum>711723</traceNum><date>1128010349</date></STPP><settledate>1128</settledate><ExtensionBack>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D3100%26orderCurrency%3D156%26orderNumber%3DSP000UQNFAFN%26qid%3D112835331111280103490%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128010349%26settleAmount%3D3100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D711723%26traceTime%3D1128010349%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D63e51dccd4e8679a21f996576826611c</ExtensionBack><backEndUrl>https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx</backEndUrl></TVPaybody><SecureData/><Mac></Mac></TVPay>\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 01:26:35  微软传过来的订单为：charset=UTF-8&merId=839310048990017&merReserved=&orderNumber=SP005NFCL0M6&orderTime=20141127170425&transType=01&version=1.0.0&signMethod=MD5&signature=4164d41adfd2745a48c90bebe752e2cf\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 01:46:34  微软传过来的订单为：charset=UTF-8&merId=839310048990017&merReserved=&orderNumber=SP006QDSAJPD&orderTime=20141127172823&transType=01&version=1.0.0&signMethod=MD5&signature=bc3f15896292fd8f1a1c4b6b2f224fd1\"}");
//		_jedis1.lpush("logstash:redis", "{\"message\":\"2014-11-28 09:23:14  消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP006E2KARXF</orderNum><acctNum>623058*********1669</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV6230581669</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>192007</traceNum><date>1128092305</date><purchAmount>000000000300</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number/></ChInfo><CntInfo><mobile/></CntInfo><channelType>16</channelType><PubKeyIndex>001</PubKeyIndex><tranCheckMode>0000000000000000</tranCheckMode><SecRelContInfo></SecRelContInfo><Extension>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D300%26orderCurrency%3D156%26orderNumber%3DSP006E2KARXF%26qid%3D112835525511280923050%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128092305%26settleAmount%3D300%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D721625%26traceTime%3D1128092305%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D9f15832900ca044c75d028b11fe38763</Extension><Resp><respCode>00</respCode><respInfo></respInfo></Resp><STPP><traceNum>721625</traceNum><date>1128092305</date></STPP><settledate>1128</settledate><ExtensionBack>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D300%26orderCurrency%3D156%26orderNumber%3DSP006E2KARXF%26qid%3D112835525511280923050%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128092305%26settleAmount%3D300%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D721625%26traceTime%3D1128092305%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D9f15832900ca044c75d028b11fe38763</ExtensionBack><backEndUrl>https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx</backEndUrl></TVPaybody><SecureData/><Mac></Mac></TVPay>\"}");
//	}

}
