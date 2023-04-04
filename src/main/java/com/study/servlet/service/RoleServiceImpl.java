package com.study.servlet.service;

import com.study.servlet.entity.Role;
import com.study.servlet.entity.User;
import com.study.servlet.repository.RoleRepository;
import com.study.servlet.repository.RoleRepositoryImpl;
import com.study.servlet.repository.UserRepository;
import com.study.servlet.repository.UserRepositoryImpl;

public class RoleServiceImpl implements RoleService {

	//Service 객체 싱글톤 정의
	private static RoleService instance;
	public static RoleService getInstance() {
		if(instance == null) {
			instance = new RoleServiceImpl();
		}
		return instance;
	}
	
	//Repository 객체 DI : 의존관계 주입
	private RoleRepository roleRepository;
	
	private RoleServiceImpl() {
		roleRepository = RoleRepositoryImpl.getInstance();
	}

	@Override
	public Role getRole(String roleName) {
		return roleRepository.findRolebyRoleName(roleName);
	}


}
