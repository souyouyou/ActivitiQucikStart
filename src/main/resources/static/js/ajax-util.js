/**
 * 前端控制
 */
//状态码
web_code = {
    SUCCESS: "0",
    FAIL: "1",
    SYS_ERROR: "-1",
    NO_LOGIN: "003",
    NO_PRIVILEGE: "004"
};

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function simpleSuccess(result) {

    //如果成功，则读取后端返回的操作指令
    if (result.code == web_code.SUCCESS) {
        //刷新
        if (result['refresh']) {
            window.location.reload();
            return;
        }
        //返回
        if (result['back']) {
            window.location.href = document.referrer;
        }
        //跳转
        if (result['redirectUrl'] != null) {
            window.location.href = result.redirectUrl;
            return;
        }
        return result.data;
    }
}

//对jquery的ajax方法封装
__ajax = function (url, data, success, type, contentType) {
    success = success||function(data){};
    data = data || {};
    var config = {
        url: url,
        type: type,
        dataType: "json",
        data: data,
        async: false,
        success: function (result) {
            if (result.code != web_code.SUCCESS) {
                layer.alert(result.message, {icon: 5, title: '提示'})
                return;
            }
            success(simpleSuccess(result));
        },
        error: function () {
            layer.msg("服务繁忙,请稍后再试！")
        }
    };
    //如果需要token校验
    if (contentType) {
        config.contentType = contentType;
    }

    var token = $.cookie("token");
    if (token) {
        config.beforeSend = function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(token));
        }
    }
    $.ajax(config)
};

AJAX = {
    GET: function (url, data, success) {
        __ajax(url, data, success, "get");
    },
    POST_JSON: function (url, data, success) {
        __ajax(url, data, success, "post", "application/json");
    },
    POST: function (url, data, success) {
        __ajax(url, data, success, "post");
    },
    DELETE: function (url, data, success) {
        __ajax(url, data, success, "delete");
    },
    PUT: function (url, data, success) {
        __ajax(url, data, success, "put", "application/json");
    },
    PATCH: function (url, data, success) {
        __ajax(url, data, success, "patch", "application/json");
    },
    INCLUDE: function (url, id) {
        $.ajax({
            url: url,
            type: "get",
            dataType: "html",
            error: function (code) {
                $("#" + id).html("加载失败");
            },
            success: function (result) {
                $("#" + id).html(result);
            }
        })
    }
};
