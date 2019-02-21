package com.xwd.base.serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Serializer;

/**
 * 初始化
 * 注册对象采用的序列化方式
 * @author Administrator
 *
 */
public class KryoRedisRegistration {

	private static Map<Class<?>, Serializer> serializerMap = new HashMap<Class<?>, Serializer>();
	private static List<Class<?>> serializerList = new ArrayList<Class<?>>();
	
	public static void register(Class<?> clazz){
		serializerList.add(clazz);
	}
	
	public static void register(Class<?> clazz, Serializer serializer){
		serializerMap.put(clazz, serializer);
	}
	
	public static Map<Class<?>, Serializer> getSerializerMap(){
		return serializerMap;
	}
	
	public static List<Class<?>> getSerializerList(){
		return serializerList;
	}
} 
