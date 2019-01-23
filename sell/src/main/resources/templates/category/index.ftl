<html>
    <#include "../common/head.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
        <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <p style="color: red">温馨提示：修改type会导致该类目下的商品无法显示，需要自行修改商品信息中的类目</p>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form onsubmit="return validate()" role="form" action="/sell/seller/category/save" method="post">
                        <div class="form-group">
                            <label>标签名字</label>
                            <input type="text" id="name" class="form-control" name="categoryName" value="${(productCategory.categoryName)! ''}" />
                        </div>

                        <div class="form-group">
                            <label>type</label>
                            <input type="text" id="type" class="form-control" name="categoryType" value="${(productCategory.categoryType)!''}" />
                        </div>
                        <input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/sell/js/jquery-1.9.1.js"></script>
<script>
    var reg = new RegExp(",","g");
    var replace_type = $("#type").val().replace(reg,"")
    $("#type").val(replace_type);

    var type  = $("#type").val();

    var str_reg = /[\u4e00-\u9fa5]/;
    var int_reg = /^[1-9]\d*$/;
    function validate() {
        var name = $("#name").val();
        var type = $("#type").val();
        if(name==null || name==''){
            window.wxc.xcConfirm("请输入标签名字", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if(!str_reg.test(name)){
            window.wxc.xcConfirm("非法标签", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }
        if(type==null || type==''){
            window.wxc.xcConfirm("type不能为空,且唯一", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if(!int_reg.test(type)){
            window.wxc.xcConfirm("type格式不匹配，正确格式为非0正整数", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }
        return true;
    };

    $("#type").change(function () {
        var type2  = $("#type").val();
        if(type2==null || type2==''){
            return;
        }else if(type2==type){
            return;
        }

        $.ajax({
            type:"post",
            url:"/sell/seller/category/exit",
            dataType:"html",
            data:{type:$("#type").val()},
                success : function(data) {
                    if(data=="false"){
                        window.wxc.xcConfirm("type已存在", window.wxc.xcConfirm.typeEnum.error);
                        $("#type").val(type);
                    }
                }
        });
    })
</script>
</body>
</html>

