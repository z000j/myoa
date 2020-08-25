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
    <li>用户管理</li>
    <li class="active">用户列表</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">用户列表&nbsp;&nbsp;&nbsp;
               <button type="button" class="btn btn-primary" title="新建" data-toggle="modal" data-target="#createUserModal">新建用户</button></div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="10%">帐号</th>
                        <th width="20%">电子邮箱</th>
                        <th width="15%">角色分配</th>
                        <th width="15%">上级主管</th>
                        <th width="25%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="user" items="${userList}">
	                    <tr>
	                        <td>${user.id}</td>
	                        <td>${user.name}</td>
	                        <td>${user.email}</td>
	                        <td>
		                        <select class="form-control" onchange="assignRole(this.value,'${user.name}')">
		                        	<c:forEach var="role" items="${allRoles}">
		                        		<option value="${role.id}" <c:if test="${role.name==user.roleName}">selected</c:if>>${role.name}</option>
		                        	</c:forEach>
		                        </select>
	                        </td>
	                        <td>
	                        	${user.manager}
	                        </td>
	                        <td>
				 				<a href="#" onclick="viewPermission('${user.name}')" class="btn btn-success btn-xs" data-toggle="modal" data-target="#editModal">
				 					<span class="glyphicon glyphicon-eye-open"></span> 查看权限</a>
	                        </td>
	                    </tr>
					</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

	<!--添加用户 编辑窗口 -->
	<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
	  <form id="permissionForm" action="saveUser" method="post">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">编辑用户</h3>
				</div>
				<div class="modal-body">
					<table class="table table-bordered table-striped" width="800px">
						<tr>
							<td>帐号</td>
							<td><input class="form-control" name="name" placeholder="名称"></td>
						</tr>					
						<tr>
							<td>初始密码</td>
							<td><input class="form-control" type="password" name="password" placeholder="名称"></td>
						</tr>
						<tr>
						    <td>电子邮箱</td>
						    <td><input class="form-control" name="email" placeholder="链接">
						    </td>
						</tr>
						<tr>
						    <td>级别</td>
						    <td>
						    	<select class="form-control" name="role" onchange="getNextManager(this.value)">
						    		<option value="1">普通员工</option>
						    		<option value="2">部门经理</option>
						    		<option value="3">总经理</option>
						    		<option value="4">财务</option>
						    	</select>
						    </td>
						</tr>					
						<tr>
						    <td>上级主管</td>
						    <td>
						    	<select  id="selManager" class="form-control" name="managerId">

								</select>
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
<!-- 查看用户角色权限窗口 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" >
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">权限列表</h3>
		</div>
		<div class="modal-body" id="roleList">
			 <table class="table table-bordered"  width="800px">	
			    <thead>	
			      	<tr>         
						<th>角色</td>
						<th>权限</th>
	                </tr>
                </thead>
                <tbody id="roleListBody">
                </tbody>
			 </table>				
		</div>
		<div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	  </div>
	</div>
	<script type="text/javascript">
		//查看当前员工的角色和权限列表
		function viewPermission(user_name) {
			$.ajax({
				url:'viewPermissionByUser',
				type:'post',
				data:{
					userName:user_name
				},
				dataType:'json',
				success:function(sysRole) {
					//先清空原来的内容
					$("#roleListBody").empty();
					
					var role_td = "<td>" + sysRole.name+"</td>";
					var permission_td = "<td>";
					var permission_list = sysRole.permissionList;
					$.each(permission_list,function(i,perm){
						permission_td += perm.name + "【" + perm.type + "】 <br/>"
					});
					permission_td += "</td>";
					
					var roleRow = $("<tr>"+role_td+permission_td+"</tr>");
					$("#roleListBody").append($(roleRow));
				},
				error:function(req,error) {
					alert(req.status+':'+error);
				}
			});
		}
		
		//重新分配待办人
		function assignRole(rid,uname) {
			var url = "assignRole";
			var params = {
				roleId:rid,
				userId:uname
			};
			$.getJSON(url,params,function(result){
				alert(result.msg);
				role = rid;
			});
		}
		
		//根据员工级别查找下一级别主管
		function getNextManager(levelNo) {
			var url = "findNextManager";
			var params = {
				level:levelNo
			};
			$.getJSON(url,params,function(managerList){
				$("#selManager").empty();
				$.each(managerList,function(i,manager){
					var opt = $("<option value='"+manager.id+"'>"+manager.name+"</option>");
					$("#selManager").append($(opt));
				});
			});
		}
	</script>
</div>
</body>
</html>