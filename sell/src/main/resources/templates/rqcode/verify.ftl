<html>
    <#include "../common/head.ftl">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">

            <div class="modal fade" id="mymodel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                莲花府管理员登录
                            </h4>
                        </div>
                        <div class="modal-body" id="model_context">
                            内容...
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.close()">关闭</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<input hidden type="text"  id = "wxid" value="${openid}">
<input hidden type="text"  id = "uuid" value="${uuid}">
<script type="text/javascript" src="/sell/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/sell/js/bootstrap.min.js"></script>
<script>
    var sell = {
        "openid":$("#wxid").val(),
        "uuid":$("#uuid").val()
    };
    $.ajax({
        url:"/sell/seller/verify",
        type: "POST",
        data: {sell:JSON.stringify(sell)},
        dataType:"json",
        success:function (data) {
            $("#model_context").html(data.msg);
            $("#mymodel").modal('show');
        },
        error:function () {
            alert("连接超时");
        }
    });
</script>
</body>
</html>
