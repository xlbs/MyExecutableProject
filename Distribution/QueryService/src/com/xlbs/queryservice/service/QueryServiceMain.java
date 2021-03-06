package com.xlbs.queryservice.service;

import java.util.HashMap;
import java.util.Map;

import com.xlbs.jedis.utils.JedisGlobal;
import com.xlbs.queryservice.config.QueryService;
import com.xlbs.serviceinterface.serviceconstant.ServiceParamter;
import com.xlbs.serviceinterface.serviceinterface.I_QueryInterface;
import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.AddressFromURIString;
import akka.actor.Props;
import akka.cluster.Cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import com.xlbs.queryservice.servicefactory.SessionFactoryManager;
import com.xlbs.queryservice.serviceimplement.QueryInterfaceImp;

public class QueryServiceMain {
	
	private final Logger log = Logger.getLogger(QueryServiceMain.class);
	
	private static QueryService sc = QueryService.getConfig();

	public static String getNodeIp(){
		return sc.getClusterNode();
	}
	
	public static void main(String[] args) {
		QueryServiceMain servicie = new QueryServiceMain();
		servicie.startService();
		
	}

	public void startService() {
		final Config config = ConfigFactory
				.parseString("akka.remote.netty.tcp.port="+ sc.getPort())
				.withValue("akka.remote.netty.tcp.hostname",ConfigValueFactory.fromAnyRef(sc.getIp()))
				.withFallback(ConfigFactory.load());//不填则默认读取application.conf配置文件，此时读取application.conf文件
		ActorSystem system = ActorSystem.create(ServiceParamter.RPC_ClusterService, config);

		String clusterNodes = sc.getClusterNode();
		String leaderNode = null;
		try {
			leaderNode = JedisGlobal.JedisUtil_DATA.queryRootKeyValue(ServiceParamter.Cluster_Leader);
			if (leaderNode != null) {
				Cluster.get(system).join(AddressFromURIString.parse(leaderNode));//加入集群
			} else {
				Cluster.get(system).join(AddressFromURIString.parse(clusterNodes));//自己建立新集群
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		system.actorOf(Props.create(ServiceListener.class), "listener");

		SessionFactoryManager.startService();
		
		// Server 加入发布的服务
		Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();
		beans.put(I_QueryInterface.class, new QueryInterfaceImp());
		
		ActorRef mainActor = system.actorOf(Props.create(ServerActor.class, beans).withDispatcher("other-dispatcher"), "QueryService");

		//把服务注册到内存库
		String serviceIp = "akka.tcp://"+ sc.getClusterName() + "@"+ sc.getIp() + ":"+ sc.getPort();
		registeService(I_QueryInterface.class.getName(), serviceIp+"/user/QueryService");
		log.info("查询服务已启动");

	}

	public void registeService(String serviceName,String url) {
		try {
			JedisGlobal.JedisUtil_DATA.updateJedisObj(ServiceParamter.Cluster_Service,serviceName, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
