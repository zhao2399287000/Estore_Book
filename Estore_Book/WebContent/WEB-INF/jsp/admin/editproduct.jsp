<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link rel="shortcut icon" href="favicon.ico">
	<link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
	<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
	<link href="css/animate.min.css" rel="stylesheet">
	<link href="css/style.min.css?v=4.0.0" rel="stylesheet">
	<script src="js/jquery.min.js?v=2.1.4"></script>

	<script src="js/content.min.js?v=1.0.0"></script>
	<script src="js/plugins/iCheck/icheck.min.js"></script>
	<script src="js/plugins/validate/jquery.validate.min.js"></script>
	<script src="js/plugins/validate/messages_zh.min.js"></script>
	<script src="js/plugins/date/WdatePicker.js"></script>
	<script src="js/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							商品管理
							<small>商品修改</small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link">
								<i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form method="post" class="form-horizontal" id="editGoodForm" 
						action="${pageContext.request.contextPath }/editProduct" 
						enctype="multipart/form-data">
							<!-- 商品名称输入框 -->
							<div class="form-group">
								<input type="hidden" name="id" value="${product.id }"/>
								<label class="col-sm-2 control-label">商品名称</label>
								<div class="col-sm-4">
									<input id="name" name="name" type="text" value="${product.name }" class="form-control">
									<br />
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<!-- 商品类型下拉框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品类型</label>
								<div class="col-sm-4">
									<select class="form-control m-b" name="category" id="category">
										<option>--请选择类别--</option>
										<option value="novel">小说</option>
										<option value="cartoon">童书</option>
										<option value="study">学习考试</option>
										<option value="literature">文学</option>
										<option value="music">音乐</option>
										<option value="art">艺术</option>
										<option value="science">科技</option>
									</select>
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品价格输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品价格</label>
								<div class="col-sm-4">
									<input name="price" type="text" value="${product.price }" class="form-control">
								</div>
							</div>
							<div class="hr-line-dashed"></div>


							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品数量</label>
								<div class="col-sm-4">
									<input name="pnum" type="text" value="${product.pnum }" class="form-control">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">出版时间</label>
								<div class="col-sm-4">
									<input name="publish" type="date" value="${product.publish }" class="form-control">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品图片</label>
								<div class="col-sm-4">
									<img src="${product.imgurl }" height="100">
									<input name="imgpic" type="file" class="form-control" onchange="checkimg(this)">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<!-- 商品详情输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品详情</label>
								<div class="col-sm-4">
									<textarea name="description" id="description" 
									class="form-control">${product.description }</textarea>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">商品操作</label>
								<div class="col-sm-4">
									<c:if test="${product.state == 1 }">
										<input type="radio" value="1" name="state" checked="checked"/>上架
										<input type="radio" value="0" name="state"/>下架
									</c:if>
									<c:if test="${product.state ==0 }">
										<input type="radio" value="1" name="state"/>上架
										<input type="radio" value="0" name="state" checked="checked"/>下架
									</c:if>
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">保存</button>
									<button class="btn btn-white" type="reset">重置</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
	<script type="text/javascript">
		function checkimg(obj){
			//获取到上传的文件的路径
			var filename = obj.value;
			//只能上传图片
			var index = filename.lastIndexOf(".");
			var name = filename.substring(index);
			if(name != ".jpg" && name != ".png" && name != ".gif"){
				alert("只能上传图片");
				obj.value = "";
			}
		}
		$(function (){
			$("#category").val("${product.category}");
		})
	</script>
</html>