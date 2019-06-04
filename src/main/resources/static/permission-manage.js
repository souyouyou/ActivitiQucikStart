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
        , url: '/permissionManage/getPermissions/'
        , title: '用户表'
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', sort: true}
            , {field: 'permission', title: '权限标识'}
            , {field: 'description', title: '描述信息', sort: true}
            , {field: 'available', title: '是否可用', sort: true}
            , {field: 'createTime', title: '创建时间', sort: true}
            , {field: 'resourceType', title: '资源类型'}
            , {field: 'url', title: '资源路径'}
            , {field: 'parentId', title: '父编号'}
            , {field: 'parentIds', title: '父编号列表'}
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
            assginRole(data)
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

function create() {
    layer.open({
        type: 1,
        area: ['612px'],
        title: "新建权限",
        content: '<div class="formbody">' +
            '<div class="formtitle"><span>基本信息</span></div>' +
            '<ul class="forminfo"><form id="permissionForm">' +
            '<li><label>权限标识</label><input name="permission" type="text" class="dfinput" value="permissionId" /><i></i></li>' +
            '<li><label>描述信息</label><input name="description" type="text" class="dfinput" value="description" /><i></i></li>' +
            '<li><label>资源类型</label><input name="resourceType" type="text" class="dfinput" value="menu/type" /><i></i></li>' +
            '<li><label>资源路径</label><input name="url" type="text" class="dfinput" value="" aria-valuemax="/usr/local"/></li>' +
            '<li><label>&nbsp;</label><input name="" type="button" onclick="save()" class="btn" value="确认保存"/></li>' +
            '</form></ul>' +
            '</div>' //这里content是一个普通的String
    });
}

function save() {
    AJAX.POST('/permissionManage/newPermission',$('#permissionForm').serialize(),function () {
        layer.msg("保存成功!");
        layer.closeAll();
        tableIns.reload();

    })
}

