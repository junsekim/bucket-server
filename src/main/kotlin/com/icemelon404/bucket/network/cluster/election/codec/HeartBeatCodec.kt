package com.icemelon404.bucket.network.cluster.election.codec

import com.icemelon404.bucket.common.InstanceAddress
import com.icemelon404.bucket.network.cluster.election.HeartBeat
import com.icemelon404.bucket.network.common.MessageCodec
import com.icemelon404.bucket.network.common.Packet
import com.icemelon404.bucket.network.util.bufferSize
import com.icemelon404.bucket.network.util.putString
import com.icemelon404.bucket.network.util.string
import java.nio.ByteBuffer

class HeartBeatCodec(packetId: Int) : MessageCodec<HeartBeat>(HeartBeat::class, packetId) {


    override fun resolve(packet: Packet): HeartBeat {
        with(ByteBuffer.wrap(packet.body)) {
            val term = long
            val address = string
            val port = int
            return HeartBeat(term, InstanceAddress(address, port))
        }
    }

    override fun serialize(msg: HeartBeat): ByteArray {
        return ByteBuffer.allocate(bufferSize(msg.address.dest) + 12).apply {
            putLong(msg.term)
            putString(msg.address.dest)
            putInt(msg.address.port)
        }.array()
    }
}
