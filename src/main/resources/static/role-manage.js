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

   tableIns =  table.render({
        elem: '#demo'
        , url: '/roleManage/getRoles/'
        , title: '用户表'
        , page: true
        , cols: [[
             {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID'}
            , {field: 'role', title: '角色名称'}
            , {field: 'description', title: '描述信息'}
            , {field: 'enable', title: '是否可用'}
            , {field: 'createTime', title: '创建时间'}
            , {field:'right', title: '操作', toolbar:"#barDemo"}

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
            if (res.code != 0){
                layer.msg(res.message);
            }
        }

    });

    table.on('tool(tablelist)', function(obj){
        var data = obj.data;
        //分配角色
        if (obj.event == 'assgin'){
            assginPermission(data)
        }
    });
});

Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

function assginPermission(data) {
    layui.use('table', function(){
        //记录选中的数据:做缓存使用,作为参数传递给后台,然后生成pdf ,压缩
        var ids =new Array();
        //当前表格中的全部数据:在表格的checkbox全选的时候没有得到数据, 因此用全局存放变量
        var table_data=new Array();
        var table = layui.table;
        layer.open({
            type : 1,
            shade:0.1,
            offset: 'lt',
            // area : [ "650px", '520px' ],
            // title : "选择模板",
            maxmin : false,
            content : '<div><table id="templateTable" lay-filter="test"></table></div>',
            success : function(index, layero) {

                table.render({
                    elem: '#templateTable'

                    , url: '/permissionManage/getPermissions/'
                    ,page: true
                    , cols: [[
                        // {checkbox: true,LAY_CHECKED: false, fixed: 'left'}
                        ,{type: 'checkbox', fixed: 'left'}
                        , {field: 'id', title: 'ID', sort: true}
                        , {field: 'permission', title: '权限标识'}
                        , {field: 'description', title: '描述信息', sort: true}
                        , {field: 'available', title: '是否可用', sort: true}
                        , {field: 'createTime', title: '创建时间', sort: true}
                        , {field: 'resourceType', title: '资源类型'}
                        , {field: 'url', title: '资源路径'}
                        , {field: 'parentId', title: '父编号'}
                        , {field: 'parentIds', title: '父编号列表'}
                        ,{field:'right', title: '操作', toolbar:"#barDemo"}
                    ]]
                    , response: {
                        statusCode: 0
                    }
                    , parseData: function (res) {
                        return {
                            "code": res.code,
                            "msg": res.message,
                            "count": res.data.totalElements,
                            "data": res.data.content
                        };
                    }
                    , done:function (res, curr, count) {

                        AJAX.POST('/roleManage/getPermissionByRid',{rId:data.id},function (res) {
                            $(res).each(function(index,element){
                                debugger
                                ids.push(element.id)
                            })
                        })
                        //在缓存中找到id ,然后设置data表格中的选中状态
                        //循环所有数据，找出对应关系，设置checkbox选中状态
                        for(var i=0;i< res.data.length;i++){
                            for (var j = 0; j < ids.length; j++) {
                                //数据id和要勾选的id相同时checkbox选中
                                if(res.data[i].id == ids[j])
                                {
                                    //这里才是真正的有效勾选
                                    res.data[i]["LAY_CHECKED"]='true';
                                    //找到对应数据改变勾选样式，呈现出选中效果
                                    var index= res.data[i]['LAY_TABLE_INDEX'];
                                    $('.layui-table-fixed-l tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                                    $('.layui-table-fixed-l tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                                }
                            }
                        }
                        //设置全选checkbox的选中状态，只有改变LAY_CHECKED的值， table.checkStatus才能抓取到选中的状态
                        var checkStatus = table.checkStatus('templateTable');
                        if(checkStatus.isAll){
                            $(' .layui-table-header th[data-field="0"] input[type="checkbox"]').prop('checked', true);
                            $('.layui-table-header th[data-field="0"] input[type="checkbox"]').next().addClass('layui-form-checked');
                        }
                    }
                });

                //复选框选中监听,将选中的id 设置到缓存数组,或者删除缓存数组
                table.on('checkbox(test)', function (obj) {
                    if(obj.checked==true){
                        if(obj.type=='one'){
                            ids.push(obj.data.id);
                        }else{
                            for(var i=0;i<table_data.length;i++){
                                ids.push(table_data[i].id);
                            }
                        }
                    }else{
                        if(obj.type=='one'){
                            for(var i=0;i<ids.length;i++){
                                if(ids[i]==obj.data.id){
                                    ids.remove(i);
                                }
                            }
                        }else{
                            for(var i=0;i<ids.length;i++){
                                for(var j=0;j<table_data.length;j++){
                                    if(ids[i]==table_data[j].id){
                                        ids.remove(i);
                                    }
                                }
                            }
                        }
                    }
                });

            },

            btn : [ '确定', '关闭' ],
            yes : function(index, layero) {
                var checkStatus = table.checkStatus('templateTable'); //
                var datas = checkStatus.data;//选中的数据
                var ids = new Array();
                for (var i = 0;i < datas.length; i++){
                    ids[i] = datas[i].id;
                }

                AJAX.POST('/roleManage/assignPermissionForRole',{rId:data.id,pIds:ids.join(',')})
                layer.msg("保存成功");
                layer.closeAll();

            }
        });
    });
}


function create() {
    layer.open({
        type: 1,
        area: ['612px'],
        title: "新建角色",
        content: '<div class="formbody">' +
            '<div class="formtitle"><span>角色信息</span></div>' +
            '<ul class="forminfo"><form id="roleForm">' +
            '<li><label>角色标识</label><input name="role" type="text" class="dfinput" /><i></i></li>' +
            '<li><label>描述</label><input name="description" type="text" class="dfinput" /><i></i></li>' +
            '<li><label>&nbsp;</label><input name="" type="button" onclick="save()" class="btn" value="确认保存"/></li>' +
            '</form></ul>' +
            '</div>' //这里content是一个普通的String
    });
}

function save() {
    AJAX.POST('/roleManage/newRole',$('#roleForm').serialize(),function () {
        layer.msg("保存成功!");
        layer.closeAll();
        tableIns.reload();

    })
}

