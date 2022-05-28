package com.icemelon404.bucket.network.cluster.election.handler

import com.icemelon404.bucket.cluster.election.listener.ClusterEventListener
import com.icemelon404.bucket.common.InstanceAddress
import com.icemelon404.bucket.cluster.election.listener.LeaderHeartBeat
import com.icemelon404.bucket.network.cluster.election.HeartBeat
import com.icemelon404.bucket.network.cluster.election.HeartBeatDeny
import com.icemelon404.bucket.network.common.MessageHandler
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import java.net.InetSocketAddress

class HeartBeatHandler(
    val listener: ClusterEventListener
) : MessageHandler<HeartBeat>(HeartBeat::class) {

    override fun onMessage(ctx: ChannelHandlerContext?, msg: HeartBeat) {
        listener.onHeartBeat(object : LeaderHeartBeat {
            override val instanceId : InstanceAddress
                get() = msg.address
            override val term: Long
                get() = msg.term

            override fun deny(term: Long) {
                ctx?.writeAndFlush(HeartBeatDeny(term))
            }
        })
    }
}

