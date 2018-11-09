package Protocal.request;

import Protocal.Command.Command;
import Protocal.Packet;
import lombok.Data;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition 服务端与客户端收发信息的对象
 */

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
