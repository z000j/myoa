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
    <li class="active">我的报销单</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline" action="myBaoxiaoBill" method="post">
        <div class="panel panel-default">
            <div class="panel-heading">报销单列表</div>
            <input type="hidden" value="1" name="pageNum" id="page">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="10%">报销金额</th>
                        <th width="15%">标题</th>
                        <th width="20%">备注</th>
                        <th width="15%">时间</th>
                        <th width="10%">状态</th>
                        <th width="25%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="bill" items="${baoxiaobillList}">
	                    <tr>
	                        <td>${bill.id}</td>
	                        <td>${bill.money}</td>
	                        <td>${bill.title}</td>
	                        <td>${bill.remark}</td>
	                        <td>
	                        	<fmt:formatDate value="${bill.creatdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                        </td>
	                        <td>
				      		   <c:if test="${bill.state==1}">
				        	   		审核中
				        	   </c:if>
				        	   <c:if test="${bill.state==2}">
				        	  		审核完成
				        	   </c:if>
	                        </td>
	                        <td>
				 				<c:if test="${bill.state==1}">
				 					<a href="${pageContext.request.contextPath }/viewHisComment?id=${bill.id}" 
				 							class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看审核记录</a>
				 					<a href="${pageContext.request.contextPath}/viewCurrentImageByBill?billId=${bill.id}" target="_blank" 
				 					 		class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看当前流程图</a>
				 				</c:if>
				 				<c:if test="${bill.state==2}">
				 					<a href="baoxiaobillAction_delete?id=${bill.id}"
				 							class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> 删除</a>
				 					<a href="${pageContext.request.contextPath }/viewHisComment?id=${bill.id}" 
				 							class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看审核记录</a>			 				
				 				</c:if>
	                        </td>
	                    </tr>
					</c:forEach>
                    </tbody>
                </table>
                <br>
                <button name="首页" class="btn btn-primary btn-xs" onclick="jumpPage(1)" >首页</button>
                <button name="上一页" class="btn btn-primary btn-xs" onclick="jumpPage(${pageInfo.prePage})">上一页</button>
                <button name="下一页" class="btn btn-primary btn-xs"  onclick="jumpPage(${pageInfo.nextPage})">下一页</button>
                <button name="尾页" class="btn btn-primary btn-xs" onclick="jumpPage(${pageInfo.lastPage})">尾页</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    function jumpPage(pageNum) {
        document.getElementById("page").value=pageNum;
        document.getElementById("form").submit();
    }
</script>
</body>
</html>