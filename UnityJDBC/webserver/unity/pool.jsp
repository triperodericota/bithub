<%@ page import="java.sql.*"%><%@ page import="javax.naming.*"%><%@ page import="javax.sql.*"%>
<html>
  <head>
    <title>UnityJDBC Test Query With Connection Pooling</title>
  </head>
  <body>

  <h2>Results</h2>

<%	
  	Connection con = null;
 	try { 		
 		// By using a connection pool (configured in META-INF/context.xml) the absolute path does not have to be exposed to the application.
 		 		
 		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		
		// Two implementations of connection pools
		// Connection pool using commons-dbcp library (see META-INF/context.xml for configuration)
		DataSource ds = (DataSource)envContext.lookup("jdbc/UnityJDBC");
		
		// Connection pool using Tomcat DBCP library (see META-INF/context.xml for configuration)
		//DataSource ds = (DataSource)envContext.lookup("jdbc/UnityJDBC2");
		
		con = ds.getConnection();		 		
 		
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
