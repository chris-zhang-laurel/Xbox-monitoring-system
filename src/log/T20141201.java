package log;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import model.Access;
import model.ChargeOrderRz;
import model.TomcatAccess;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import parser.Json2Obj;
import redis.clients.jedis.Jedis;
import util.Constant;

public class T20141201 {

	
	public static void main(String[] args){
		
		long startTime = System.currentTimeMillis();
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", 
				"elasticsearch").build();
		TransportClient transportClient = new TransportClient(settings);
		Client client = null;
		client = transportClient.addTransportAddress(
					new InetSocketTransportAddress("10.0.49.23", 
							9300));

	
			Jedis jedis1 = new Jedis("10.0.49.23");	
			jedis1.del("chargeOrder");
//			Constant.testInsertData(jedis1);
			System.out.println("T2_20141124 Tomcat log list 长度"
						+jedis1.llen("logstash:redis"));
		
			if( (System.currentTimeMillis()-startTime)>60*1000 )
				System.exit(0);//1:异常退出2：正常退出
			
			while (true) {
				
				List<String> values = jedis1.blpop(1, "logstash:redis");
				System.out.println("[DEBUG mode] Get redis:" + values);
				if (values == null) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				
				for(String value:values){
					java.util.Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR, -8);
					String indexName = "logstash";
//					String timeStamp = format1.format(c.getTime());
//					System.out.println("~~~indexName:"+indexName);
//					System.out.println("~~~value:"+value);
					
					int logType = Constant.judgeLogType(value);
					
					if(logType==1){
						TomcatAccess ta = new TomcatAccess();
						ta = Json2Obj.parse(value);								
						Access ac = new Access(ta.getMessage());
						indexName += "_acc"+ Constant.getUTCindexDate(ac.getTime(),1);
						System.out.println("~~~indexName:"+indexName);
						
						try {
							IndexResponse response = client.prepareIndex(indexName, "testENVaccess",
									UUID.randomUUID().toString()).setSource(
										jsonBuilder().startObject()
										.field("message", ta.getMessage())
										.field("@version", ta.getVersion())
										.field("@timestamp", Constant.getUTCindexDate(ac.getTime(),3))		
										.field("host_IP",ac.getIp())
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

					if(logType==21){
						System.out.println("~~~value21:"+value);
						
//						TomcatAccess ta = new TomcatAccess();
//						ta = Json2Obj.parse(value);			
//						ObtainedOrder oo = new ObtainedOrder(ta.getMessage());
//						Long i = jedis1.hset("chargeOrder", oo.getOrderNum(), oo.getTime());
//						System.out.println("Input OrderNum into Redis RZ?"+i);
//						System.out.println("^^^"+jedis1.hget("chargeOrder", oo.getOrderNum()));
						
					}
					if(logType==22){	
						System.out.println("~value22:"+value);
						TomcatAccess ta = new TomcatAccess();
						ta = Json2Obj.parse(value);		
						ChargeOrderRz cor = new ChargeOrderRz(ta.getMessage());
						
						ta.setTimestamp(Constant.getUTCtime(cor.getTime(),1));
						System.out.println("!~~~indexFile-TIME~~~="+ta.getTimestamp());
						indexName += "_cat"+ ta.getTimestamp();
						
						try {
							Float amount = 0.0f;
							try {
								amount = Float.parseFloat(cor.getOrderAmount()) ;
							}catch (Exception e) {
							}
							
							IndexResponse response = client.prepareIndex(indexName, "testENVcatalina",
									UUID.randomUUID().toString()).setSource(
										jsonBuilder().startObject()
										.field("message", ta.getMessage())
										.field("@version", ta.getVersion())
										.field("@timestamp", Constant.getUTCtime(cor.getTime(),2))		
										.field("host_IP",ta.getHost())
										.field("CPPstatus",(cor.getRespCode().equals("NULL")?"0":"1"))
										.field("OrderNumber",cor.getOrderNum())
										.field("OrderAmount",amount)
										.field("ResponseCode",cor.getRespCode())
										.field("AccountNumber",cor.getAcctNum())
										.field("CloseTime", cor.getTime())
										.endObject()).execute().actionGet();
													
						} catch (ElasticsearchException e) {
							e.printStackTrace();							
							jedis1.rpush("logstash:redis", value);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
		
			
	}

}
