package com.xwd.web.init;

import javax.security.auth.AuthPermission;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.auth.AuthorizationPrincipal;
import com.auth.User;
import com.auth.impl.AbstractAuthorizationPrincipal;
import com.auth.impl.SimpleAuth;
import com.auth.impl.SimpleGroup;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.xwd.auth.model.AuthUser;
import com.xwd.base.serializer.KryoRedisRegistration;


/**
 * 对象序列化初始化
 * 
 * @author linjinliang
 * 
 */
public class KryoSerializationServlet extends HttpServlet {

	private static final long serialVersionUID = -3198980332587562173L;

	public void init(ServletConfig config) throws ServletException {
		KryoRedisRegistration.register(User.class);
		KryoRedisRegistration.register(AuthUser.class);
		KryoRedisRegistration.register(AuthorizationPrincipal.class);
		KryoRedisRegistration.register(AbstractAuthorizationPrincipal.class);
		KryoRedisRegistration.register(SimpleAuth.class, new JavaSerializer());
		KryoRedisRegistration.register(SimpleGroup.class);
		KryoRedisRegistration.register(AuthPermission.class);
	}

}
