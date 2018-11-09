package Protocal.Command;

import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */

public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte HEARTBEAT_REQUEST = 5;
    Byte HEARTBEAT_RESPONSE = 6;
}
