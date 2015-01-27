package model;

import util.Constant;

public class ChargeOrderRz {
	
	private String time;
	private String orderNum;
	private String respCode;
	private String orderAmount;
	private String acctNum;
	
	
	public ChargeOrderRz(String _message){
		
		this.respCode = "NULL";
		this.orderAmount = "0";
		this.time = Constant.getNow();
		if(_message.length()<400)
			System.out.println("data error, pls inv...");
		
		if( _message.contains("消费后端返回")  ){
			_message = _message.trim();			
			this.time = _message.substring(0,_message.indexOf("消费后端返回"));
			this.time = this.time.trim();
			if(this.time.length()>20)
				this.time = this.time.substring(0,20);
			
			if( _message.contains("<orderNum>") && _message.contains("</orderNum>")  ){
				String on1 = _message.substring(_message.indexOf("<orderNum>")+10,
						_message.indexOf("</orderNum>"));
				on1 = on1.trim();
				this.orderNum = on1;
			}else{
				if(_message.contains("%26orderNumber%3D") && _message.contains("%26qid") ){
					String on2 = _message.substring(_message.indexOf("%26orderNumber%3D")+17,
							_message.indexOf("%26qid"));
					on2 = on2.trim();	
					this.orderNum = on2;
				}
			}
			
			if( _message.contains("<acctNum>") && _message.contains("</acctNum>") ){
				this.acctNum = _message.substring(_message.indexOf("<acctNum>")+9,
						_message.indexOf("</acctNum>"));
				this.acctNum = this.acctNum.replace("*", "_");
			}
			
			if(_message.contains("<purchAmount>") && _message.contains("</purchAmount>") ){
				String pa = _message.substring(_message.indexOf("<purchAmount>")+13,
						_message.indexOf("</purchAmount>"));
				pa = pa.trim();				
				this.orderAmount = pa;				
			}else{
				if(_message.contains("%26orderAmount%3D")&&_message.contains("%26orderCurrency%3D")){
					this.orderAmount = _message.substring(_message.indexOf("%26orderAmount%3D")+17,
							_message.indexOf("%26orderCurrency%3D"));
				}
			}
			if(this.orderAmount.length()>2){							
				Long amount = Long.parseLong(this.orderAmount);
				amount = amount/100; 
				this.orderAmount = amount +"";				
			}else
				this.orderAmount = "0."+this.orderAmount;
			
			
			if(_message.contains("%26respCode%3D") &&  _message.contains("%26respMsg%3D") ){
					this.respCode = _message.substring(_message.indexOf("%26respCode%3D")+14,
					_message.indexOf("%26respMsg%3D"));
			}else{
				if(_message.contains("<respCode>") &&  _message.contains("</respCode>"))
					this.respCode = _message.substring(_message.indexOf("<respCode>")+10,
							_message.indexOf("</respCode>"));
			}
			
			
		}
		System.out.println("ChargeOrderRz:time="+this.time);
		System.out.println("ChargeOrderRz:orderNum="+this.orderNum);
		System.out.println("ChargeOrderRz:respcode="+this.respCode);
		System.out.println("ChargeOrderRz:orderAmount="+this.orderAmount);
		System.out.println("ChargeOrderRz:acctNum="+this.acctNum);
		
//		if(_message.contains("消费后端返回") && _message.contains("%26respCode%3D") &&
//				_message.contains("<orderNum>") && _message.contains("</orderNum>") &&
//				_message.contains("%26orderNumber%3D") && _message.contains("%26qid") 
//				&& _message.contains("%26respMsg%3D") 
//				&& _message.contains("%26orderAmount%3D")
//				&& _message.contains("%26orderCurrency%3D")
//				&& _message.contains("<acctNum>") && _message.contains("</acctNum>")){
//			
//			_message = _message.trim(); 
//			
//			this.time = _message.substring(0,_message.indexOf("消费后端返回"));
//			this.time = this.time.trim();
//			if(this.time.length()>20)
//				this.time = this.time.substring(0,20);
//			
//			String on1 = _message.substring(_message.indexOf("<orderNum>")+10,
//					_message.indexOf("</orderNum>"));
//			on1 = on1.trim();
//			String on2 = _message.substring(_message.indexOf("%26orderNumber%3D")+17,
//					_message.indexOf("%26qid"));
//			on2 = on2.trim();			
//			if(on1.length()==on2.length() && on1.contains(on2))
//				this.orderNum = on1;
//			this.respCode = _message.substring(_message.indexOf("%26respCode%3D")+14,
//					_message.indexOf("%26respMsg%3D"));
//			this.orderAmount = _message.substring(_message.indexOf("%26orderAmount%3D")+17,
//					_message.indexOf("%26orderCurrency%3D"));
//			if(this.orderAmount.length()>2){
//				int n = this.orderAmount.length();
//				this.orderAmount = this.orderAmount.substring(0, n-2)+"."+this.orderAmount.substring(n-1,n);
//			}
//			this.acctNum = _message.substring(_message.indexOf("<acctNum>")+9,
//					_message.indexOf("</acctNum>"));
//			
//			
//			
//			System.out.println("ChargeOrderRz:time="+this.time);
//			System.out.println("ChargeOrderRz:orderNum="+this.orderNum);
//			System.out.println("ChargeOrderRz:respcode="+this.respCode);
//			System.out.println("ChargeOrderRz:orderAmount="+this.orderAmount);
//			System.out.println("ChargeOrderRz:acctNum="+this.acctNum);
//		}else{
//			
//			_message = _message.trim();
//			this.time = _message.substring(0,_message.indexOf("消费后端返回"));
//			this.time = this.time.trim();
//			this.respCode = "NULL";
//			this.orderAmount = "0";
//			System.out.println("data error, pls inv...");
//		}
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}


	public static void main(String[] args){
//		String a = "2014-11-28 15:55:26  消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP000V3VQ2KK</orderNum><acctNum>621785*********5126</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV151941282255126</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>192082</traceNum><date>1128155503</date><purchAmount>000000060100</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number/></ChInfo><CntInfo><mobile/></CntInfo><channelType>16</channelType><PubKeyIndex>001</PubKeyIndex><tranCheckMode>0000000010000000</tranCheckMode><SecRelContInfo></SecRelContInfo><Extension>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D60100%26orderCurrency%3D156%26orderNumber%3DSP000V3VQ2KK%26qid%3D112836687211281555030%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128155503%26settleAmount%3D60100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D778041%26traceTime%3D1128155503%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D635a46a6c654bc9f9f5e685ce7d7504c</Extension><Resp><respCode>00</respCode><respInfo></respInfo></Resp><STPP><traceNum>778041</traceNum><date>1128155503</date></STPP><settledate>1128</settledate><ExtensionBack>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1128%26exchangeRate%3D1%26merAbbr%3DMicrosoft%26merId%3D839310048990017%26orderAmount%3D60100%26orderCurrency%3D156%26orderNumber%3DSP000V3VQ2KK%26qid%3D112836687211281555030%26respCode%3D00%26respMsg%3D%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F%26respTime%3D20141128155503%26settleAmount%3D60100%26settleCurrency%3D156%26settleDate%3D1128%26traceNumber%3D778041%26traceTime%3D1128155503%26transType%3D01%26version%3D1.0.0%26signMethod%3DMD5%26signature%3D635a46a6c654bc9f9f5e685ce7d7504c</ExtensionBack><backEndUrl>https://commercepaycallback.cp.microsoft.com/PGWProviderCallbackService/UnionPayNotification.ashx</backEndUrl></TVPaybody><SecureData/><Mac></Mac></TVPay>";
//		String a = "2014-12-02 12:05:58 消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP007ZRR914V</orderNum><acctNum>622202*********2633</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV6222022633</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>708990</traceNum><date>1202120450</date><purchAmount>000000004900</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number/></ChInfo><CntInfo><mobile/></CntInfo><channelType>16</channelType><PubKeyIndex>001</PubKeyIndex><tranCheckMode>0010000000000000</tranCheckMode><SecRelContInfo></SecRelContInfo><Extension>charset%3DUTF-8%26cupReserved%3D%26exchangeDate%3D1202%26exchangeR2014-12-02 12:05:58 应答码：05";
		String a ="2014-12-02 18:22:46 消费后端返回:<?xml version=\"1.0\" encoding=\"utf-8\"?><TVPay><TVPaybody><Msg><version>1000</version><typeID>0210</typeID></Msg><TermStatus>0</TermStatus><ProcessingCode>000000</ProcessingCode><EntryCode/><xclass>XB</xclass><PosCondCode>81</PosCondCode><orderNum>SP0074CJOISL</orderNum><acctNum>622848*********2516</acctNum><cardType/><safetyVerifyMode/><Merchant><termUnitNo>20000051</termUnitNo><termTypeID>TV</termTypeID><acqBIN>00000046</acqBIN><fwdBIN>49910005</fwdBIN><userID>TV133699729332516</userID><merID>839310048990017</merID><name/><tp>4899</tp><OrderInfo/></Merchant><Purchase><icCondCode>0</icCondCode><termAcAbility>0</termAcAbility><termID>20000051</termID><traceNum>709064</traceNum><date>1202182137</date><purchAmount>000000019900</purchAmount><currency>156</currency></Purchase><ChInfo><certType/><number2014-12-02 18:22:46 应答码：00";
		new ChargeOrderRz(a);
	}
}
