<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>办公管理系统-修改模块</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
    <meta name="Keywords" content="keyword1,keyword2,keyword3"/>
    <meta name="Description" content="网页信息的描述"/>
    <meta name="Author" content="靳玉祥"/>
    <meta name="Copyright" content="All Rights Reserved." />
    <link href="${ctx}/jinyuxiang.ico" rel="shortcut icon" type="image/x-icon"/>
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

            $("#btn_submit").click(function () {
                var name = $("#moduleName").val();
                var url = $("#moduleUrl").val();
                var msg = "";
                if (!/^\S{1,15}$/.test($.trim(name))) {
                    msg = "请输入菜单的名称";
                }else if (!/^\S{1,}$/.test($.trim(url))) {
                    msg = "请输入菜单地址";
                }

                if (msg != "") {
                    $.messager.alert("菜单提示", msg, "error");
                    return;
                }
                $("#updateModuleForm").submit();
            })
        })
    </script>
</head>
<body style="background: #F5FAFA;">
    <div style="text-align: center;">
        <form id="updateModuleForm" action="${ctx}/oa/module/updateModule" method="post" style="padding: 10px;">
            <input type="hidden" value="${module.moduleCode}" name="moduleCode"/>
            <table class="table-condensed" width="90%" height="100%">
                <tbody>
                    <tr>
                        <td align="center"><label>模块名称：</label></td>
                        <td><input type="text" id="name" name="name" value="${module.moduleName}" class="form-control" placeholder="请输入您的模块名称"/></td>
                    </tr>
                    <tr>
                        <td align="center"><label>操作地址：</label></td>
                        <td><input type="text" id="url" name="url" value="${module.moduleUrl}" class="form-control" placeholder="请输入您的操作地址"/></td>
                    </tr>
                    <tr>
                        <td align="center"><label>模块备注：</label></td>
                        <td><textarea type="text" id="remark" name="remark" class="form-control" placeholder="请输入您的备注信息">${module.moduleRemark}</textarea></td>
                    </tr>
                </tbody>
            </table>
            <div align="center" style="margin-top: 20px;">
                <a id="btn_submit" class="btn btn-info"><span class="glyphicon glyphicon-plus"></span>&nbsp;修改</a>
                <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;重置</button>
            </div>
        </form>
    </div>
</body>
</html>
