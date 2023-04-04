package com.study.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.User;
import com.study.servlet.service.UserService;
import com.study.servlet.service.UserServiceImpl;


@WebServlet("/auth/signin")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;
	private Gson gson;
	
	
    public SignIn() {
        userService = UserServiceImpl.getInstance();
        gson = new Gson();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//인증은 post /basiclogin/ pomlogin(pom태그에 아이디 비번 입력후 액션버튼)/ JWT(제이슨 웹 토큰)
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userService.getUser(username);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		if(user == null) {
			//로그인 실패 1 ( 아이디 찾을 수 없음)
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
			return;
		}
		
		if(!user.getPassword().equals(password)) {
			//로그인 실패 2 (비밀번호 틀림)
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
			return;
		}
			//로그인 성공
			HttpSession session = request.getSession(); //성공시 session들고옴
			session.setAttribute("AuthenticationPrincipal", user.getUserId());
		
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(200, "사용자 인증 성공", true);
			out.println(gson.toJson(responseDto));
	}
			/**
			 * 세션과 쿠키
			 * 
			 * 클라이언트 저장소 =================== 서버 저장소
			 * 
			 *  클라 저장소 
			 *  1.로컬 스토리지 서버가 종료되도 날아가지않음
			 *  2. 쿠키  (최근 잘 안씀) 
			 * 
			 * 서버 저장소
			 * 1. 세션 			JSession(브라우저 닫을 시 삭제// 닫지않는한 지속) 만료시간 지날시 사라짐 기본적으로 만료시간은 30분
			 * 2. Context		셋 다 사용 방법 동일(set/get attribute    key,value값을 꺼냄)  Context:서버 전역 저장소(static) 다같이 사용,중요한 정보X object자료형, 서버의 설정값등을 저장
			 * 3. Request		해당 요청 안에서만 유용한 저장소 (2,3 거의 안씀) 
			 * JWT사용시 세션 사용안함 서버 저장소 사용안함 클라이언트에 jwt토큰을 줌. refresh token auth token 두가지로 나뉨
			 * 로컬 스토리지에 refresh token
			 * 
			 * 
			 */
}
