package com.xlbs.queryservice.service;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.xlbs.jedis.utils.JedisGlobal;
import com.xlbs.serviceinterface.serviceconstant.ServiceParamter;

public class ServiceListener extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(),ClusterEvent.initialStateAsEvents(),
                MemberEvent.class, UnreachableMember.class, LeaderChanged.class);//订阅集群状态转换信息
    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(getSelf());//取消订阅
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MemberUp) {
            MemberUp mUp = (MemberUp) message;
            log.info("############### Member is Up: {}", mUp.member());
        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("############### Member detected as unreachable: {}",mUnreachable.member());
            cluster.down(mUnreachable.member().address());
        } else if (message instanceof LeaderChanged) {
            LeaderChanged lChanged = (LeaderChanged) message;
            log.info("############### Member is LeaderChanged: {}", lChanged.leader());
            String serviceAddr = lChanged.getLeader().protocol() + "://"+lChanged.getLeader().hostPort();
            if(serviceAddr.equals(QueryServiceMain.getNodeIp())){//只有是本机ip变为主节点才修改内存库
                JedisGlobal.JedisUtil_DATA.setRootKeyValue(ServiceParamter.Cluster_Leader, serviceAddr);
            }
        }else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            cluster.join(mRemoved.member().address());
            log.info("############### Member is Removed: {}", mRemoved.member());
        } else if (message instanceof MemberEvent) {
            // ignore
        } else {
            unhandled(message);
        }
    }


}
