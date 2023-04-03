package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.servlet.dto.RequestDto;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.User;
import com.study.servlet.service.UserService;
import com.study.servlet.service.UserServiceImpl;

/**
 * Servlet implementation class Authentication
 */
@WebServlet("/auth")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private Gson gson;
	
	public Authentication() {
		userService = UserServiceImpl.getInstance();
		gson = new Gson();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		
		
		
		
		
		
		
		
		
		/**
		 * get요청 요청 데이터 : 쿼리파람 doGet() 
		 * UserList에서 username을 통해 사용자 정보 찾기
		 * 응답 데이터: JSON(User)
		 */
//			String username = request.getParameter("username");
//			System.out.println(username);  //잘 모르겠으면 출력해보기
//			
//			List<User> UserList = new ArrayList<>();
//			UserList.add(new User("a", "1234", "A", "aaa@Gmail.com"));
//			UserList.add(new User("b", "1234", "b", "aaa@Gmail.com"));
//			UserList.add(new User("c", "1234", "c", "aaa@Gmail.com"));
//			UserList.add(new User("bb", "1234", "d", "aaa@Gmail.com"));
//		
//			User findUser = null;
//			
//			for(User user : UserList) {
//				if(user.getUsername().equals(username)) {
//					findUser = user;
//					break;
//				}
//			}
//		
//			Gson gson = new Gson();
//			
//			String userJson = gson.toJson(findUser);
//			
//			response.setContentType("application/json;charset=utf-8");
//			response.getWriter().println(userJson);
//		
//		
		
//		Gson gson = new Gson();
//		
//		List<User> userList = new ArrayList<>();
//		User A = new User("userA", "1234", "A", "aaa@Gmail.com");
//		User B = new User("userB", "1234", "B", "bbb@Gmail.com");
//		
//		userList.add(A);
//		userList.add(B);
//		
//		
		
		//String userJson = gson.toJson(userList);
		//System.out.println(userJson);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = RequestDto.<User>convertRequestBody(request, User.class);
		
		boolean duplicatedFlag = userService.duplicatedUsername(user.getUsername());
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		if(duplicatedFlag) {
			//true == 중복, false == 가입가능
			ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400, "duplicated username", duplicatedFlag);
			out.println(gson.toJson(responseDto));
			return; //안하면 밑줄까지 타고감
		}
		
		ResponseDto<Integer> responseDto = 
				new ResponseDto<Integer>(201, "signup", userService.addUser(user));
		out.print(gson.toJson(responseDto));
		
		
		
		
		//User user = RequestDto.<User>convertRequestBody(request, User.class);
		//Map<String, String> map = RequestDto.<Map<String, String>>convertRequestBody(request, Map.class);
		//System.out.println(user);
		//System.out.println(map);
		
		//컨트롤러 - 서비스 - 레파지토리
		
		
		
		
//		
//		ServletInputStream inputStream = request.getInputStream();
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//		
//		String requestBody = bufferedReader.lines().collect(Collectors.joining());
//		Gson gson = new Gson();
//		User user = gson.fromJson(requestBody, User.class);
//		
//		
//		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * post요청 요청 데이터: JSON(User)
		 * doPost()
		 * JSON을 User객체로 변환 후 ToString으로 출력
		 * 응답 데이터: JSON {"success": "true"}
		 * 
		 * 
		 * post = CRUD 중 C
		 */
		
//		
//		System.out.println(user);
//		
//		response.setContentType("application/json;charset=UTF-8"); //getwrite 이전에	
//		PrintWriter out = response.getWriter();
//		
//		JsonObject responseBody = new JsonObject();
//		
//		if(user ==  null) {
//			responseBody.addProperty("success", false);			
//		}else {
//			responseBody.addProperty("success", true);
//		}
//		out.println(responseBody.toString());
	}

}
