<%@ page import="java.io.*,java.util.*" %>
<html>
<head>
<title>---</title>
</head>
<body>

<%
   // New location to be redirected
   String site = new String("home.action");
   response.setStatus(response.SC_MOVED_TEMPORARILY);
   response.setHeader("Location", site);
%>
</body>
</html>