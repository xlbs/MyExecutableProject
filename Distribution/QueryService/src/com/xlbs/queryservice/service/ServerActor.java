package com.xlbs.queryservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

public class ServerActor extends UntypedActor {

	private Map<String, Object> proxyBeans;

	private Router router;

	public ServerActor(Map<Class<?>, Object> beans) {
		proxyBeans = new HashMap<String, Object>();
		for (Class<?> inface : beans.keySet()) {
			proxyBeans.put(inface.getName(), beans.get(inface));
		}
//		for (Iterator<Class<?>> iterator = beans.keySet().iterator(); iterator
//				.hasNext();) {
//			Class<?> inface = iterator.next();
//			proxyBeans.put(inface.getName(), beans.get(inface));
//		}
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < 100; i++) {
			routees.add(new ActorRefRoutee(getContext().actorOf(
					Props.create(QueryServerActor.class, proxyBeans)
							.withDispatcher("main-dispatcher"))));
		}
		router = new Router(new RoundRobinRoutingLogic(), routees);
	}

	@Override
	public void onReceive(Object message) throws Exception {
		router.route(message, getSender());
	}

}
