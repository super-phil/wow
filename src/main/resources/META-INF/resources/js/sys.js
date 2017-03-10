/**
 * js 工具
 * Created by Phil on 2015/12/19.
 */
var sys = $.extend({}, sys);
$(function () {
    /**
     * js命名空间
     * 使用方法 sys.ns('a.b.c');
     * @type {{}}
     */
    sys.namespace = function (path) {
        var arr = path.split(".");
        var v = "";
        for (var i = 0; i < arr.length; i++) {
            if (i > 0) v += ".";
            v += arr[i];
            eval("if(typeof(" + v + ") == 'undefined') " + v + " = new Object();");
        }
    };
    /**
     * 获得项目根目录  使用方法 sys.path();
     * @returns {string}
     */
    sys.path = function () {
        var current_www_path = window.document.location.href;
        var path_name = window.document.location.pathname;
        var pos = current_www_path.indexOf(path_name);
        var local_host_path = current_www_path.substring(0, pos);
        var project_name = path_name.substring(0, path_name.substring(1).indexOf('/') + 1);
        return (local_host_path + project_name);
    };

    /**
     * 增加formatString 功能
     */
    sys.formatStr = function (str) {
        for (var i = 0; i < arguments.length - 1; i++) {
            str = str.replace('{' + i + '}', arguments[i + 1]);
        }
        return str;
    };
    /**
     * 将form form表单序列化成json 对象 用法 var obj = sys.serializeObject(form);
     * @param form
     * @returns {{}}
     */
    sys.serializeObject = function (form) {
        var o = {};
        $.each(form.serializeArray(), function (index) {
            if (o[this['name']]) {
                o[this['name']] = o[this['name']] + this['value'];
            } else {
                o[this['name']] = this['value'];
            }
        });
        return o;
    };
    /**
     * 开启新窗口 兼容所有浏览器
     * 模拟a标签
     * @param url
     */
    sys.openWin = function (url) {
        var a = document.createElement("a");
        a.setAttribute("href", url);
        a.setAttribute("target", "_blank");
        document.body.appendChild(a);
        a.click();
    };
    /**
     * 日期格式化
     * @param fmt
     * @returns {*}
     */
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    /**
     * 消息提示
     * @param result
     * @param callback
     */
    sys.tip = function (result, callback) {
        if (result.code == 0) {
            swal({
                title: result.msg||"ok",//提示消息
                type: "success",//成功类型
                timer: 1500,//1.8秒后自动关闭
                showConfirmButton: false//不显示确认按钮
            });
            if (callback) {
                callback();
            }
        } else {
            swal({
                    title: result.msg||"error",//提示消息
                    type: "error"//成功类型
                }
            );
            if (callback) {
                callback();
            }
        }
    };
    //确认弹窗
    sys.confirm = function (title, callback) {
        swal({
            title: title || "确认删除?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            closeOnConfirm: false
        }, function () {
            callback();
        });
    };
    /**
     * 提交表单
     * @param form
     * @param url
     * @param callback
     */
    sys.submit = function (form, url, callback) {
        var data = $(form).serialize();//序列化表单
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            success: function (result) {
                sys.tip(result, callback);
            }
        })
    };
    /**
     * 提交表单
     * @param data
     * @param url
     * @param callback
     */
    sys.submitData = function (data, url, callback) {
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            contentType: "application/json",
            success: function (result) {
                sys.tip(result, callback);
            }
        })
    };

    /**
     * 提交表单
     * @param form
     * @param url
     * @param callback
     */
    sys.submitNoTip = function (form, url, callback) {
        var data = $(form).serialize();//序列化表单
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            success: function (result) {
                callback(result);
            }
        })
    };
    /**
     * 删除
     * @param url
     * @param id
     * @param callback
     */
    sys.del = function (url, id, callback) {
        $.ajax({
            type: 'POST',
            url: url,
            data: {"id": id},
            dataType: 'json',
            success: function (result) {
                sys.tip(result, callback);
            }
        })
    };
});







