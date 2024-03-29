<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>OA办公管理系统-添加用户</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
    <meta name="Keywords" content="keyword1,keyword2,keyword3"/>
    <meta name="Description" content="网页信息的描述"/>
    <meta name="Author" content="jinyuxiang"/>
    <meta name="Copyright" content="All Rights Reserved."/>
    <link href="${ctx}/yuxiang.ico" rel="shortcut icon" type="image/x-icon"/>
    <link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
    <!-- 导入bootStrap的库 -->
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
    <script type="text/javascript">
        $(function () {
            //如果有提示就弹出来
            if ("${tip}") {
                parent.showTip("${tip}");
            }

            /** 异步加载部门信息 */
            $.ajax({
                url: "${ctx}/oa/dept/getAllDeptsAndJobsAjax",
                type: "post",
                dataType: "json",
                async: false,
                success: function (data) {
                    //数据形式
                    // Dept [{id:0001,name:"研发部"},{id:0001,name:"实时数仓"}]
                    // Job [{code:0001,name:"董事长"},{code:0001,name:"java开发工程师"}]
                    // {
                    //      Dept: [{id:0001,name:"研发部"},{id:0001,name:"实时数仓"}],
                    //      Job: [{code:0001,name:"董事长"},{code:0001,name:"java开发工程师"}]
                    // }
                    var depts = data.depts;
                    var jobs = data.jobs;
                    $.each(depts, function () {
                        $("<option/>").val(this.code).html(this.name).appendTo("#deptSelect");

                        //var option = new Option(this.name, this.code);
                        //$("#deptSelect").append(option);

                        /** 直接拼接出完整的option  */
                        //$("#deptSelect").append("<option value='" + this.code + "'>" + this.name + "</option>");

                        //var select = document.getElementById("deptSelect");
                        //var option = new Option(this.name, this.code);
                        //select.appendChild(option);

                    });

                    $.each(jobs, function () {
                        $("<option/>").val(this.code).html(this.name).appendTo("#jobSelect");
                    });

                }, error: function () {
                    $.messager.alert("用户提示", "加载部门失败了", "error");
                }
            });

            //定义一个变量用于标记登录名是否被注册了
            var isExist;

            /** 在用户离开登录名称框的时候去异步校验输入的登录名是否已经被注册了 */
            $("#userId").blur(function () {
                var userId = this.value;
                /** 是否输入了登录名称 */
                if ($.trim(this.value) != "") {
                    $.ajax({
                        url: "${ctx}/oa/user/isUserValidAjax?userId=" + userId,
                        type: "post",
                        dataType: "text",
                        async: false,
                        success: function (data) {
                            /** 没有被注册：返回"success" */
                            if (data == "error") {
                                isExist = true;
                                $.messager.alert("用户提示", userId + "已经被注册了！", "error");
                            } else {
                                isExist = false;
                            }
                        }, error: function () {
                            $.messager.alert("用户提示", "校验登录名是否注册了失败！", "error");
                        }
                    });
                }
            });

            /** 添加用户，提交表单函数 */
            $("#btn_submit").click(function () {
                //对表单中所有字段做校验
                var userId = $("#userId");
                var name = $("#userName");
                var password = $("#userPassword");
                var repwd = $("#repwd");
                var email = $("#userEmail");
                var tel = $("#userTel");
                var phone = $("#userPhone");
                var qqNum = $("#userQqnum");
                var answer = $("#userAnswer");
                var msg = "";
                if ($.trim(userId.val()) == "") {
                    msg += "用户登录名不能为空！";
                    userId.focus();
                } else if (!/^\w{5,20}$/.test(userId.val())) {
                    msg += "用户登录名长度必须在5-20之间！";
                    userId.focus();
                } else if (isExist) {
                    msg += "用户登录名已经被注册！";
                } else if ($.trim(name.val()) == "") {
                    msg += "姓名不能为空！";
                    name.focus();
                } else if ($.trim(password.val()) == "") {
                    msg += "密码不能为空！";
                    password.focus();
                } else if (!/^\w{6,20}$/.test(password.val())) {
                    msg += "密码长度必须在6-20之间！";
                    password.focus();
                } else if (repwd.val() != password.val()) {
                    msg += "两次输入的密码不一致！";
                    repwd.focus();
                } else if ($.trim(email.val()) == "") {
                    msg += "邮箱不能为空！";
                    email.focus();
                } else if (!/^\w+@\w{2,}\.\w{2,}$/.test(email.val())) {
                    msg += "邮箱格式不正确！";
                    email.focus();
                } else if ($.trim(tel.val()) == "") {
                    msg += "电话号码不能为空！";
                    tel.focus();
                    //020-38216920 02034432323  0755
                } else if (!/^0\d{2,3}-?\d{7,8}$/.test(tel.val())) {
                    msg += "电话号码格式不正确";
                    tel.focus();
                } else if ($.trim(phone.val()) == "") {
                    msg += "手机号码不能为空！";
                    phone.focus();
                } else if (!/^1[3|4|5|8]\d{9}$/.test(phone.val())) {
                    msg += "手机号码格式不正确！";
                    phone.focus();
                } else if ($.trim(qqNum.val()) == "") {
                    msg += "QQ号码不能为空！";
                    qqNum.focus();
                } else if (!/^\d{5,12}$/.test(qqNum.val())) {
                    msg += "QQ号码长度必须在5-12之间！";
                    qqNum.focus();
                } else if ($.trim(answer.val()) == "") {
                    msg += "密保答案不能为空！";
                    answer.focus();
                }
                //直接提交
                if (msg != "") {
                    $.messager.alert("提示", msg, "error");
                } else {
                    $("#addUserForm").submit();
                }
            });
        });
    </script>
</head>
<body style="background: #F5FAFA;">
    <div style="text-align: center;">
        <form id="addUserForm" action="${ctx}/oa/user/addUser" method="post">
            <table class="table-condensed">
                <tbody>
                    <tr>
                        <td><label>登录名称：</label></td>
                        <td>
                            <input type="text" id="userId" name="userId" value="${user.userName}" class="form-control" placeholder="请输入您的登录名"/>
                        </td>
                        <td><label>用户姓名：</label></td>
                        <td>
                            <input type="text" id="name" name="name" value="${user.userName}" class="form-control" placeholder="请输入您的姓名"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>密码：</label></td>
                        <td>
                            <input type="text" id="password" name="password" value="${user.userPassword}" class="form-control" placeholder="请输入您的密码"/>
                        </td>
                        <td><label>确认密码：</label></td>
                        <td>
                            <input type="text" id="repwd" name="repwd" value="${user.userPassword}" class="form-control" placeholder="请输入您的确认密码"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>性别：</label></td>
                        <td>
                            <select name="sex" id="sex" class="btn btn-default">
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                        </td>
                        <td><label>部门：</label></td>
                        <td>
                            <select id="deptSelect" name="dept.id" class="btn btn-default"></select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>职位：</label></td>
                        <td>
                            <select id="jobSelect" name="job.code" class="btn btn-default"></select>
                        </td>
                        <td><label>邮箱：</label></td>
                        <td>
                            <input id="email" name="email" type="text" value="${user.userEmail}" class="form-control" placeholder="请输入您的电子邮件"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>电话：</label></td>
                        <td>
                            <input id="tel" name="tel" type="text" value="${user.userTel}" class="form-control" placeholder="请输入您的电话"/>
                        </td>
                        <td><label>手机：</label></td>
                        <td>
                            <input id="phone" name="phone" type="text" value="${user.userPhone}" class="form-control" placeholder="请输入您的手机"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>问题：</label></td>
                        <td>
                            <select name="question" class="btn btn-default" id="question">
                                <option value="1">您的生日</option>
                                <option value="2">您的出生地</option>
                                <option value="3">您的毕业院校</option>
                            </select>
                        </td>
                        <td><label>答案：</label></td>
                        <td>
                            <input id="answer" name="answer" type="text" value="${user.userAnswer}" class="form-control" placeholder="请输入您的答案"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>qq号码：</label></td>
                        <td>
                            <input id="qqNum" name="qqNum" type="text" value="${user.userQqnum}" class="form-control" placeholder="请输入您的qq号码"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div align="center" style="margin-top:20px;">
                <a id="btn_submit" class="btn btn-info"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
                <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;重置</button>
            </div>
        </form>
    </div>
</body>
</html>