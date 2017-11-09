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
Hello World!123dasdas
${hello}
<#include "/base/foot.ftl">
</body>
</html>