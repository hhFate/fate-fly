<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#assign path>${request.contextPath}</#assign>
    <#assign baseePath>${request.scheme}://${request.serverName}:${request.serverPort}${path}/</#assign>
    <base href="${baseePath}">

    <title>登录成功</title>
    <#include "/base/head.ftl">
    <style>
    </style>
</head>

<body>

<#include "/base/header.ftl">
Hello World!1111
${hello}
<#include "/base/foot.ftl">
</body>
</html>