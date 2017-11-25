package main.java.com.generater;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

/**
 * Servlet implementation class MockGetData
 */
@WebServlet("/MockGetData")
public class MockGetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MockGetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
	    
		String csvFile = classLoader.getResource("airbnb.csv").getPath();
        response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
        Boolean skipFirst = request.getParameter("skipFirst").contains("T");
        String temp = request.getParameter("col");
        int col = Integer.valueOf(temp);
        int start = Integer.valueOf(request.getParameter("start"));
        int end = Integer.valueOf(request.getParameter("end"));
        
        
        String[] data = Generator.getData(csvFile, start, end, 	col, skipFirst);

        JSONArray jsonArray = new JSONArray();
        for (String str : data){
        	if (str!=null) jsonArray.add(str);
        }
        
        out.print(jsonArray.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
