<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>OA办公管理系统-预览用户</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
    <meta name="Keywords" content="keyword1,keyword2,keyword3"/>
    <meta name="Description" content="网页信息的描述"/>
    <meta name="Author" content="jinyuxiang"/>
    <meta name="Copyright" content="All Rights Reserved."/>
    <link href="${ctx}/yuxiang.ico" rel="shortcut icon" type="image/x-icon"/>
    <link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
    <!-- 导入bootStrap的库 -->
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
</head>
<body style="background: #F5FAFA;">
    <div style="text-align: center;">
        <form id="preUserForm" action="${ctx}/oa/user/showPreUser" method="post">
            <table class="table-condensed" style="width: 80%;height: 80%;padding: 13px;">
                <tbody>
                    <tr>
                        <td><label>登录名称：</label></td>
                        <td><span class="label label-info">${user.userId}</span></td>
                        <td><label>用户姓名：</label></td>
                        <td><span class="label label-info">${user.userName}</span></td>
                        <td><label>性别：</label></td>
                        <td>
                            <span class="label label-info">
                                <c:if test="${user.userSex == 1}">男</c:if>
                                <c:if test="${user.userSex == 2}">女</c:if>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td><label>部门：</label></td>
                        <td><span class="label label-info">${user.dept.deptName}</span></td>
                        <td><label>职位：</label></td>
                        <td><span class="label label-info">${user.job.jobName}</span></td>
                        <td><label>邮箱：</label></td>
                        <td><span class="label label-info">${user.userEmail}</span></td>
                    </tr>
                    <tr>
                        <td><label>电话：</label></td>
                        <td><span class="label label-info">${user.userTel}</span></td>
                        <td><label>手机：</label></td>
                        <td><span class="label label-info">${user.userPhone}</span></td>
                        <td><label>qq号码：</label></td>
                        <td><span class="label label-info">${user.userQqnum}</span></td>
                    </tr>
                    <tr>
                        <td><label>问题：</label></td>
                        <td>
                            <c:if test="${user.userQuestion == 1}"><span class="label label-info">您的生日</span></c:if>
                            <c:if test="${user.userQuestion == 2}"><span class="label label-info">您的出生地</span></c:if>
                            <c:if test="${user.userQuestion == 2}"><span class="label label-info">您的毕业院校</span></c:if>
                        </td>
                        <td><label>答案：</label></td>
                        <td><span class="label label-info">${user.userAnswer}</span></td>
                    </tr>
                    <tr>
                        <td><label>创建人：</label></td>
                        <td><span class="label label-info">${user.creater.userName}</span></td>
                        <td><label>创建日期：</label></td>
                        <td><span class="label label-info">${user.createDate}</span></td>
                    </tr>
                    <tr>
                        <td><label>修改人：</label></td>
                        <td><span class="label label-info">${user.modifier.userName}</span></td>
                        <td><label>修改日期：</label></td>
                        <td><span class="label label-info">${user.modifyDate}</span></td>
                    </tr>
                    <tr>
                        <td><label>审核人：</label></td>
                        <td><span class="label label-info">${user.checker.userName}</span></td>
                        <td><label>审核日期：</label></td>
                        <td><span class="label label-info">${user.checkDate}</span></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</body>
</html>

