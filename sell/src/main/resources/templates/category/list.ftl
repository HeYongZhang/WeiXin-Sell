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
                                <th>类目id</th>
                                <th>热榜</th>
                                <th>type</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list categoryPage.content as category>
                                <tr>
                                    <td>${category.categoryId}</td>
                                    <td>${category.categoryName}</td>
                                    <td>${category.categoryType}</td>
                                    <td>${category.updateTime}</td>
                                    <td>${category.createTime}</td>
                                    <td>
                                        <a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a>
                                    </td>
                                    <td>
                                        <a href="/sell/seller/category/del?categoryId=${category.categoryId}">删除</a>
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
                         <li><a href="/sell/seller/category/list?page=${currenPage-1}">上一页</a></li>
                    </#if>

                    <#--总页数 OrderDTOpage.getTotalPages() -->
                    <#--当前页数 currenpage-->
                    <#--前置起始点 5-currenpage == 2 -->
                    <#--后置结束点 OrderDTOpage.getTotalPages()-currenpage ==2 -->
                    <#--循环数 5-->

                   <#list 1..categoryPage.getTotalPages() as index>
                       <#if currenPage == index>
                            <li class="active"><a href="#">${index}</a></li>
                       <#else>
                            <li><a href="/sell/seller/category/list?page=${index}">${index}</a></li>
                       </#if>
                   </#list>


                    <#if currenPage gte categoryPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                          <li><a href="/sell/seller/category/list?page=${currenPage+1}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>