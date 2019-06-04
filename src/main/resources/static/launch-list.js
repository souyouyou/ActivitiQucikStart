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
        , url: '/flowManage/flows'
        , title: '模型列表'
        , id: 'testReload'
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: '任务ID', sort: true}
            , {field: 'processInstanceId', title: '流程实例ID'}
            , {field: 'assignee', title: '指定人', sort: true}
            , {field: 'createTime', title: '创建时间', sort: true}
            , {field:'endTime', title: '结束时间'}
            , {field:'endTime', title: '结束时间', toolbar:"#barDemo"}
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




