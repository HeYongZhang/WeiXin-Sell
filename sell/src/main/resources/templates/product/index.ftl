<html>
    <#include "../common/head.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
        <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="/sell/seller/product/save" method="post">

                        <div class="form-group">
                            <label>商品名称</label>
                            <input type="text" id="name" class="form-control" name="productName" value="${(productInfo.productName)! ''}" />
                        </div>

                        <div class="form-group">
                            <label>价格</label>
                            <input type="text" id="price"  class="form-control" name="productPrice" value="${(productInfo.productPrice)!''}" />
                        </div>

                        <div class="form-group">
                            <label>库存</label>
                            <input type="text" id="stock" class="form-control" name="produceStock" value="${(productInfo.produceStock)!''}" />
                        </div>

                        <div class="form-group">
                            <label>描述</label>
                            <input type="text" id="description" class="form-control" name="productDescription" value="${(productInfo.productDescription)!''}"/>
                        </div>

                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.productIcon)!''}" height="250" width="300">
                            <input type="text" id="icon" class="form-control" name="productIcon" value="${(productInfo.productIcon)!''}" />
                        </div>

                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" id="categoryType" class="form-control">
                                <#list productCategoryList as category>
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? &&  productInfo.categoryType=category.categoryType>
                                            selected
                                        </#if>
                                    >${category.categoryName}</option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<script type="text/javascript" src="/sell/js/jquery-3.3.1.min.js"></script>-->
<script>
    //表单验证
    $('form').submit(function () {
        //价格表达式
        var price_reg = /^([1-9]\d{0,9}(\.\d{1,2})?)$/;
        var stock_reg = /^[1-9]\d*$/;
        var str_reg = /[\u4e00-\u9fa5]/;
       var url_reg = /^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-.,@?^=%&:\/~+#]*[\w\-@?^=%&\/~+#])?$/;
        var name = $("#name").val();
        var price = $("#price").val();
        var stock = $("#stock").val();
        var description = $("#description").val();
        var icon = $("#icon").val();
        if(name==null || name==''){
            window.wxc.xcConfirm("请输入名字!", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if(!str_reg.test(name)){
            window.wxc.xcConfirm("填写商品名称格式错误!", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if (price==null||price==''){
            window.wxc.xcConfirm("请输入价格!", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if (!price_reg.test(price)){
            window.wxc.xcConfirm("请输入合理的价格!", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if (stock==null||stock==''){
            window.wxc.xcConfirm("请填写库存量!", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if(!stock_reg.test(stock)){
            if(stock!=0){
                window.wxc.xcConfirm("库存填写格式错误!", window.wxc.xcConfirm.typeEnum.error);
                return false;
            }
        }

        if(description==null || description == ''){
            window.wxc.xcConfirm("请对商品进行您的描述!",window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if (icon==null ||  icon ==''){
            window.wxc.xcConfirm("请填写网页图片的链接!",window.wxc.xcConfirm.typeEnum.error);
            return false;
        }else if(!url_reg.test(icon)){
            window.wxc.xcConfirm("填写网页图片的URL错误!",window.wxc.xcConfirm.typeEnum.error);
            return false;
        }
        return true;
    });

    //图片显示
    $("#icon").change(function () {
       $("img").attr("src",$(this).val());
    });

    //替换option中的 ‘，’
    var reg = new RegExp(",","g");
    $("#categoryType option").each(function (e) {
       $(this).val($(this).val().replace(reg,""));
    });


</script>
</body>
</html>

