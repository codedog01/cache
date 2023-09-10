package com.ronhe.romp.cache.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * kryo缓存键值序列化器
 *
 * @author hss
 */
public class KryoCacheKvSerializer implements CacheKvSerializer {
    private Logger log = LoggerFactory.getLogger(KryoCacheKvSerializer.class);

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            /**
             * 不要轻易改变这里的配置,更改之后，序列化的格式就会发生变化，
             * 上线的同时就必须清除 Redis 里的所有缓存，
             * 否则那些缓存再回来反序列化的时候，就会报错
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置

            //Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };


    private static KryoFactory factory = new KryoFactory() {
        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            /**
             * 不要轻易改变这里的配置！更改之后，序列化的格式就会发生变化，
             * 上线的同时就必须清除 Redis 里的所有缓存，
             * 否则那些缓存再回来反序列化的时候，就会报错
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置

            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置

            //Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };


    private static KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    @Override
    public byte[] serialize(Object object) throws Throwable {
        if (object == null) {
            return null;
        }
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output, object);
            output.flush();
            output.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Kryo 序列化对象异常，异常信息为: {}", e);
            throw e;
        } finally {
            return bytes;
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Throwable {
        if (bytes == null) {
            return null;
        }
        Kryo kryo = pool.borrow();
        T object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            object = (T) kryo.readClassAndObject(input);
            input.close();
        } catch (Exception e) {
            log.error("Kryo 反序列化对象异常，异常信息为: {}", e);
            throw e;
        } finally {
            pool.release(kryo);
            return object;
        }
    }
}
