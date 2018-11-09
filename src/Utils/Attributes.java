package Utils;

import io.netty.util.AttributeKey;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition 是否登录成功标志位
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
