<div class="layui-header">
    <div class="layui-container">
        <div class="layui-logo">
            <img class="layui-img" src="images/logo3.png" height="50"/>
        </div>
    <#if user?exists>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="${(user.avatar)!"images/default_headicon.png"}" class="layui-nav-img"
                         alt="${user.nickname}">
                ${user.nickname}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="profile/basicInfo"><i class="fa fa-user"></i>&nbsp;&nbsp;个人资料</a></dd>
                    <dd><a href="profile/avatar"><i class="fa fa-image"></i>&nbsp;&nbsp;修改头像</a></dd>
                    <dd><a href="profile/password"><i class="fa fa-pencil"></i>&nbsp;&nbsp;修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="javascript:logout();"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;退出</a></li>
        </ul>
    </#if>
    </div>
</div>
