package Serialize.Impl;

import Serialize.Serializer;
import Serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

/**
 * @author zhanghongjie
 * @date 2018/11/2
 * @descrition
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
