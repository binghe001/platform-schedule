<!DOCTYPE html>
<html>
<head>
  	<#import "/common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <title>编辑应用</title>
</head>

<body>
<div>
    <div class="modal-body">
        <form class="form-horizontal form" role="form" id="appupdateForm">
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">AppName<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control required" name="appName" placeholder="应用名" autocomplete="off" value="${appinfo.appName}" style="width: 500px;">
                    <input type="hidden" name="id" placeholder="应用名" value="${appinfo.id}">
                </div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">应用编号</label>
                <div class="col-sm-10"><input type="text" class="form-control required" name="appId" autocomplete="off" value="${appinfo.appId}" disabled
                                              placeholder="" style="width: 500px;"></div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">appKey</label>
                <div class="col-sm-10"><input type="text" class="form-control required" name="appId" autocomplete="off" value="${appinfo.appKey}" disabled
                                              placeholder="" style="width: 500px;"></div>
            </div>
            <div class="form-group">
                <label for="lastname" class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                    <textarea class="form-control" placeholder=""
                              style="width: 500px;height: 150px;" name="remark">${appinfo.remark}</textarea>
                </div>
            </div>
            <hr>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-6" style="text-align: center">
                    <button type="submit" class="btn btn-primary">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeLayer()">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/moment.min.js"></script>

<!-- 弹窗 -->
<script src="${request.contextPath}/static/plugins/layer/layer.js?t=20181226"></script>

<script type="text/javascript">

    function closeLayer() {
        window.parent.layer.closeAll();
    }

    jQuery.validator.addMethod("myValid01", function (value, element) {
        var length = value.length;
        var valid = /^[a-z][a-zA-Z0-9-]*$/;
        return this.optional(element) || valid.test(value);
    }, 'AppName必须是字母数字组合');

    var addModalValidate = $("#appupdateForm").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            appName: {
                required: true,
                myValid01: true
            },
            appId: {
                required: true,
                rangelength: [4, 20]
            }
        },
        messages: {
            appName: {
                required: '请输入' + "AppName",
                myValid01: 'AppName含有特殊字符'
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            //提交请求
            $.post(base_url + "/applist/doUpdate",  $("#appupdateForm").serialize(), function(data, status) {
                if (data.code == "200") {
                    layer.open({
                        title: '系统提示' ,
                        btn: [ '确定' ],
                        content: '编辑成功' ,
                        icon: '1',
                        end: function(layero, index){
                            window.parent.location.reload();
                        }
                    });
                } else {
                    layer.open({
                        title: '系统提示',
                        btn: [  '确定'],
                        content: '新增失败',
                        icon: '2'
                    });
                }
            });
        }
    });

</script>
</body>
</html>
