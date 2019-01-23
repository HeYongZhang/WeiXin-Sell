<html>
<head>
    <meta charset="UTF-8">
    <title>错误消息提示</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="alert alert-dismissable alert-warning">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4>
                            错误!
                        </h4> <strong>${(msg)!''}</strong><a href="${url}'" class="alert-link" id="sp">3秒后自动跳转</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
<script type="text/javascript">
    onload=function(){
        setInterval(go, 1000);
    };
    var x=3; //利用了全局变量来执行
    function go(){
        x--;
        if(x>0){
            document.getElementById("sp").innerHTML=x+"秒后自动跳转"; //每次设置的x的值都不一样了。
        }else{
            location.href=document.getElementsByTagName("a")[0].href;
        }
    }
</script>
</html>

