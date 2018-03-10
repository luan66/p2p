<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
        <link type="text/css" rel="stylesheet" href="/js/UploadFive 1.2.2/uploadifive.css" />
        <script type="text/javascript" src="/js/UploadFive 1.2.2/jquery.uploadifive.js"></script>
		
		<style type="text/css">
			.uploadify{
				height: 20px;
				font-size:13px;
				line-height:20px;
				text-align:center;
				position: relative;
				margin-left:auto;
				margin-right:auto;
				cursor:pointer;
				background-color: #337ab7;
			    border-color: #2e6da4;
			    color: #fff;
			}
		</style>
		<script type="text/javascript">
			$(function(){

                //材料上传:
                $("#btn_uploadUserFiles").uploadifive({
                    'auto': true,
                    'uploadScript': '/uploadFile',
                    'fileObjName': 'file',
                    'buttonText': '上传文件',
                    'fileType': 'image',
                    'multi': false,
                    'fileSizeLimit': 5242880,
                    'onUploadComplete': function (file, data) {
                    	location.href = "/userFile";
					},
                    onFallback: function () {
                        alert("该浏览器无法使用!");
                    },
                })
			})
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl"/>
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="userFile"/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							用户认证文件信息
						</div>
					</div>
					<div class="row">
					  <#list userFiles as file>
					  <div class="col-sm-6 col-md-4">
					    <div class="thumbnail">
					      <img src="${file.image}" style="width: 200px;" />
					      <div class="caption">
					        <h4>${(file.fileTypeId.title)!""}</h4>
					        <p>得分：${file.score} &nbsp;&nbsp;状态：${file.stateDisplay}</p>
					      </div>
					    </div>
					  </div>
					  </#list>
					</div>
					<div class="row">
						<a href="javascript:;" id="btn_uploadUserFiles"></a>
					</div>
				</div>
			</div>
		</div>		
		<#include "common/footer-tpl.ftl" />		
	</body>
</html>