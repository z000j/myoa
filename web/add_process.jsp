<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>发布流程</title>

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
    <li class="active">发布流程</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <div class="panel panel-default">
        <div class="panel-heading">发布流程定义</div>
        <div class="panel-body">
            <form action="${pageContext.request.contextPath}/deployProcess" enctype="multipart/form-data" method="post">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-5">

                            <div class="form-group">
                                <label for="processName">流程名称 </label>
                                <input type="text" class="form-control" id="processName" name="processName" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="fileName">选择流程文件</label>
                                <input type="file" class="form-control" id="fileName" name="processFile" placeholder="">
                            </div>

							<button type="submit" class="btn btn-primary">部署流程</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>

</div>



</body>
</html>