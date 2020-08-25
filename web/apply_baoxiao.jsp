<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提交报销申请</title>

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
    <li class="active">提交报销申请</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <div class="panel panel-default">
        <div class="panel-heading">报销申请单</div>
        <div class="panel-body">
            <form action="saveStartBaoxiao" method="post">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-8">

                            <div class="form-group">
                                <label>标题</label>
								<input type="text" class="form-control" id="title" name="title" placeholder="">
                            </div>

                            <div class="form-group">
                                <label for="col_name">金额</label>
                                <input type="text" class="form-control" id="money" name="money" placeholder="">
                            </div>
                            
                            <div class="form-group">
                                <label for="seo_title">报销事由</label>
                                <textarea class="form-control" rows="15" cols="10" id="remark" name="remark"></textarea>
                            </div>
                            
                            <div class="form-group">
                            	<button type="submit" class="btn btn-primary">提交报销申请</button>
                            	<button type="reset" class="btn btn-primary">重  填</button>
                            </div>

                        </div>
                </div>
                </div>
            </form>
        </div>

    </div>

</div>
</body>
</html>