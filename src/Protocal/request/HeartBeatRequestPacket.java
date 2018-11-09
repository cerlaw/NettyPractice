package Protocal.request;

import Protocal.Command.Command;
import Protocal.Packet;

/**
 * @author zhanghongjie
 * @date 2018/11/5
 * @descrition 心跳包请求
 */
public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
