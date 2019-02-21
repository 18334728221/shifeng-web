package com.xwd.auth.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.auth.SecurityUtils;
import com.auth.constant.RoleConstant;
import com.frame.tree.JsTree;
import com.frame.tree.TreeNode;
import com.xwd.auth.model.AuthModule;
import com.xwd.auth.model.AuthPermission;
import com.xwd.auth.service.AuthComprisedService;
import com.xwd.auth.service.AuthModuleService;
import com.xwd.auth.service.AuthPermissionService;

@Component
@Transactional
@Aspect
public class AuthComprisedServiceImpl implements AuthComprisedService {

	@Autowired
	private AuthPermissionService authPermissionService;
	@Autowired
	private AuthModuleService authModuleService;

	@Override
	public String findModulePermissionAsJson() {
		List listNode = new ArrayList();
		Long maxId = 0L;
		long level = RoleConstant.getControlLevel(SecurityUtils.getAuthorizationPrincipal().getRoles());
		List<AuthModule> list = authModuleService.findAll();
		HashMap nodeMap = new HashMap();
		// 没有权限的模块集合，那么下面的权限添加也没有
		Set<Long> moduleSet = new HashSet<Long>();
		for (AuthModule authModule : list) {
			if (authModule.getControlLevel() < level) {
				moduleSet.add(authModule.getId());
				continue;
			}
			TreeNode node = new TreeNode();
			if (authModule.getId() > maxId) {
				maxId = authModule.getId();
			}
			node.setId(authModule.getId());
			node.setPId(authModule.getParentId());
			node.setName(authModule.getModuleName().toString());
			node.setLevel(Byte.valueOf(authModule.getControlLevel() + ""));
			nodeMap.put(node.getId(), node);
		}
		List<AuthPermission> authPermissionList = authPermissionService.findAll();
		if (null != authPermissionList && authPermissionList.size() > 0) {
			for (AuthPermission authPermission : authPermissionList) {
				if (level > authPermission.getControlLevel() || moduleSet.contains(authPermission.getModuleId())) {
					continue;
				}
				TreeNode node = new TreeNode();
				node.setId(authPermission.getId() + maxId);
				node.setTreeCode(authPermission.getId().toString());
				node.setPId(authPermission.getModuleId());
				node.setName(authPermission.getDescription().toString());
				nodeMap.put(node.getId(), node);
			}
		}

		listNode.add(JsTree.getJonsTree(nodeMap));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(listNode);
			json = json.replaceAll("'", "\"");
			return "[" + json.substring(2, json.length() - 2) + "]";
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String findModuleAsJson() {
		List listNode = new ArrayList();
		Long maxId = 0L;
		long level = RoleConstant.getControlLevel(SecurityUtils.getAuthorizationPrincipal().getRoles());
		List<AuthModule> list = authModuleService.findAll();
		HashMap nodeMap = new HashMap();
		// 没有权限的模块集合，那么下面的权限添加也没有
		Set<Long> moduleSet = new HashSet<Long>();
		for (AuthModule authModule : list) {
			if (authModule.getControlLevel() < level) {
				moduleSet.add(authModule.getId());
				continue;
			}
			TreeNode node = new TreeNode();
			if (authModule.getId() > maxId) {
				maxId = authModule.getId();
			}
			node.setId(authModule.getId());
			node.setPId(authModule.getParentId());
			node.setName(authModule.getModuleName().toString());
			node.setLevel(Byte.valueOf(authModule.getControlLevel() + ""));
			nodeMap.put(node.getId(), node);
		}
		listNode.add(JsTree.getJonsTree(nodeMap));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(listNode);
			json = json.replaceAll("'", "\"");
			return "[" + json.substring(2, json.length() - 2) + "]";
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
