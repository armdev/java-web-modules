<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="am.iunetworks.tp3.ejb.control.util.*, am.iunetworks.tp3.ejb.control.jobs.*, org.quartz.JobExecutionContext"%>    
<%
	RMIHelper rmiHelper = new RMIHelper() ;
	JobsManager jobManager = JobsManager.getInstance() ;
	String[] allGroups = jobManager.getJobGroups() ;

	java.util.Map<String, JobExecutionContext> workingJobs = jobManager.getWorkingJobs() ;
	
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>EJB Controller Console</title>
</head>
<body>
<table border="1" cellpadding="10" cellspacing=0>
	<tr>
		<th colspan="2">Name</th><th>Actions</th><th>Info</th>
	</tr>
	<%
		for(int i=0; i<allGroups.length; i ++) {
	%>
	<tr>
		<th colspan="2"><%= allGroups[i] %></th>
		<td>
			<a href="Controller?action=startGroup&amp;param1=<%=allGroups[i] %>">start</a>
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
	
	<%
		java.util.List<org.quartz.JobDetail> jobs = jobManager.listJobs(allGroups[i]) ;
		for(int j=0; j<jobs.size(); j ++) {
			org.quartz.JobDetail job = jobs.get(j) ;
			JobExecutionContext jec = workingJobs.get(job.getName()) ;
	%>
		<tr>
			<td>
				&nbsp;
			</td>
			<td><%=job.getName()%></td>
			<td>
				<% if (jec == null) { %>
				<a href="Controller?action=startJob&amp;param1=<%=job.getName() %>&amp;param2=<%=allGroups[i] %>">start</a>
				<% } %>
			</td>
			<td>
			<% if (jec != null) { %>
			
				Fire time: <%= jec.getFireTime() %> <br/>
				Status: <%= jec.get("status") %> <br/>
				<a href="job-result/<%=job.getName() %>-result.html">result</a>
			<% } else { %>
				Last execution <a href="job-result/<%=job.getName() %>-result.html">result</a> 
			<% } %>
			
			</td>
		</tr>
	<% }
	}
	%>
</table>
</body>
</html>