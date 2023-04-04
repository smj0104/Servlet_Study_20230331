package com.study.servlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;
import com.study.servlet.entity.Role;
import com.study.servlet.entity.User;
import com.study.servlet.util.DBConnectionMgr;

public class RoleRepositoryImpl implements RoleRepository {
	//Repository 객체 싱글톤 정의
	private static RoleRepository instance;
	public static RoleRepository getInstance() {
		if(instance == null) {
			instance = new RoleRepositoryImpl();
		}
		return instance;
	}
	
	// DIConnectionMgr DI
	private DBConnectionMgr pool;
	
	private RoleRepositoryImpl() {
		pool = DBConnectionMgr.getInstance();
	}

	@Override
	public Role findRolebyRoleName(String roleName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Role role = null;
		
		try {
			con = pool.getConnection();
			String sql = "select role_id, role_name from role_mst where role_name = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				role = Role.builder()
						   .roleId(rs.getInt(1))  //결과가 나왔을때 그곳에서 꺼낸다
						   .roleName(rs.getString(2))
						   .build();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);  //객체 소멸을 위한것(커넥션이 똑바로 실행되건 말건)
		}
			return role;  //return 된 role이 null이면 찾지못한것
	}

	

}
