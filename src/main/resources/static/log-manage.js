layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function () {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , carousel = layui.carousel //轮播
        , upload = layui.upload //上传
        , element = layui.element //元素操作
        , slider = layui.slider //滑块

    //监听Tab切换
    element.on('tab(demo)', function (data) {
        layer.tips('切换了 ' + data.index + '：' + this.innerHTML, this, {
            tips: 1
        });
    });

    tableIns = table.render({
        elem: '#demo'
        , id: 'logReload'
        , url: '/sysLogManage/getLogs/'
        , title: '日志'
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', sort: true}
            , {field: 'optype', title: '操作类型'}
            , {field: 'optContent', title: '操作内容', sort: true}
            , {field: 'optUserId', title: '操作人员', sort: true,templet: '#userIdTpl'}
            , {field: 'optime', title: '操作时间', sort: true}
            , {field: 'optRes', title: '操作结果'}
            , {field: 'apiReq', title: '接口请求参数'}
            , {field: 'apiRes', title: '接口响应参数'}
            , {field: 'optBefore', title: '修改前'}
            , {field: 'optAfter', title: '修改后'}
            , {field: 'ip', title: '操作IP'}
            , {field: 'url', title: '操作URL'}
            , {field: 'method', title: '类方法'}
            , {field: 'exception', title: '异常信息'}
        ]]
        , response: {
            statusCode: 0
        }
        , parseData: function (res) {
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.totalElements, //解析数据长度
                "data": res.data.content //解析数据列表
            };
        }
        , done: function (res, curr, count) {
            $('th').css({'background-color': '#f0f5f7', 'font-weight': 'bold'})

            //加载用户下拉框
            $("#users").load("/userManage/getUserList",function () {
                layui.use('form', function() {
                    var form = layui.form;
                    form.render();
                });
            });


            if (res.code != 0){
                layer.msg(res.message);
            }
        }

    });

    var $ = layui.$, active = {
        reload: function(){
            var optContent = $('#optContent');

            //执行重载
            table.reload('logReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    optContent: optContent.val(),
                    optContent1: optContent.val()
                }
            }, 'data');
        }
    };
debugger
    $('#searchBtn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    table.on('tool(tablelist)', function(obj){
        var data = obj.data;
        //分配角色
        if (obj.event == 'assgin'){
            assginRole(data)
        }
    });
});