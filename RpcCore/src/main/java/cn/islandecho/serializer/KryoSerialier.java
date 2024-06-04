package cn.islandecho.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerialier implements Serializer{

    private static final ThreadLocal <Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
               Kryo kryo = new Kryo();
               // 设置动态序列化和反序列化类，不提前注册所有类
               kryo.setRegistrationRequired(false);
               return kryo;
            });

    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeClassAndObject(output, obj);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        Input input = new Input(new ByteArrayInputStream(data));
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        T object = kryo.readObject(input, clazz);
        input.close();
        return object;
    }
}
