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
                        <table class="table table-hover table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                        <#list productPage.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="100" width="100" title="${productInfo.productName}" src="${productInfo.productIcon}"></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.produceStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td class="text-info">
                                <a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                            </td>
                                  <td class="text-danger">
                                          <#if productInfo.getproductStatusEnum().getMessage() == "在架">
                                              <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}" onclick="">下架</a>
                                              <#else>
                                               <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                          </#if>
                                  </td>
                        </tr>
                        </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-12 column">
                        <ul class="pagination">
                    <#if currenPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else >
                         <li><a href="/sell/seller/product/list?page=${currenPage-1}">上一页</a></li>
                    </#if>

                        <#--总页数 OrderDTOpage.getTotalPages() -->
                        <#--当前页数 currenpage-->
                        <#--前置起始点 5-currenpage == 2 -->
                        <#--后置结束点 OrderDTOpage.getTotalPages()-currenpage ==2 -->
                        <#--循环数 5-->

                   <#list 1..productPage.getTotalPages() as index>
                       <#if currenPage == index>
                            <li class="active"><a href="#">${index}</a></li>
                       <#else>
                            <li><a href="/sell/seller/product/list?page=${index}">${index}</a></li>
                       </#if>
                   </#list>


                    <#if currenPage gte productPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                          <li><a href="/sell/seller/product/list?page=${currenPage+1}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
    </body>
</html>

<#--
<h1>${OrderDTOpage.totalPages }</h1>

<#list OrderDTOpage.content as orderDTO>
    ${orderDTO.orderId}</<br>
</#list>-->
