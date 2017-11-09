<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#assign path>${(Request.getRequest().contextPath)!""}</#assign>
    <#assign basePath>${(Request.getRequest().scheme)!""}${path}/</#assign>
    <base href="${basePath}">

    <title>登录成功--${siteName}</title>
    <#include "/base/head.ftl">
    <style>
    </style>
</head>

<body>
<#include "/base/header.ftl">
<div class="layui-container">

    Hello World!12111
${hello}
</div>
<#include "/base/foot.ftl">
<script>
    layui.use('element', function(){
        var element = layui.element;

    });
</script>
</body>
</html>