<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="#" />
    <link type="text/css" rel="styleSheet"  href="sell/css/main.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>订单支付页面</title>
</head>
<body>
<input  type="text" value="${returnURL}" id="returnURL">
</body>
<script type="text/javascript" src="/sell/js/jquery-3.3.1.min.js"></script>
<script>
    location.href=$("#returnURL").val();
</script>
</html>
