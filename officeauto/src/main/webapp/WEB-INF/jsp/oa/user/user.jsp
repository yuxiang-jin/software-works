<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>智能办公系统-用户管理</title>
    <%@ include file="/WEB-INF/taglib.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
    <meta http-equiv="description" content="This is user page" />
    <link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
    <!-- 导入bootStrap的库 -->
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css"/>
    <script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"/>
    <script type="text/javascript">
        var showTip = function (tip) {
            $.messager.show({
                title: '添加用户',
                msg: "<span style='color:red;'>" + tip + "</span>",
                showType: 'slide'
            });
        };
        //文档加载完成
        $(function () {
            //激活用户操作
            $("input[id^='checkUser_']").switchbutton({
                onChange: function (checked) {
                    var status = checked ? "1" : "0";
                    window.location = "${ctx}/oa/user/activeUser?userId=" + this.value + "&status=" + status +
                        "&pageIndex=${pageModel.pageIndex}&name=${user.userName}&phone=${user.userPhone}&dept.id=${user.dept.deptId}&job.code=${user.job.jobCode}";
                }
            });

            if ("${tip}") {
                $.messager.show({
                    title: '添加用户',
                    msg: "<span style='color:red;'>${tip}</span>",
                    showType: 'slide'
                });
            }

            /*$(document).ajaxStart($.blockUI({
                css: {backgroundColor: '#11a9e2', color: '#fff'},
                message: '<h6>正在加载...</h6>'
            })).ajaxStop($.unblockUI);*/

            //异步加载部门信息
            $.ajax({
                url: "${ctx}/oa/dept/getAllDeptsAndJobsAjax",
                type: "post",
                dataType: "json",
                async: true,
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

                        /** 直接拼接出完整的option */
                        //$("#deptSelect").append("<option value='" + this.code + "'>" + this.name + "</option>");

                        //var select = document.getElementById("deptSelect");
                        //var option = new Option(this.name, this.code);
                        //select.appendChild(option);
                    });

                    $.each(jobs, function () {
                        $("<option/>").val(this.code).html(this.name).appendTo("#jobSelect");
                    });

                    /** 选中部门和职位 */
                    if ("${user.job.jobCode}") $("#jobSelect").val("${user.job.jobCode}");
                    if ("${user.dept.deptId}") $("#deptSelect").val("${user.dept.deptId}");
                }, error: function () {
                    $.messager.alert("用户提示", "加载部门失败了", "error");
                }
            })

            /** 用户界面效果开发 */
            /** 得到所有数据行的jQuery对象 */
            var dataTrs = $("tr[id^='dataTr_']");
            dataTrs.hover(function () {
                $(this).css({backgroundColor: "#eeecdd", cursor: "pointer"});
            }, function () {
                //判断这一行的单选是否被选中了,如果被选中不要恢复成白色背景
                //得到当前行对应的单选的id
                var trBoxId = this.id.replace("dataTr_", "box_");
                var trBox = $("#" + trBoxId);
                if (!trBox.attr("checked")) {
                    $(this).css("backgroundColor", "#ffffff");
                }
            });

            /** 全选 */
            /** 得到所有数据行的选项按钮 */
            var dataBoxs = $("input[name='box'][id^='box_']");
            $("#checkAll").click(function () {
                dataBoxs.attr("checked", this.checked);
                /** 全选如果被选中,则所有行的背景色被选中,反之 */
                dataTrs.trigger(this.checked ? "mouseover" : "mouseout");
            });

            /** 如果没有全部选中,那么全选按钮也应该不选中 */
            var boxSize = dataBoxs.length;
            /** 给每个单选绑定单击事件 */
            dataBoxs.on("click", function () {
                /** 取消单选事件的传播,单选点击完成以后,事件就结束了 */
                event.stopPropagation();
                /** 拿到当前选中的单选 */
                var checkedBoxs = dataBoxs.filter(":checked");
                $('#checkAll').attr("checked", checkedBoxs.length == boxSize);
            });

            /** 为所有数据行绑定单击事件 */
            dataTrs.click(function () {
                /** 得到当前所点击行的对应单选按钮对象 */
                var trBoxId = this.id.replace("dataTr_", "box_");
                var trBox = $("#" + trBoxId);
                trBox.trigger("click");
            });

            /** 删除用户 */
            $("#deleteUser").on("click", function () {
                /** 获取所有选中的数据行的id,传输到后台删除数据 */
                /** 拿到当前选中的单选 */
                var checkedBoxs = dataBoxs.filter(":checked");
                /** admin,yuxiang */
                if (checkedBoxs.length > 0) {
                    $.messager.confirm('用户提示', '你确认删除吗?', function (r) {
                        if (r) {
                            /** 真正删除 */
                            var maps = checkedBoxs.map(function () {
                                return this.value;
                            });
                            //alert(maps.get());
                            window.location = "${ctx}/oa/user/deleteUser?ids=" + maps.get()
                                + "&pageIndex=${pageModel.pageIndex}&name=${user.userName}&phone=${user.userPhone}&dept.id=${user.dept.deptId}&job.code=${user.job.jobCode}";
                        }
                    });
                } else {
                    $.messager.alert("用户提示", "请选择您要删除的用户！", "error");
                }
            });

            /** 添加用户操作 */
            $("#addUser").click(function () {
                $("#divDialog").dialog({
                    //标题
                    title: "添加用户",
                    //class
                    cls: "easyui-dialog",
                    //宽度和高度
                    width: 600,
                    height: 420,
                    //最大化和最小化
                    maximizable: true,
                    mimimizable: false,
                    //可伸缩
                    collapsible: true,
                    //模态窗口
                    modal: true,
                    //关闭窗口
                    onClose: function () {
                        window.location = "${ctx}/oa/user/selectUser?pageIndex=${pageModel.pageIndex}&name=${user.userName}&phone=${user.userPhone}&dept.id=${user.dept.deptId}";
                    }
                });
                /** 为此窗口的iframe触发界面请求 */
                $("#iframe").attr("src", "${ctx}/oa/user/showAddUser");
            });

            var moduleOperasUrls = "${moduleOperasUrls}";
            if (moduleOperasUrls.indexOf("addUser") == -1) {
                $("#addUser").hide();
            }
            if (moduleOperasUrls.indexOf("deleteUser") == -1) {
                $("#deleteUser").hide();
            }
            if (moduleOperasUrls.indexOf("updateUser") == -1) {
                $("span[id^='updateUser_']").css("display", "none");
            }
            if (moduleOperasUrls.indexOf("activeUser") == -1) {
                $("input[id^='checkUser_']").switchbutton('disable');
            }
        });

        function updateUser(userId) {
            $("#divDialog").dialog({
                title: "修改用户",
                cls: "easyui-dialog",
                width: 600,
                height: 420,
                maximizable: true,
                minimizable: false,
                collapsible: true,
                modal: true,
                //关闭窗口
                onClose : function () {
                    window.location = "${ctx}/oa/user/selectUser?pageIndex=${pageModel.pageIndex}&name=${user.userName}&phone=${user.userPhone}&dept.id=${user.dept.deptId}";
                }
            });
            /** 为此窗口的iframe触发界面请求 */
            $("#iframe").attr("src", "${ctx}/oa/user/showUpdateUser?userId=" + userId);
        }

        function preUser(userId) {
            $("#divDialog").dialog({
                title: "用户详情",
                cls: "easyui-dialog",
                width: 820,
                height: 350,
                maximizable: true,
                minimizable: false,
                collapsible: true,
                modal: true,
                onClose : function () {

                }
            });
            $("#iframe").attr("src", "${ctx}/oa/user/showPreUser?userId=" + userId);
        }
    </script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
    <%--工具按钮区--%>
    <form class="form-horizontal" action="${ctx}/oa/user/selectUser" method="post" style="padding-left: 5px;">
        <table class="table-condensed">
            <tbody>
            <tr>
                <td>
                    <input name="name" type="text" class="form-control" placeholder="姓名" value="${user.userName}"/>
                </td>
                <td>
                    <input name="phone" type="text" class="form-control" placeholder="手机号码" value="${user.userPhone}"/>
                </td>
                <%--<td>
                    <input name="status" type="text" class="form-control" placeholder="状态" value="${user.userStatus}"/>
                </td>--%>
                <td>
                    <select class="btn btn-default" placeholder="部门" id="deptSelect" name="dept.id">
                        <option value="0">==请选择部门==</option>
                    </select>
                </td>
                <td>
                    <select class="btn btn-default" placeholder="职位" id="jobSelect" name="job.code">
                        <option value="0">==请选择职位==</option>
                    </select>
                </td>
                <td>
                    <button type="submit" id="selectUser" class="btn btn-info"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
                    <a id="addUser" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
                    <a id="deleteUser" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
                </td>
            </tr>
            </tbody>
        </table>
    </form>

    <div class="panel panel-primary" style="padding-left: 10px;">
        <div class="panel-heading" style="background-color: #11a9e2;">
            <h3 class="panel-title">用户信息列表</h3>
        </div>
        <div class="panel-body" >
            <table class="table table-bordered">
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
                        <th style="text-align: center;">激活状态</th>
                        <th style="text-align: center;">审核人</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.users}" var="user" varStatus="stat">
                    <tr id="dataTr_${stat.index}" align="center">
                        <td><input type="checkbox" name="box" id="box_${stat.index}" value="${user.userId}"/></td>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.userSex ==1 ? '男' : '女'}</td>
                        <td>${user.dept.deptName}</td>
                        <td>${user.job.jobName}</td>
                        <td>${user.userPhone}</td>
                        <td>${user.userEmail}</td>
                        <td>
                            <c:if test="${user.userStatus == 1}">
                                <input id="checkUser_${user.userId}" value="${user.userId}" name="status" class="easyui-switchbutton" data-options="onText:'激活,offText:'冻结'" checked/>
                            </c:if>
                            <c:if test="${user.userStatus != 1}">
                                <input id="checkUser_${user.userId}" value="${user.userId}" name="status" class="easyui-switchbutton" data-options="onText:'激活',offText:'冻结'"/>
                            </c:if>
                        </td>
                        <td>${user.checker.userName}</td>
                        <td>
                            <span id="updateUser_${stat.index}" class="label label-info"><a href="javascript:updateUser('${user.userId}')" style="color: white;">修改</a></span>
                            <span id="preUser_${stat.index}" class="label label-success"><a href="javascript:preUser('${user.userId}')" style="color: white;">预览</a></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <%--分页标签--%>
            <yuxiang:pager pageIndex="${pageModel.pageIndex}" pageSize="${pageModel.pageSize}" recordCount="${pageModel.recordCount}"
                           submitUrl="${ctx}/oa/user/selectUser?pageIndex={0}&name=${user.userName}&phone=${user.userPhone}&dept.id=${user.dept.deptId}&job.code=${user.job.jobCode}"/>
        </div>
    </div>

    <div id="divDialog" style="display: none;">
        <%--放置一个添加用户的界面--%>
        <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
    </div>

</body>
</html>