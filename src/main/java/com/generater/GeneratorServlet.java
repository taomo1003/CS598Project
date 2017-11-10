package main.java.com.generater;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class GeneratorServlet
 */
@WebServlet("/GeneratorServlet")
public class GeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		ClassLoader classLoader = getClass().getClassLoader();
	    
		String csvFile = classLoader.getResource("airbnb.csv").getPath();
         
        Boolean skipFirst = true;
        int n = Generator.getLength(csvFile);

        if (n == 0) throw new FileNotFoundException("the length is " + n);
        if (skipFirst) n--;
        
        String[] data = Generator.getData(csvFile, 0 ,n, 0, skipFirst);
        
        String model = request.getParameter("model");
        if (model.equals("RandomEven") || model.equals("ByName"))
        {
        	JSONObject obj = Generator.generateJsonObj(model, data, 0, data.length-1 , 10000 , 10);
        	out.print(obj.toJSONString());
        }
        
        if (model.equals("BarChart"))
        {
        	JSONArray obj = Generator.generateJsonArrayForBar(Integer.valueOf(request.getParameter("size")),data);
        	out.print(obj.toJSONString());
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
