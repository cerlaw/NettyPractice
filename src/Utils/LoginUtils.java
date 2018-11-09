package Utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author zhanghongjie
 * @date 2018/11/3
 * @descrition 登录相关工具类
 */
public class LoginUtils {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;

    }
}
