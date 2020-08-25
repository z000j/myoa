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
    <li>系统管理</li>
    <li class="active">角色管理</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline" action="saveRoleAndPermissions" method="post">
    	<div class="panel panel-default">
    		<div class="panel-heading">添加角色</div>
    		<div class="panel-body">
    			<div class="form-group">
                     <label for="name">角色名称 </label>
                     <input type="text" class="form-control" id="roleName" name="name" placeholder="">
                     <button type="submit" class="btn btn-primary">保存角色和权限</button>
                </div>
    		</div>
    	</div>
        <div class="panel panel-default">
            <div class="panel-heading">权限分配列表&nbsp;&nbsp;&nbsp;
            		<button type="button" class="btn btn-primary" title="新建" data-toggle="modal" data-target="#editModal">新建权限</button></div>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
	                    <tr>
	                        <th width="20%">主菜单</th>
	                        <th width="80%">子菜单和权限</th>
	                    </tr>
                    </thead>
                    <tbody>
						<c:forEach var="menu" items="${allPermissions}">
		                    <tr>
		                        <td>
		                        <input type="checkbox" name="permissionIds" value="${menu.id}"/> 
		                        ${menu.name}</td>
		                        <td>
		                        	<c:forEach var="subPermission" items="${menu.children}">
		                        		<input type="checkbox" name="permissionIds" value="${subPermission.id}"/> 
		                        		&nbsp;&nbsp;${subPermission.name} [${subPermission.type}]<br/>
		                        	</c:forEach>
		                        </td>
		                    </tr>
						</c:forEach>
                    </tbody>
                </table>
                
            </div>
        </div>
    </form>
    
</div>

	<!-- 编辑窗口 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
	  <form id="permissionForm" action="saveSubmitPermission" method="post">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">编辑权限</h3>
				</div>
				<div class="modal-body">
					<table class="table table-bordered table-striped" width="800px">
						<tr>
							<td>名称</td>
							<td><input class="form-control" name="name" placeholder="名称"></td>
						</tr>					
						<tr>
							<td>类型</td>
							<td>
							    <select class="form-control" name="type">
							    	<option value="menu">菜单</option>
							    	<option value="permission">权限</option>
							    	<option value="menu|permission">菜单|权限</option>
							    </select>
							</td>
						</tr>
						<tr>
						    <td>链接</td>
						    <td><input class="form-control" name="url" placeholder="链接">
						    </td>
						</tr>
						<tr>
							<td>权限标识</td>
							<td>
							   <input class="form-control" name="percode" placeholder="权限标识">
							</td>
						</tr>
						<tr>
						    <td>父节点</td>
						    <td>
						    	<select class="form-control" name="parentid">
						    		<option value="1">权限</option>
						    		<c:forEach var="menu" items="${menuTypes}">
						    			<option value="${menu.id}">${menu.name}</option>
						    		</c:forEach>
						    	</select>
						    </td>
						</tr>
						<tr>
						   <td>是否可用</td>
						   <td>
						   		<input type="checkbox" name="available" value="1"/>
						   </td>
						</tr>

					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal"
						aria-hidden="true" onclick="javascript:document.getElementById('permissionForm').submit()">保存</button>
					<button class="btn btn-default" data-dismiss="modal"
						aria-hidden="true">关闭</button>
				</div>
			</div>
		</div>
	  </form>
	</div>
	
	
</body>
</html>