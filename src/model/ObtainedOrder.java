package model;

public class ObtainedOrder {
	
	private String orderNum;
	private String time;
	
	
	public ObtainedOrder(String _message){
		if(_message.contains("&orderNumber=")&&_message.contains("&orderTime=")
				&&_message.contains("微软传过来的订单")){
			String ms = _message.trim();
			this.time = ms.substring(0, ms.indexOf("微软传过来的订单"));
			this.time = this.time.trim();			
			System.out.println("time="+time);
			
			this.orderNum = ms.substring(ms.indexOf("&orderNumber=")+13,
					ms.indexOf("&orderTime="));
			this.orderNum = this.orderNum.trim();
			System.out.println("orderNum="+this.orderNum);
			
		}else
			System.out.println("Error info, pls inv...");
	}
	
	
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}



	public static void main(String[] args){
		String a = "2014-11-28 15:55:00 "
				+ " 微软传过来的订单："
				+ "acqCode=&backEndUrl=https://"
				+ "commercepaycallback.cp.microsoft.com/"
				+ "PGWProviderCallbackService/UnionPayNotification.ashx"
				+ "&charset=UTF-8&commodityDiscount="
				+ "&commodityName=《无冬Online》 Zen&commodityQuantity="
				+ "&commodityUnitPrice=&commodityUrl=&customerIp=127.0.0.1"
				+ "&customerName=&defaultBankNumber=&defaultPayType="
				+ "&frontEndUrl=$RUPlaceholder$&merAbbr=Microsoft&merCode="
				+ "&merId=839310048990017&merReserved=&orderAmount=60100"
				+ "&orderCurrency=156&orderNumber=SP000V3VQ2KK"
				+ "&orderTime=20141128155534&origQid=&transTimeout=900000"
				+ "&transType=01&transferFee=&version=1.0.0&signMethod=MD5"
				+ "&signature=3264e3b4f8cabd667409674e5b26bed9";
		new ObtainedOrder(a);
	}
}
