<%--
  Created by IntelliJ IDEA.
  User: luzihao
  Date: 2020/12/6
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
    <title>管理员后台</title>
    <link rel="stylesheet" href="${ctx}/resource/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/resource/css/admin.css">
    <script src="${ctx}/resource/js/jquery.js"></script>
    <script src="${ctx}/resource/js/pintuer.js"></script>
</head>
<body style="background-color: #f2f9fd">
<div class="header bg-main">
    <div class="logo margin-big-left fadein-top">
        <h1>管理员后台</h1>
    </div>
    <div class="head-l">
        <a class="button button-little bg-green" href="${ctx}/login/uIndex" target="_blank">
            <span class="icon-home"></span>前台首页
        </a>
        <a class="button button-little bg-red" href="mtuichu.html">
            <span class="icon-power-off"></span>退出登录
        </a>
    </div>
</div>
<div class="leftnav">
    <div class="leftnav-title"><strong><span class="icon-list">菜单列表</span> </strong></div>
    <h2><span class="icon-user"></span>基本设置 </h2>
    <ul style="display: block">
        <li><a href="${ctx}/itemCategory/findBySql" target="right"><span class="icon-caret-right"></span>类目管理</a> </li>
        <li><a href="${ctx}/user/findBySql" target="right"><span class="icon-caret-right"></span>用户管理</a> </li>
        <li><a href="${ctx}/item/findBySql" target="right"><span class="icon-caret-right"></span>商品管理</a> </li>
        <li><a href="${ctx}/itemOrder/findBySql" target="right"><span class="icon-caret-right"></span>订单管理</a> </li>
        <li><a href="${ctx}/news/findBySql" target="right"><span class="icon-caret-right"></span>公告管理</a> </li>
        <li><a href="${ctx}/message/findBySql" target="right"><span class="icon-caret-right"></span>留言管理</a> </li>
    </ul>
</div>
<ul class="bread">
    <li><a href="{:}" target="right" class="icon-home">首页</a> </li>
    <li><a href="#">网站信息</a> </li>
    <li><b>当前语言：</b><span style="color:red">中文</span>
        &nbsp;&nbsp;&nbsp;&nbsp;切换语言：<a href="#">中文</a>&nbsp;&nbsp;<a href="#">英文</a>
    </li>
</ul>
<div class="admin">
    <iframe scrolling="auto" rameborder="0"  name="right" width="100%" height="100%"></iframe>
</div>
</body>
</html>
