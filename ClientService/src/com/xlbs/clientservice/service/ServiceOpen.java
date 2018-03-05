package com.xlbs.clientservice.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.AddressFromURIString;
import akka.cluster.Cluster;
import com.xlbs.jedis.utils.JedisGlobal;
import com.xlbs.serviceinterface.serviceconstant.ServiceParamter;
import com.xlbs.clientservice.entityobject.ClusterConfig;

import java.util.Map;

public class ServiceOpen {
	
	private ActorSystem system;
	
	private static ServiceOpen instance = new ServiceOpen();
	
	private ClusterConfig cc = ClusterConfig.getConfig();
	
	public ActorSystem getSystem() {
		return system;
	}
	
	public static ServiceOpen getInstance() {
		return instance;
	}

	public void stopActor(ActorRef openService) {
		system.stop(openService);
	}
	
	public void initActorSystem(ActorSystem system){
		if(system ==null){
			Config config = ConfigFactory
					.parseString("akka.remote.netty.tcp.port=" + cc.getPort())
					.withValue("akka.remote.netty.tcp.hostname",ConfigValueFactory.fromAnyRef(cc.getIp()))
					.withFallback(ConfigFactory.load());//不填则默认读取application.conf配置文件，此时读取client.conf文件
			
			this.system = ActorSystem.create("ServiceOpen", config);
			
			String clusterNodes = cc.getClusterNode();
			Cluster.get(this.system).join(AddressFromURIString.parse(clusterNodes));//加入集群，设置为集群主节点
		}else{
			this.system = system;
		}
	}
	
	
	/**
	 * 获取默认服务，请求超时建默认为30秒
	 * @param clz 服务对象
	 * @return
	 * @throws Exception
	 */
	public <T> T getBean(Class<T> clz) throws Exception {
		Map<String,String> serviceMap = JedisGlobal.JedisUtil_DATA.queryJedisMapAllObj(ServiceParamter.MICRO_SERVICE);
		return getServiceBean(clz, serviceMap.get(clz.getName()), 30);
	}

	/**
	 * 获取超时请求设置的bean服务
	 * @param clz 服务对象
	 * @param serviceUrl 服务路径
	 * @param timeOutSeconds 超时时间
	 * @return
	 * @throws Exception
	 */
	public <T> T getServiceBean(Class<T> clz, String serviceUrl, int timeOutSeconds)
			throws Exception {
		ActorSelection rpc;
		if (serviceUrl != null) {
			rpc = system.actorSelection(serviceUrl);
		} else {
			throw new Exception("服务未启动!");
		}
		return new BeanProxy().proxy(rpc, clz, timeOutSeconds);
	}
	


}
