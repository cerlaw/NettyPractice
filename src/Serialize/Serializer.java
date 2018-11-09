package Serialize;

import Serialize.Impl.JSONSerializer;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
