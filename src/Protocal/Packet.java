package Protocal;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition 自定义通信过程中的java对象
 */

@Data
public abstract class Packet {
    /**
     * 协议版本
     * */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令
     * */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
