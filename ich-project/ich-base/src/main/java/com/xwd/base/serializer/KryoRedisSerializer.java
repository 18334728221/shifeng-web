package com.xwd.base.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptyListSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptyMapSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptySetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonListSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonMapSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonSetSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.SubListSerializers.ArrayListSubListSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableListSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMultimapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableSetSerializer;

/**
 * 使用kryo序列化
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		try {
			Kryo kryo = kryos.get();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Output output = new Output(baos);
			kryo.writeClassAndObject(output, t);
			output.flush();
			return baos.toByteArray();
		} catch (Exception ex) {
			throw new SerializationException("Cannot serialize", ex);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		try {
			Kryo kryo = kryos.get();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			Input input = new Input(bais);
			T t = (T) kryo.readClassAndObject(input);
			input.close();
			return t;
		} catch (Exception ex) {
			throw new SerializationException("Cannot deserialize", ex);
		}
	}
	
	private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
	    protected Kryo initialValue() {
	        Kryo kryo = new Kryo();
	        kryo.register(boolean[].class);
	        kryo.register(byte[].class);
	        kryo.register(short[].class);
	        kryo.register(char[].class);
	        kryo.register(int[].class);
	        kryo.register(float[].class);
	        kryo.register(long[].class);
	        kryo.register(double[].class);
	        kryo.register(String[].class);
	        kryo.register(int[][].class);
	        kryo.register(Object.class);
	        kryo.register(Object[].class);
	        kryo.register(HashSet.class, new JavaSerializer());
	        kryo.register(LinkedHashSet.class, new JavaSerializer());
	        kryo.register(TreeSet.class, new JavaSerializer());
	        kryo.register(CopyOnWriteArraySet.class, new JavaSerializer());
	        kryo.register(ArrayList.class, new JavaSerializer());
	        kryo.register(LinkedList.class, new JavaSerializer());
	        kryo.register(CopyOnWriteArrayList.class, new JavaSerializer());
	        kryo.register(HashMap.class, new MapSerializer());
	        kryo.register(LinkedHashMap.class, new MapSerializer());
	        kryo.register(TreeMap.class);
	        kryo.register(Date.class);
	        kryo.register(Calendar.class);
	        kryo.register(ConcurrentHashMap.class);
			kryo.register(EnumMap.class, new EnumMapSerializer());
			kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
			kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
			kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
			kryo.register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer());
			kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
			kryo.register(Collections.singletonMap("", "").getClass(), new CollectionsSingletonMapSerializer());
			kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
			kryo.register(InvocationHandler.class, new JdkProxySerializer());
			UnmodifiableCollectionsSerializer.registerSerializers(kryo);
			SynchronizedCollectionsSerializer.registerSerializers(kryo);
			ImmutableListSerializer.registerSerializers(kryo);
			ImmutableSetSerializer.registerSerializers(kryo);
			ImmutableMapSerializer.registerSerializers(kryo);
			ImmutableMultimapSerializer.registerSerializers(kryo);
			List<Class<?>> list = KryoRedisRegistration.getSerializerList();
			for(Class<?> clazz : list){
				kryo.register(clazz);
			}
			for(Entry<Class<?>, Serializer> entry : KryoRedisRegistration.getSerializerMap().entrySet()){
				kryo.register(entry.getKey(), entry.getValue());
			}
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
			kryo.setRegistrationRequired(false);
			kryo.setMaxDepth(20);
	        return kryo;
	    };
	};

}
