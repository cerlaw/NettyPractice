package Protocal.response;

import Protocal.Command.Command;
import Protocal.Packet;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition 心跳包响应
 */
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
