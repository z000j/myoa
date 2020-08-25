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
    <li>流程管理</li>
    <li class="active">查看流程</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">部署信息管理列表</div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="10%">ID</th>
                        <th width="60%">流程名称</th>
                        <th width="20%">发布时间</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="dep" items="${depList}">
	                    <tr>
	                        <td>${dep.id}</td>
	                        <td>${dep.name}</td>
	                        <td>
	                        	<fmt:formatDate value="${dep.deploymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                        </td>
	                        <td>
	                            <a href="delDeployment?deploymentId=${dep.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> 删除</a>
	                        </td>
	                    </tr>
					</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
    
   <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">流程定义信息列表</div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="12%">ID</th>
                        <th width="18%">名称</th>
                        <th width="10%">流程定义的KEY</th>
                        <th width="10%">流程定义的版本</th>
                        <th width="15%">流程定义的规则文件名称</th>
                        <th width="15%">流程定义的规则图片名称</th>
                        <th width="10%">部署ID</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="pd" items="${pdList}">
	                    <tr>
	                        <td>${pd.id}</td>
	                        <td>${pd.name}</td>
	                        <td>${pd.key}</td>
	                        <td>${pd.version}</td>
	                        <td>${pd.resourceName}</td>
	                        <td>${pd.diagramResourceName}</td>
	                        <td>${pd.deploymentId}</td>
	                        <td>
	                          <a target="_blank" href="viewImage?deploymentId=${pd.deploymentId}&imageName=${pd.diagramResourceName}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看流程定义图</a>
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