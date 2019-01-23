<html>
    <#include "../common/head.ftl">
    <body>
        <div id="wrapper" class="toggled">

        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
            <div id="page-content-wrapper">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-4 column">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>订单总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    <#--订单详情数据-->
                        <div class="col-md-12 column">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>商品id</th>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>总额</th>
                                </tr>
                                </thead>
                                <tbody>
                            <#list orderDTO.orderDetailList as detail>
                            <tr>
                                <td>${detail.productId}</td>
                                <td>${detail.productName}</td>
                                <td>${detail.productPrice}</td>
                                <td>${detail.productQuantity}</td>
                                <td>${detail.productPrice * detail.productQuantity}</td>
                            </tr>
                            </#list>
                                </tbody>
                            </table>
                        </div>

                    <#--订单按钮-->
            <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单">
                <div class="col-md-12 column">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cencer?orderId=${orderDTO.orderId}" class="btn btn-default btn-danger">取消订单</a>
                </div>
            </#if>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>

