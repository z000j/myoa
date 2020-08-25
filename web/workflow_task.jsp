<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>流程管理</title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="css/content.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="js/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<!--路径导航-->
<ol class="breadcrumb breadcrumb_nav">
    <li>首页</li>
    <li>报销管理</li>
    <li class="active">我的待办事务</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">待办事务列表</div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="15%">任务ID</th>
                        <th width="25%">任务名称</th>
                        <th width="20%">创建时间</th>
                        <th width="20%">办理人</th>
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
						<c:forEach var="task" items="${taskList}">
						  <tr>
	                        <td>${task.id}</td>
	                        <td>${task.name}</td>
	                        <td>
	                        	<fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
	                        </td>
	                        <td>${task.assignee}</td>
	                        <td>
	                        	<a href="${pageContext.request.contextPath}/viewTaskForm?taskId=${task.id}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-plus"></span> 办理任务</a>    
	                            <a href="${pageContext.request.contextPath}/viewCurrentImage?taskId=${task.id}" target="_blank" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看当前流程图</a>
	                        </td>
	                      </tr>
						
						
					  </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </form>
    
</div>
</body>
</html>