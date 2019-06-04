layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function () {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , carousel = layui.carousel //轮播
        , upload = layui.upload //上传
        , element = layui.element //元素操作
        , slider = layui.slider //滑块

    var tableIns = table.render({
        elem: '#demo'
        , url: '/flowManage/auditTask'
        , title: '模型列表'
        , id: 'testReload'
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'name', title: '任务名称', sort: true}
            , {field: 'createTime', title: '创建时间'}
            , {field: 'applyUser', title: '申请人', sort: true}
            , {field: 'applyTime', title: '申请时间', sort: true}
            , {field:'days', title: '请假时长'}
            , {field:'reason', title: '请假事由'}
            , {field:'applyStatus', title: '任务审批状态'}
        ]]
        , response: {
            statusCode: 0
        }
        , parseData: function (res) {
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.extraData.totalRows, //解析数据长度
                "data": res.data //解析数据列表
            };
        }
        , done: function (res, curr, count) {
            $('th').css({'background-color': '#f0f5f7', 'font-weight': 'bold'})
            if (res.code != 0){
                layer.msg(res.message);
            }
        }

    });

    table.on('tool(tablelist)', function(obj){
        var data = obj.data;
        //修改
        if (obj.event == 'del'){
            layer.confirm('确定删除该模型?', {icon: 3, title:'提示'}, function(index){
                //do something
                AJAX.DELETE('/deployments/'+data.id)
                tableIns.reload();
                layer.close(index);
            });
        }
    });

});




