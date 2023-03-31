package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class UserInfo
 */
@WebServlet("/user")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		
		System.out.println("GET 요청");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name", name);
		userMap.put("phone", phone);
		
		String userJson = gson.toJson(userMap);
		System.out.println(userJson);
		
		System.out.println(name);
		/**	get요청의 특징
		 *  1. 주소창, src, href, replace 등으로 요청할 수 있음.
		 *  2. 데이터를 전달하는 방법(Query String)
		 *   http(s)://서버주소:포트/요청메세지?key=value&key=value
		 */
		System.out.println(response.getCharacterEncoding());
		
		//response.addHeader("Content-Type", "text/html;charset=UTF-8");  //컨텐츠 타입에 따라 문자열 혹은 HTMl로 봄
		//response.setContentType("text/html;charset=UTF-8");  HTML 응답
		response.setContentType("application/json;charset=UTF-8");  //응답될때의 데이터가 UTF8로 잡힘 characterset의 필요가 없어짐
		
		PrintWriter out = response.getWriter();
//		out.println("이름:  " + name);
//		out.println("연락처: " + phone);
		out.println(userJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청");
		request.setCharacterEncoding("UTF-8");
		//System.out.println(request.getParameter("address"));
		
		Gson gson = new Gson();
		
		ServletInputStream inputStream = request.getInputStream(); //servlit입장에서 input
		BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
		
		String json = "";
		String line = null;
		while((line = reader.readLine()) != null) {
			json += line;
		}
		json.replaceAll("", "");
		System.out.println(json);
		
		
//		AtomicReference<String> jsonAtomic = new AtomicReference<>("");
//		reader.lines().forEach(line -> {
//			jsonAtomic.getAndUpdate(t -> t + line);
//		});
//		String json = jsonAtomic.get().replaceAll("", "");
//		System.out.println(json);
		
//String json = reader.lines().collect(Collectors.joining()); 코드 한줄짜리
		
		Map<String, String> jsonMap = gson.fromJson(json, Map.class);
		
		System.out.println(jsonMap);
		
	}//post요청 방법: form태그 method요청 /비동기 통신 / testtool사용(postman)
	 /** 1.
	  * 	<form action="요청메시지"method="post">
	  * 		<input name="key" value="value">
	  * 		<button type="submit">요청</button>
	  *  	</form>
	  *  
	  *  	CarInfo 서블릿
	  *  	mapping url: /car
	  *  	method: get, post
	  *  
	  *  	doGet
	  *  	Map: id, model을 key값으로
	  *  	{id =1, model=쏘나타}
	  *  	{id =2, model=K5}
	  *  	{id =3, model=SM6}
	  *  
	  *  	요청 데이터: id
	  *  	String id = getParameter("id");
	  *  
	  *  	응답 데이터
	  *  	{"id":"2", "model":"K5"} 제이슨형식으로
	  *  	
	  *  
	  *  	doPost
	  *  [
	  *  	{"id" ="1", "model"="쏘나타"}
	  *  	{"id"="2", "model"="K5"}
	  *  	{"id" ="3", "model"="SM6"}
	  *  ] 
	  *  
	  *  console
	  *  id(1):쏘나타
	  *  id(2):K5
	  *  id(3):SM6  응답은 그대로 print해서 HTMl의 문자열로 출력(response에 넣기)
	  */
}
