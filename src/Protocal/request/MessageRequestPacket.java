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

    /**表示要发给那一个用户*/
    String toUserId;

    String message;

    public MessageRequestPacket(String userId, String text) {
        this.toUserId = userId;
        this.message = text;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
