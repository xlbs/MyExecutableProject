package com.xlbs.getuiservice.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.AddressFromURIString;
import akka.actor.Props;
import akka.cluster.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import com.xlbs.getuiservice.config.GetuiInfoConfig;
import com.xlbs.getuiservice.config.GetuiPushService;
import com.xlbs.getuiservice.serviceimplement.GetuiPushInterfaceImp;
import com.xlbs.getuiservice.utils.ConstantParamets;
import com.xlbs.jedis.utils.JedisGlobal;
import com.xlbs.serviceinterface.serviceconstant.ServiceParamter;
import com.xlbs.serviceinterface.serviceinterface.I_GetuiPushInterface;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetuiServiceMain {
	
	private final Logger log = Logger.getLogger(GetuiServiceMain.class);

	private static GetuiPushService sc = GetuiPushService.getConfig();

	private static GetuiInfoConfig gtconfig = GetuiInfoConfig.getConfig();

	public static void main(String[] args) {
		ConstantParamets.appId = gtconfig.getAppId();
		ConstantParamets.appKey = gtconfig.getAppKey();
		ConstantParamets.masterSecret = gtconfig.getMasterSecret();
		ConstantParamets.host = gtconfig.getHost();
		GetuiServiceMain servicie = new GetuiServiceMain();
		servicie.startService();
		
	}

	public void startService() {
		final Config config = ConfigFactory
				.parseString("akka.remote.netty.tcp.port="+ sc.getPort())
				.withValue("akka.remote.netty.tcp.hostname",ConfigValueFactory.fromAnyRef(sc.getIp()))
				.withFallback(ConfigFactory.load());//不填则默认读取application.conf配置文件，此时读取application.conf文件
		ActorSystem system = ActorSystem.create(ServiceParamter.RPC_ClusterService, config);

		String clusterNodes = sc.getClusterNode();
		Cluster.get(system).join(AddressFromURIString.parse(clusterNodes));//加入集群，设置为集群主节点

		// Server 加入发布的服务
		Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();
		beans.put(I_GetuiPushInterface.class, new GetuiPushInterfaceImp());

		ActorRef mainActor = system.actorOf(Props.create(ServerActor.class, beans).withDispatcher("main-dispatcher"), "GetuiPushService");

		//把服务注册到内存库
		String serviceIp = "akka.tcp://"+ sc.getClusterName() + "@"+ sc.getIp() + ":"+ sc.getPort();
		registeService(I_GetuiPushInterface.class.getName(), serviceIp+"/user/GetuiPushService");
		log.info("Getui服务已启动");

	}

	public void registeService(String serviceName,String url) {
		try {
			JedisGlobal.JedisUtil_DATA.updateJedisObj(ServiceParamter.Cluster_Service,serviceName, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
