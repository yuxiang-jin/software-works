<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>OA办公管理系统-当前用户</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
    <meta http-equiv="description" content="This is user page"/>
    <link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
    <!-- 导入bootStrap的库 -->
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
    <script type="text/javascript">
        var showTip = function (tip) {
            $.messager.show({
                title: '添加用户',
                msg: "<span style='color:red;'>" + tip + "</span>",
                showType: 'slide'
            });
        };

        /** 文档加载完成 */
        $(function () {

            if ("${tip}") {
                $.messager.show({
                    title: '添加用户',
                    msg: "<span style='color:red;'>${tip}</span>",
                    showType: 'slide'
                });
            }

            /** 用户界面效果开发  */
            /** 得到所有数据行的jQuery对象 */
            var dataTrs = $("tr[id^='dataTr_']");
            dataTrs.hover(function () {
                $(this).css({backgroundColor: "#eeecdd", cusor: "pointer"});
            }, function () {
                // 判断这一行的单选是否被选中了,如果被选中不要恢复成白色背景
                // 得到当前行对应的单选的id
                var trBoxId = this.id.replace("dataTr_", "box_");
                var trBox = $("#" + trBoxId);
                if (!trBox.attr("checked")) {
                    $(this).css("backgroundColor", "#ffffff");
                }
            });

            /** 全选 */
            /** 得到所有数据行的选项按钮 */
            var dataBoxs = $("input[name='box'][id^=box_]");
            $("#checkAll").click(function () {
                dataBoxs.attr("checked", this.checked);
                /** 全选如果被选中,则所有行的背景色被选中,反之 */
                dataTrs.trigger(this.checked ? "mouseover" : "mouseout");
            });

            /** 如果没有全部选中那么全选按钮也应该不选中 */
            var boxSize = dataBoxs.length;
            /** 给每个单选绑定点击事件 */
            dataBoxs.on("click", function (event) {
                /** 取消单选事件的传播,单选点击完成以后,事件就结束了 */
                event.stopPropagation();
                /** 拿到当前选中的单选 */
                var checkedBoxs = dataBoxs.filter(":checked");
                $("#checkAll").attr("checked", checkedBoxs.length == boxSize);
            });

            /** 为所有数据行绑定点击事件 */
            dataTrs.click(function () {
                /** 得到当前所点击行的对应单选按钮对象 */
                var trBoxId = this.id.replace("dataTr_", "box_");
                var trBox = $("#" + trBoxId);
                trBox.trigger("click");
            });

            $("#back").click(function () {
                window.location = "${ctx}/oa/role/selectRole";
            });

            $("#bindUser").click(function () {
                $("#divDialog").dialog({
                    title: '绑定用户',
                    cls: 'easyui-dialog',
                    width: 780,
                    height: 350,
                    maximizable: true,
                    minimizable: false,
                    collapsible: true,
                    modal: true,
                    onClose: function () {
                        window.location = "${ctx}/oa/role/selectRoleUser?id=${role.roleId}&pageIndex=${pageModel.pageIndex}";
                    }
                });
                /** 为此窗口的iframe触发界面请求 */
                $("#iframe").attr("src", "${ctx}/oa/role/showBindUser?id=${role.roleId}");
            });

            /** 解绑用户 */
            $("#unBindUser").on("click", function () {
                /** 获取所有选中的数据行的id,传输到后台解绑用户  */
                /** 拿到当前选中的单选 */
                var checkedBoxs = dataBoxs.filter(":checked");
                /** admin,yuxiang */
                if (checkedBoxs.length > 0) {
                    var maps = checkedBoxs.map(function () {
                        return this.value;
                    });
                    //alert(maps.get());
                    window.location = "${ctx}/oa/role/unbindUser?ids=" + maps.get() + "&pageIndex=${pageModel.pageIndex}&id=${role.roleId}";
                } else {
                    $.messager.alert("角色提示", "请选择您要解绑的用户！", "error");
                }
            });
        });

        function preUser(userId) {
            $("#divDialog").dialog({
                title: "用户详情",
                cls: "easyui-dialog",
                width: 780,
                height: 350,
                maximizable: true,
                minimizable: false,
                collapsible: true,
                modal: true,
                onClose: function () {

                }
            });
            /** 为此窗口的iframe触发界面请求 */
            $("#iframe").attr("src", "${ctx}/oa/user/showPreUser?userId=" + userId);
        }
    </script>
</head>
<body style="overflow: hidden; width: 98%; height: 98%;">
        <!-- 工具按钮区 -->
        <div class="panel panel-primary" style="padding-left: 5px;">
            <div style="padding-top: 4px;padding-bottom: 4px;">
                <a id="back" class="btn btn-primary"><span class="glyphicon glyphicon-hand-left"></span>&nbsp;返回</a>
                <a id="bindUser" class="btn btn-success"><span class="glyphicon glyphicon-copy"></span>&nbsp;绑定用户</a>
                <a id="unBindUser" class="btn btn-danger"><span class="glyphicon glyphicon-paste"></span>&nbsp;解绑用户</a>
            </div>
            <div class="panel-title" style="background-color: #11a9e2;">
                <h3 class="panel-title">
                    <label style="color: white;font-size: 15px;"><span class="glyphicon glyphicon-user"></span>&nbsp;${role.roleName}</label>
                </h3>
            </div>
            <div class="panel-body">
                <table class="table-bordered">
                    <thead>
                        <tr style="font-size: 12px;" align="center">
                            <th style="text-align: center;"><input id="checkAll" type="checkbox"/></th>
                            <th style="text-align: center;">账户</th>
                            <th style="text-align: center;">姓名</th>
                            <th style="text-align: center;">性别</th>
                            <th style="text-align: center;">部门</th>
                            <th style="text-align: center;">职位</th>
                            <th style="text-align: center;">手机号码</th>
                            <th style="text-align: center;">邮箱</th>
                            <th style="text-align: center;">审核人</th>
                        </tr>
                    </thead>
                    <c:forEach items="${requestScope.users}" var="user" varStatus="stat">
                        <tr id="dataTr_${stat.index}" align="center">
                            <td><input type="checkbox" name="box" id="box_${stat.index}" value="${user.userId}"/></td>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userSex == 1 ? '男' : '女' }</td>
                            <td>${user.dept.deptName}</td>
                            <td>${user.job.jobName}</td>
                            <td>${user.userPhone}</td>
                            <td>${user.userEmail}</td>
                            <td>${user.checker.userName}</td>
                        </tr>
                    </c:forEach>
                </table>
                <!-- 分页标签区 -->
                <yuxiang:pager pageIndex="${pageModel.pageIndex}" pageSize="${pageModel.pageSize}" recordCount="${pageModel.recordCount}"
                              submitUrl="${ctx}/oa/user/selectUser?pageIndex={0}?id=${role.roleId}"/>
            </div>
        </div>

        <div id="divDialog" style="display:none;">
            <!-- 放置一个添加用户的界面  -->
            <iframe id="iframe" frameborder="0" style="width: 100%; height: 100%;"></iframe>
        </div>
</body>
</html>
