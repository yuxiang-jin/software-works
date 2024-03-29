<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>OA办公管理系统-角色管理</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
    <meta http-equiv="description" content="This is role page"/>
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
        var showTip = function (tip) {
            $.messager.show({
                title: '添加用户',
                msg: "<span style='color:red;'>" + tip + "</span>",
                showType: 'slide'
            });
        };

        $(function () {
            //alert("${user_module_opera_popedoms}");
            //如果有提示就弹出来
            if ("${tip}") {
                $.messager.show({
                    title: '操作提示',
                    msg: "<font color='red'>${tip}</font>",
                    timeout: 2000,
                    showType: 'show'
                });
            }

            /** 获取所有的数据选项 */
            var dataBoxs = $("input[name='box'][id^='box_']");
            /** 给全选按钮绑定单击事件 */
            $("#checkAll").click(function () {
                dataBoxs.attr("checked", this.checked);
                $("tr[id^='dataTr_']").trigger(this.checked ? "mouseover" : "mouseout");
            });

            /** 如果数据按钮都选中，全选也选中，反之 */
            dataBoxs.click(function (event) {
                /** 取消单选事件的传播,单选点击完成以后,事件就结束了 */
                event.stopPropagation();
                /** 获取当前选中的数据行数 */
                var checkedBoxs = dataBoxs.filter(":checked");
                /** 判断选中的数据行是否等于总的数据行，如果是全选选中，如果不是全选不选中 */
                $("#checkAll").attr("checked", checkedBoxs.length == dataBoxs.length);
            });

            /** 给数据行绑定单击事件，通过单击行来控制数据的选中与不选中 */
            $("tr[id^='dataTr_']").click(function (event) {
                //获取点击当前行的数据选项的Id
                var boxId = this.id.replace("dataTr_", "box_");
                /** 触发对应选项的点击 */
                $("#" + boxId).trigger("click");
            }).hover(function () {
                $(this).css({backgroundColor: "#cccccc", cusor: "pointer"});
            }, function () {
                var boxId = this.id.replace("dataTr_", "box_");
                if (!$("#" + boxId).attr("checked")) {
                    $(this).css("backgroundColor", "#ffffff");
                }
            });

            /** 修改角色 */
            $("addRole").click(function () {
                $("#divDialog").dialog({
                    title: "添加角色",
                    cls: "easyui-dialog",
                    width: 480,
                    height: 275,
                    maximizable: true,
                    minimizable: false,
                    collapsible: true,
                    modal: true,
                    onClose: function () {
                        window.location = "${ctx}/oa/role/selectRole?pageIndex=${pageModel.pageIndex}";
                    }
                });
                /** div弹出以后，立即加载界面，并且显示界面 */
                $("#iframe").attr("src", "${ctx}/oa/role/showAddRole").show();
            });

            /** 给删除角色绑定单击事件 */
            $("#deleteRole").on("click", function () {
                /** 获取当前选中的数据行数 */
                var checkedBoxs = dataBoxs.filter(":checked");
                if (checkedBoxs.length > 0) {
                    /** 提示确认删除吗？ */
                    $.messager.confirm('角色管理', '确认删除角色吗？', function (r) {
                        if (r) {
                            /** 获取选中的数据行用户id */
                            var idMaps = checkedBoxs.map(function () {
                                return this.value;
                            });
                            /** 获取需要删除的用户的id */
                            //admin yuxiang
                            //alert(idMaps.get());
                            window.location = "${ctx}/oa/role/deleteRole?pageIndex=${pageModel.pageIndex}&ids=" + idMaps.get();
                        }
                    });
                } else {
                    $.messager.alert('错误提示', "请选择需要删除的角色！", 'error');
                }
            });
        });

        function updateRole(id) {
            $("#divDialog").dialog({
                title: "修改角色",
                cls: "easyui-dialog",
                width: 480,
                height: 275,
                maximizable: true,
                minimizable: false,
                collapsible: true,
                modal: true,
                onClose : function () {
                    window.location = "${ctx}/oa/role/selectRole?pageIndex=${pageModel.pageIndex}";
                }
            });
            /** div弹出以后，立即加载界面，并且显示界面 */
            $("#iframe").attr("src", "${ctx}/oa/role/showUpdateRole?id=" + id).show();
        }
    </script>
</head>
<body style="overflow: hidden;width: 100%;height: 100%;padding: 5px;">
    <div>
        <div class="panel panel-primary">
            <%--工具按钮区--%>
            <div style="padding-top: 4px; padding-bottom: 4px;">
                <a id="addRole" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
                <a id="deleteRole" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
            </div>

            <div class="panel-body">
                <table class="table table-bordered" style="float: right;">
                    <thead>
                        <tr>
                            <th style="text-align: center;"><input type="checkbox" id="checkAll"</th>
                            <th style="text-align: center;">名称</th>
                            <th style="text-align: center;">备注</th>
                            <th style="text-align: center;">操作</th>
                            <th style="text-align: center;">创建日期</th>
                            <th style="text-align: center;">创建人</th>
                            <th style="text-align: center;">修改日期</th>
                            <th style="text-align: center;">修改人</th>
                            <th style="text-align: center;">操作</th>
                        </tr>
                    </thead>
                    <c:forEach items="${roles}" var="role" varStatus="stat">
                        <tr align="center" id="dataTr_${stat.index}">
                            <td><input type="checkbox" name="box" id="box_${stat.index}" value="${role.roleId}"</td>
                            <td>${role.roleName}</td>
                            <td>${role.roleRemark}</td>
                            <td>
                                <span class="label label-success"><a href="${ctx}/oa/role/selectRoleUser?id=${role.roleId}" style="color: white;">绑定用户</a></span>
                                <span class="label label-info"><a href="${ctx}/oa/popedom/mgrPopedom?id=${role.roleId}" style="color: white;">绑定操作</a></span>
                            </td>
                            <td><fmt:formatDate value="${role.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${role.creater.userName}</td>
                            <td><fmt:formatDate value="${role.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td>${role.modifier.userName}</td>
                            <td><span class="label label-info"><a href="javascript:updateRole('${role.roleId}')">修改</a></span></td>
                        </tr>
                    </c:forEach>
                </table>
                <%--分页标签区--%>
                <yuxiang:pager pageIndex="${pageModel.pageIndex}" pageSize="${pageModel.pageSize}" recordCount="${pageModel.recordCount}"
                               submitUrl="${ctx}/oa/role/selectRole?pageIndex={0}"/>
            </div>
        </div>
    </div>
    <%--div作为弹出窗口--%>
    <div id="divDialog" style="overflow: hidden;">
        <iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%" style="display:none;"></iframe>
    </div>
</body>
</html>