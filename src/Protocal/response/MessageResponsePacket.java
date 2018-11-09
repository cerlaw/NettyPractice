package Protocal.response;

import Protocal.Command.Command;
import Protocal.Packet;
import lombok.Data;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition
 */

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
