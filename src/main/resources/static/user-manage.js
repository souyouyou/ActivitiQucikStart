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
        , url: '/userManage/getUsers/'
        , title: '用户表'
        , page: true
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', sort: true}
            , {field: 'username', title: '用户名'}
            , {field: 'nickname', title: '昵称', sort: true}
            , {field: 'password', title: '密码', sort: true}
            , {field: 'mobile', title: '手机号码', sort: true}
            , {field: 'email', title: '电子邮箱'}
            ,{field:'right', title: '操作', width:'120px',toolbar:"#barDemo"}
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
            assginRole(data);
            //更新用户信息
        }else if (obj.event == 'update'){
            update(data);
        }else if (obj.event == 'leaveApply'){
            debugger
            leaveApply(data);
        }
    });
});


function leaveApply(data) {
    debugger
    layer.open({
        type: 1,
        area: ['612px'],
        title: "更新用户信息",
        content: '<div class="formbody">' +
            '<div class="formtitle"><span>基本信息</span></div>' +
            '<ul class="forminfo"><form id="applyForm">' +
            '<input type="hidden" name="id" type="text" class="dfinput" value="'+data.id+'"  />' +
            '<input type="hidden" name="username" type="text" class="dfinput" value="'+data.username+'"  />' +
            '<li><label>请假事由</label><input name="reason" type="text" class="dfinput"/><i></i></li>' +
            '<li><label>请假时长</label><input name="days" type="number" class="dfinput"/><i></i></li>' +
            '<li><label>&nbsp;</label><input name="" type="button" onclick="leave()" class="btn" value="确认保存"/></li>' +
            '</form></ul>' +
            '</div>' //这里content是一个普通的String
    });
}


function leave() {
    AJAX.POST('/flowManage/leaveApply',$('#applyForm').serialize(),function () {
        layer.msg("保存成功!");
        layer.closeAll();
        tableIns.reload();

    })
}
function update(data){
    layer.open({
        type: 1,
        area: ['612px'],
        title: "更新用户信息",
        content: '<div class="formbody">' +
            '<div class="formtitle"><span>基本信息</span></div>' +
            '<ul class="forminfo"><form id="userForm">' +
            '<input type="hidden" name="id" type="text" class="dfinput" value="'+data.id+'"  />' +
            '<li><label>用户名</label><input name="username" type="text" class="dfinput" value="'+data.username+'"/><i></i></li>' +
            '<li><label>昵称</label><input name="nickname" type="text" class="dfinput" value="'+data.nickname+'"/><i></i></li>' +
            '<li><label>密码</label><input name="password" type="password" class="dfinput" value="'+data.password+'"/><i></i></li>' +
            '<li><label>手机号码</label><input name="mobile" type="text" class="dfinput" value="" value="'+data.mobile+'"/></li>' +
            '<li><label>电子邮箱</label><input name="email" type="text" class="dfinput" value="" value="'+data.email+'"/></li>' +
            '<li><label>描述信息</label><textarea name="" cols="" rows="" class="textinput"></textarea></li>' +
            '<li><label>&nbsp;</label><input name="" type="button" onclick="save()" class="btn" value="确认保存"/></li>' +
            '</form></ul>' +
            '</div>' //这里content是一个普通的String
    });
}


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

function assginRole(data) {
    layui.use('table', function(){
        //记录选中的数据:做缓存使用,作为参数传递给后台,然后生成pdf ,压缩
        var ids =new Array();
        //当前表格中的全部数据:在表格的checkbox全选的时候没有得到数据, 因此用全局存放变量
        var table_data=new Array();
        var table = layui.table;
        layer.open({
            type : 1,
            // area : [ "800px", '600px' ],
            // title : "选择模板",
            maxmin : false,
            content : '<div><table id="templateTable" class="tablelist" lay-filter="test"></table></div>',
            success : function(index, layero) {

                table.render({
                    elem: '#templateTable'
                    // ,height: '475px'
                    // ,width:'100%'
                    , url: '/roleManage/getRoles/'
                    ,page: true
                    , cols: [[
                          {checkbox: true,LAY_CHECKED: false, fixed: 'left'}
                        , {field: 'id', title: 'ID'}
                        , {field: 'role', title: '角色名称'}
                        , {field: 'description', title: '描述信息'}
                        , {field: 'enable', title: '是否可用'}
                        , {field: 'createTime', title: '创建时间'}
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
                        
                        AJAX.POST('/userManage/getRolesByUid',{uid:data.id},function (res) {
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

                AJAX.POST('/userManage/assignRoleForUser',{uId:data.id,rIds:ids.join(',')})
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
        title: "新建用户",
        content: '<div class="formbody">' +
            '<div class="formtitle"><span>基本信息</span></div>' +
            '<ul class="forminfo"><form id="userForm">' +
            '<li><label>用户名</label><input name="username" type="text" class="dfinput" /><i></i></li>' +
            '<li><label>昵称</label><input name="nickname" type="text" class="dfinput" /><i></i></li>' +
            '<li><label>密码</label><input name="password" type="password" class="dfinput" /><i></i></li>' +
            '<li><label>手机号码</label><input name="mobile" type="text" class="dfinput" value="" /></li>' +
            '<li><label>电子邮箱</label><input name="email" type="text" class="dfinput" value="" /></li>' +
            '<li><label>描述信息</label><textarea name="" cols="" rows="" class="textinput"></textarea></li>' +
            '<li><label>&nbsp;</label><input name="" type="button" onclick="save()" class="btn" value="确认保存"/></li>' +
            '</form></ul>' +
            '</div>' //这里content是一个普通的String
    });
}

function save() {
    AJAX.POST('/userManage/newUser',$('#userForm').serialize(),function () {
        layer.msg("保存成功!");
        layer.closeAll();
        tableIns.reload();

    })
}

