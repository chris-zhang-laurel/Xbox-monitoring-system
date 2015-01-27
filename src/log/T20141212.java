package log;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import model.Access;
import model.ChargeOrderRz;
import model.TomcatAccess;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import parser.Json2Obj;
import redis.clients.jedis.Jedis;
import util.Constant;

public class T20141212 {

public static void main(String[] args){
		
		long startTime = System.currentTimeMillis();
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", 
				"elasticsearch").build();
		TransportClient transportClient = new TransportClient(settings);
		Client client = null;
		client = transportClient.addTransportAddress(
					new InetSocketTransportAddress("10.0.49.23", 
							9300));
//		client = transportClient.addTransportAddress(
//				new InetSocketTransportAddress("192.168.17.100", 
//						9300));

//		client.admin().indices().prepareCreate("env_catalina").execute().actionGet();
//		XContentBuilder mapping = null;
//		try {
//			mapping = jsonBuilder().startObject().startObject("env_catalina").
//					startObject("properties").startObject("commodity_Name_index")
//					.field("type","string").field("index", "not_analyzed")
//					.endObject().endObject().endObject().endObject();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		PutMappingRequest mappingRequest = Requests.putMappingRequest("env_catalina").type("env_catalina").source(mapping);  
//	    client.admin().indices().putMapping(mappingRequest).actionGet(); 

		
	
			Jedis jedis1 = new Jedis("10.0.49.23");	
//		Jedis jedis1 = new Jedis("192.168.17.96");	
//		jedis1.del("chargeOrder");
//			Constant.testInsertData(jedis1);
			
			System.out.println("T2_20141226_0 Tomcat log list 长度"
					+jedis1.llen("logstash:redis"));
			System.out.println("T2_20141226_1 Tomcat log list 长度"
						+jedis1.llen("logstash:redis_1"));
		
			if( (System.currentTimeMillis()-startTime)>60*1000 )
				System.exit(0);//1:异常退出 0：正常退出
			
			
			
			while (true) {
				
//				List<String> values = jedis1.blpop(1, "logstash:redis");
				List<String> values = jedis1.blpop(1, "logstash:redis_1");
				System.out.println("[DEBUG mode] Get redis:" + values);
				if (values == null) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				
				for(String logvalue:values){
					java.util.Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR, -8);
					String indexName = "logstash";
					
					System.out.println("logvalue="+logvalue);
					LogParser lp = new LogParser(logvalue);					
					System.out.println("typeID="+lp.typeid);
					if(lp.typeid==0)
						continue;
					else{
						if(lp.typeid==9){
							Access ac = new Access(logvalue);//TODO
							indexName += "access"+ Constant.getUTCindexDate(ac.getTime(),1);
							System.out.println("~~~indexName:"+indexName);

							try {
								IndexResponse response = client.prepareIndex(indexName, "ENVaccess",
										UUID.randomUUID().toString()).setSource(
											jsonBuilder().startObject()
											.field("message", ac.getMessage())
											.field("@version", 1)
											.field("@timestamp", Constant.getUTCindexDate(ac.getTime(),3))		
											.field("userhost_IP",ac.getIp())
											.field("url",ac.getUrl())
											.field("method",ac.getMethod())
											.field("respCode",ac.getRespCode())
											.field("closeTime",Constant.getUTCindexDate(ac.getTime(),2))
											.field("Accstatus",(ac.getRespCode().equals("NULL")?"0":"1"))
											.endObject()).execute().actionGet();
							} catch (ElasticsearchException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						
						if(lp.typeid<=3){
							HashMap<String, Object> catalinaObj = lp.getLogObj(logvalue, "");
							if(lp.typeid==1)
								indexName += "-cat-charger-ms";
							if(lp.typeid==2)
								indexName += "-cat-query-ms";
							if(lp.typeid==3)
								indexName += "-cat-refund-ms";
							System.out.println("time----"+catalinaObj.get("time").toString());
							System.out.println("utc-----"+Constant.getUTCdate(catalinaObj.get("time").toString()));
//							indexName += Constant.getUTCindexDate(catalinaObj.get("time").toString(),1);
							indexName += Constant.getUTCdate(catalinaObj.get("time").toString());
							
							System.out.println("~~~indexName:"+indexName);
							try {
								IndexResponse response = client.prepareIndex(indexName, "env_catalina",
										UUID.randomUUID().toString()).setSource(
											jsonBuilder().startObject()
											.field("message", catalinaObj.get("message").toString())
											.field("@version", 1)
//											.field("@timestamp", Constant.getUTCindexDate(catalinaObj.get("time").toString(),3))
											.field("@timestamp", Constant.getUTCindexDate(catalinaObj.get("time").toString()))
											.field("host_IP",catalinaObj.get("exIP").toString())
											.field("commodity_Name",catalinaObj.get("commName").toString())
											.field("commodity_Name_index",catalinaObj.get("commName").toString())
											.field("close_Time",catalinaObj.get("time") )
											.field("order_Number",catalinaObj.get("orderNum").toString())
											.endObject()).execute().actionGet();
							} catch (ElasticsearchException e) {
								e.printStackTrace();
								jedis1.rpush("logstash:redis_1", logvalue);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(lp.typeid==11||lp.typeid==21||lp.typeid==31){
							HashMap<String, Object> catalinaObj = lp.getLogObj(logvalue, "");
							if(lp.typeid==11)
								indexName += "-cat-charge-rz";
							if(lp.typeid==21)
								indexName += "-cat-query-rz";
							if(lp.typeid==31)
								indexName += "-cat-refund-rz";								
//							indexName += Constant.getUTCindexDate(catalinaObj.get("time").toString(),1);
							System.out.println("time="+catalinaObj.get("time").toString());
							indexName += Constant.getUTCdate(catalinaObj.get("time").toString());
							
							System.out.println("~~~indexName:"+indexName);
							try {
								IndexResponse response = client.prepareIndex(indexName, "env_catalina",
										UUID.randomUUID().toString()).setSource(
											jsonBuilder().startObject()
											.field("message", catalinaObj.get("message").toString())
											.field("@version", 1)
//											.field("@timestamp", Constant.getUTCindexDate(catalinaObj.get("time").toString(),3))
											.field("@timestamp", Constant.getUTCindexDate(catalinaObj.get("time").toString()))
											.field("host_IP",catalinaObj.get("inIP").toString())
											.field("amount",catalinaObj.get("amount").toString())											
											.field("resp_Code",catalinaObj.get("respCode").toString())
											.field("close_Time",catalinaObj.get("time") )
											.field("order_Number",catalinaObj.get("orderNum").toString())
											.field("card_Number",catalinaObj.get("cardNum").toString())
											.field("bank",catalinaObj.get("bank").toString())
											.field("bank_Name_index",catalinaObj.get("bank").toString())
											.endObject()).execute().actionGet();
							} catch (ElasticsearchException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					
					}
				
			}
		
		}
}
}
	



