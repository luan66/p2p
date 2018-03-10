<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#assign currentNav='personal' />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
		<#include "common/links-tpl.ftl" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		
		<script type="text/javascript">
			$(function () {

			    //1:手机认证：
				$("#showBindPhoneModal").click(function () {
                    //清楚表单内容:
					$("#phoneNumber").val("");
                    $("#verifyCode").val("");
                    //5,取消按钮禁用效果
					$("#sendVerifyCode").attr("disabled",false);
					//点击显示模态框:
					$("#bindPhoneModal").modal("show");
                })

				//2:效验数据: js要做什么?
                $("#sendVerifyCode").click(function () {
                	//1,手机号校验
					var phoneNumber = $("#phoneNumber").val();
					if(phoneNumber.length == 11){
						//2, 按钮禁用
						$("#sendVerifyCode").attr("disabled",true);
						//3,执行ajax请求,发送验证码
						$.post("/sendVerifyCode",{phoneNumber:phoneNumber},function (result) {
							//发送成功:
							if(result.success){
								//4,倒计时
								var time = 60;
								var interval = setInterval(function () {
									time = time - 1;
									//设置按钮内容:
                                    $("#sendVerifyCode").html(time+"秒后可重新发送")
									//5,倒计时完了之后:取消按钮禁用效果
									if(time <= 0) {
                                        clearInterval(interval)
                                        $("#sendVerifyCode").html("发送验证码")
                                        $("#sendVerifyCode").attr("disabled", false);
                                    }
                               },1000)
							}else{
								//发送失败:提示,按钮效果恢复
								$("#sendVerifyCode").attr("disabled",false);
								//提示框
								$.messager.alert("温馨提示",result.msg);
							}
						})
					}else{
					    $.messager.popup("手机号格式不正确!")
					}
                })

				//3:绑定手机:
				$("#bindPhone").click(function () {
                	//1,手机号校验
					var phoneNumber = $("#phoneNumber").val();
					if(phoneNumber.length == 11){
					    //2:发送ajax请求,绑定手机号:
						var param = {
							phoneNumber:$("#phoneNumber").val(),
							verifyCode:$("#verifyCode").val(),
						}
						$.post("/bindPhoneNumber",param,function (result) {
							if(result.success){
								$.messager.confirm("温馨提示","手机号绑定成功!",function () {
										$("#bindPhoneModal").modal("hide");
										window.location.reload();
								})
							}else{
								$.messager.alert("温馨提示",result.msg);
							}
						})
					}else{
					    $.messager.popup("手机号格式不正确!")
					}
                })


				//4:点击打开邮箱模态框
                $("#showBindEmailModal").click(function () {
                    //情况邮箱框内容：
                    $("#email").val("");
					//点击显示模态框:
					$("#bindEmailModal").modal("show");
                })

				//5: 发送邮箱：
                $("#bindEmail").click(function(){
                    $("#bindEmailForm").ajaxSubmit({
                        dataType:"json",
                        success:function(result){
                            if(result.success){
                                $.messager.confirm("提示:","恭喜你,邮箱发送成功,请及时激活",function(){
									$("#bindEmailModal").modal("hide");
                                })
                            }else{
                                $.messager.popup(result.msg);
                            }
                        }
                    });

                });
            })
		</script>
	</head>
	<body>

	<#--邮箱绑定模式框-->
    <div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail">
                        <div class="form-group">
                            <label for="email" class="col-sm-2 control-label">Email:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="email" name="email" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="bindEmail">保存</button>
                </div>
            </div>
        </div>
    </div>

	<#--绑定手机的模式框-->
    <div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" >
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone">
                        <div class="form-group">
                            <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" />
                                <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="verifyCode" name="verifyCode" />
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="bindPhone">保存</button>
                </div>
            </div>
        </div>
    </div>

	<#-- freemarker创建变量: -->
	<#assign currentNav="personal" />
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫nav,变量的值叫personal -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-body el-account">
							<div class="el-account-info">
								<div class="pull-left el-head-img">
									<img class="icon" src="/images/ms.png" />
								</div>
								<div class="pull-left el-head">
									<p>用户名:${loginInfo.username}</p>
									<p>最后登录时间：2015-01-25 15:30:10</p>
								</div>
								<div class="pull-left" style="text-align: center;width: 400px;margin:30px auto 0px auto;">
									<a class="btn btn-primary btn-lg" href="/recharge.do">账户充值</a>
									<a class="btn btn-danger btn-lg" href="/moneyWithdraw.do">账户提现</a>
								</div>
								<div class="clearfix"></div>
							</div>
							
							<div class="row h4 account-info">
								<div class="col-sm-4">
									账户总额：${account.totalAmount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									可用金额：${account.usableAmount?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									冻结金额：${account.freezedAmount?string("0.00")}元</span>
								</div>
							</div>
							
							<div class="row h4 account-info">
								<div class="col-sm-4">
									待收利息： ${account.unReceiveInterest?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									待收本金：${account.unReceivePrincipal?string("0.00")}元</span>
								</div>
								<div class="col-sm-4">
									待还本息：${account.unReturnAmount?string("0.00")}元</span>
								</div>
							</div>
							
							<div class="el-account-info top-margin">
								<div class="row">
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shiming.png" />
											</div>
											<div class="el-accoun-auth-right">
												<#if userInfo.hasRealAuth()>
													<h5>实名认证</h5>
													 已认证
												<#else>
													<h5>实名认证</h5>
													<p>
														未认证
														<a href="/realAuth.do" id="">立刻绑定</a>
													</p>
												</#if>
												<p>
												</p>
											</div>
											<div class="clearfix"></div>
											<p class="info">实名认证之后才能在平台投资</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shouji.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>手机认证</h5>
												<#if userInfo.hasBindPhoneNumber()>
													已认证
												<#else>
													<p>
														未认证
														<a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
													</p>
												</#if>

											</div>
											<div class="clearfix"></div>
											<p class="info">可以收到系统操作信息,并增加使用安全性</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/youxiang.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>邮箱认证</h5>

												<#if userInfo.hasBindEmail()>
													已认证
												<#else>
													<p>
														未绑定
														<a href="javascript:;" id="showBindEmailModal">去绑定</a>
													</p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">您可以设置邮箱来接收重要信息</p>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/baozhan.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>VIP会员</h5>
												<p>
													普通用户
													<a href="#">查看</a>
												</p>
											</div>
											<div class="clearfix"></div>
											<p class="info">VIP会员，让你更快捷的投资</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>		
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>