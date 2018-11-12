package Protocal.request;

import Protocal.Packet;
import lombok.Data;

import static Protocal.Command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    String userId;

    String userName;

    String passWord;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
