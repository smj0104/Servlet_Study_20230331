package com.study.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.Role;
import com.study.servlet.service.RoleService;
import com.study.servlet.service.RoleServiceImpl;

/**
 * 
 * 데이터베이스에서 파라미터로 넘어온 RoleName이 존재하는지 여부 확인
 * 존재한다면 ResponseDto Json(200,success, true)
 * 존재하지않으면 ResponseDto(400,error,false)
 * 
 * RoleService
 * RoleRepository
 */


@WebServlet("/role") //소문자 이유: url 주소는 반드시 소문자(checkedrole에서도 소문자를 사용했기때문)
public class RoleInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RoleService roleService;
	private Gson gson;

    public RoleInfo() {
    	roleService = RoleServiceImpl.getInstance();
    	gson = new Gson();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//메소드가 get이라 doget에 작성
		String roleName = request.getParameter("roleName");
		System.out.println("roleName: " + roleName);
		
		
		Role role = roleService.getRole(roleName);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(role == null) {
			out.println(gson.toJson(new ResponseDto<>(400, "error", false)));
			return;
		}
		out.println(gson.toJson(new ResponseDto<>(200, "success", true)));
		
		//System.out.println(roleService.getRole(roleName));
		
		
		
		/**
		 * 필터
		 * WAS와 servlit사이에 들어감
		 * request요청때,응답할때 한번 걸러줌 (전처리와 후처리)
		 * 여려겹으로 존재 가능/우선순위 지정가능
		 * 여려겹의 필터들 통과시 servlit으로 들어감 못들어갔을시도 바로 응답가능
		 * forward
		 * 
		 * 백엔드의 전체적 흐름
		 * 클라 - WAS- Servlit - service - repository - DB
		 * 
		 * 클라이언트와 WAS가 http protocol로 요청과 응답
		 * 틀을 정해서 요청해야 서버가 알아봄 틀 = http protocol(URL,method에 따라 서버가 응답을 정함)
		 * url = /role  성공관련 = 200번대 (201 create)
		 * 400번대(400 Bad request 나쁜 요청 / 403 forbidden(권한 관련) // 405 메소드가 잘못됨 //  415 요청,응답하는 데이터의 변수 설정 문제
		 * 500번대 (코딩문제,서버문제) 클라문제 아님
		 * 
		 * WAS(Tomcat)
		 * client가 WAS를 찾아올때 IP:PORT필요함 (localhost:8080) 뒤에 /
		 * 요청에 따라 해당 doget dopost 실행
		 * 요청url뒤?가 붙으면 쿼리파람       key,value값으로 서버로 전달
		 * 서버는 getparameter메소드로 받음
 		 *
 		 * header / body로 request요청이 나뉘어서 넘어감 WAS를 통해 servlit doget dopost의 response객체로 넘어감
 		 * body가 data를 의미 header는 요청에 대한 정보를 담고있음
 		 * 
 		 * input의 name이 key value가 value값
 		 * 
		 * Servlit
		 * 요청데이터 응답데이터를 다루는 곳
		 * DI의 이유: 결합도를 낮추기 위함
		 * 
		 *  
		 * Service(business logic)
		 * 처리된 값을 메소드로 묶어둠   (관련있는 것끼리 묶는 행위 = 응집도를 높히다)
		 *   
		 * Repository
		 * DB랑 통신
		 * DB값을 넣고 빼고함
		 * 
		 *  
		 *  
		 * DB
		 */
		
	}

}
