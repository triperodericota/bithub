<%@ page import="java.sql.*,java.io.File"%><%@ page import="unity.jdbc.*"%>
<html>
<head>
<title>Sample UnityJDBC Query</title>
</head>
<body>

<h2>Results</h2>
 
<%	
  	Connection con = null;
 	try {
 		// Absolute path addresses are used to the source files.  
 		// TODO: Change these addresses according to your installation.
 		// Note: These files should not be web-accessible as they contained protected information.  The directory they are in should be accessible only to the web server.
 		String path = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/unity/WEB-INF/unity/UnityDemo.xml";
 		con = (UnityConnection) DriverManager.getConnection("jdbc:unity://"+path);
 		
 		Statement stmt = con.createStatement();
 		ResultSet rst = stmt.executeQuery("select n_nationkey, n_name, r_name from OrderDB.Nation N, PartDB.Region R WHERE N.n_regionkey = R.r_regionkey and n_nationkey < 10;");
 		out.println("<table><tr><th>Nation Id</th><th>Nation Name</th><th>Region Name</th></tr>"); 		
 		while (rst.next())
 		{	out.println("<tr><td>"+rst.getString(1)+"</td><td>"+rst.getString(2)+"</td><td>"+rst.getString(3)+"</td></tr>"); 		
 		}
 		out.println("</table>");
 	}
 	catch (Exception ex)
 	{
 		out.println("SQLException: " + ex);
 	}
 	finally
 	{
 		if (con != null)
 			try
 			{
 				con.close();
 			}
 			catch (SQLException ex)
 			{
 				out.println("SQLException: " + ex);
 			}
 	}	
 %>
 
<% out.println("<h3>Query complete</h3>"); %>

</body>
</html>