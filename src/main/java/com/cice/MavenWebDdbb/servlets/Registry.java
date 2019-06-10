package com.cice.MavenWebDdbb.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cice.MavenWebDdbb.datasource.Datasource;

public class Registry extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String SUCCESS = "/main.jsp";
    private static String ERROR = "/index.jsp";
	
    public Registry() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/registry.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("name");
		System.out.println("Nombre -> "+nombre);
		String pass = request.getParameter("password");
		System.out.println("Password -> "+pass);
		
		String result = null;
		
		try {
			Datasource.addDataToDB(nombre, pass);
			result = SUCCESS;
		} catch (NoSuchAlgorithmException e) {		
			e.printStackTrace();						
		} catch (SQLException e) {
			e.printStackTrace();
			result = ERROR;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(result);
		dispatcher.forward(request, response);
	}

}
