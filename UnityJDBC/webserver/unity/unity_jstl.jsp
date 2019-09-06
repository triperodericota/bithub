<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.net.URL,java.io.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<title>Unity Query with JSP/JSTL Example</title>
</head>
<body>

<sql:setDataSource var="data" driver="unity.jdbc.UnityDriver"
     url="jdbc:unity://C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/unity/WEB-INF/unity/UnityDemo.xml"
     user=""  password=""/>

<sql:query dataSource="${data}" var="result">
SELECT n_nationkey, n_nationkey, n_name, n_regionkey FROM OrderDB.nation WHERE n_name >= 'C';
</sql:query>
 
<table border="1">
<tr>
<th>_id</th>
<th>Nation Id</th>
<th>Nation Name</th>
<th>Region Id</th>
</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
<td><c:out value="${row._id}"/></td>
<td><c:out value="${row.n_nationkey}"/></td>
<td><c:out value="${row.n_name}"/></td>
<td><c:out value="${row.n_regionkey}"/></td>
</tr>
</c:forEach>
</table>

</body>
</html>