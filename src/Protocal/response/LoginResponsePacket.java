package Protocal.response;

import Protocal.Command.Command;
import Protocal.Packet;
import lombok.Data;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition 自定义相应包
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
