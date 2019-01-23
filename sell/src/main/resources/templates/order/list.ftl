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
                            <div class="form-group">
                                <label for="dtp_input1" class="col-md-1 control-label">选择时间</label>
                                <div class="input-group date form_datetime col-md-4" >
                                    <input id="createTime" class="form-control" size="16" name = "createTime" type="text" value="${(createTime)!''}" readonly>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                </div>
                            </div>
                        <table class="table table-hover table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>总金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                        <#list OrderDTOpage.content as orderDTO>
                        <tr>
                            <td> ${orderDTO.orderId}</td>
                            <td> ${orderDTO.buyerName}</td>
                            <td> ${orderDTO.buyerPhone}</td>
                            <td> ${orderDTO.buyerAddress}</td>
                            <td> ${orderDTO.orderAmount}</td>
                            <td> ${orderDTO.getOrderStatusEnum().getMessage()}</td>
                            <td> ${orderDTO.getPayStatusEnum().getMessage()}</td>
                            <td> ${orderDTO.createTime}</td>
                            <td class="text-info">
                                <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                            </td>
                            <td class="text-danger">
                                <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单">
                                    <a href="/sell/seller/order/cencer?orderId=${orderDTO.orderId}">取消订单</a>
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
                         <li><a href="javascript:;" class="orderpage" currenPage="${currenPage-1}">上一页</a></li>
                    </#if>

                        <#--总页数 OrderDTOpage.getTotalPages() -->
                        <#--当前页数 currenpage-->
                        <#--前置起始点 5-currenpage == 2 -->
                        <#--后置结束点 OrderDTOpage.getTotalPages()-currenpage ==2 -->
                        <#--循环数 5-->
                            <#if OrderDTOpage.getTotalPages() = 0>
                                <li class="active"><a href="javascript:;">没有当天任何记录</a></li>
                            <#else >
                                <#list 1..OrderDTOpage.getTotalPages() as index>
                                    <#if currenPage == index>
                                    <li class="active"><a href="javascript:;">${index}</a></li>
                                    <#else>
                                <li><a  href="javascript:;" class="orderpage" currenPage="${index}">${index}</a></li>
                                    </#if>
                                </#list>
                            </#if>


                    <#if currenPage gte OrderDTOpage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                          <li><a href="javascript:;" class="orderpage" currenPage="${currenPage+1}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="mymodal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提示
                    </h4>
                </div>
                <div class="modal-body">
                    你有新的订单
                </div>
                <div class="modal-footer">
                    <button  onclick="location.reload()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button  onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>

    <audio id="notice" loop="loop">
       <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
    </audio>

    <script type="text/javascript" src="/sell/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/sell/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/sell/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="/sell/js/locales/bootstrap-datetimepicker.fr.js"></script>
    <script>
        $(document).ready(function () {
            $('.form_datetime').datetimepicker({
                weekStart: 0, //一周从哪一天开始
                todayBtn:  1,
                minView:2,
                language: 'zh-CN',
                todayHighlight: 1,
                initialDate: new Date(),
                startView: 4,
                setDate: new Date(),
                language: 'zh-CN',
                bootcssVer:3,
                autoclose: 0,
                format:'yyyy-mm-dd'
            }).change(function () {
                location.href="/sell/seller/order/list?createTime="+$("#createTime").val();
            });

            $(".orderpage").click(function () {
                var index = $(this).attr("currenPage");
                $(this).attr("href","/sell/seller/order/list?page="+index+"&createTime="+$("#createTime").val());
            });

            var websocket = null;
            if('WebSocket' in window) {
                websocket = new WebSocket('ws://127.0.0.1:81/sell/webSocket');
            }else{
                alert("该游览器不支持websocket");
            }

            websocket.onopen = function (ev) {
                console.log('建立连接');
            }

            websocket.onclose = function (ev) {
                console.log('连接关闭');
            }

            websocket.onmessage = function (ev) {
                console.log('收到消息:'+ev.data);
                //弹窗提醒，播放音乐
                $("#mymodal").modal('show');
                document.getElementById('notice').play();
            }

            websocket.onerror = function (ev) {
                alert('websocket通信发送错误');
            }

            window.onbeforeunload = function () {
                websocket.close();
            }
        });
    </script>
    </body>
</html>

<#--
<h1>${OrderDTOpage.totalPages }</h1>

<#list OrderDTOpage.content as orderDTO>
    ${orderDTO.orderId}</<br>
</#list>-->
