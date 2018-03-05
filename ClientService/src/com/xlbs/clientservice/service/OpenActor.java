package com.xlbs.clientservice.service;



import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.xlbs.serviceinterface.messageentity.Event;

public class OpenActor extends UntypedActor {
	
	private ActorSelection rpc;
	
	public OpenActor(ActorSelection rpc) {
		this.rpc = rpc;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof Event.CallMethod) {
			rpc.tell(arg0, getSender());
		}
		
	}

}
