<nav class="navbar navbar-toggleable-md navbar-light bg-faded fixed-top" id="header">
    <div class="container">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="/">
            <img src="images/logo3.png" height="50" alt="">
        </a>

        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">

            </ul>
            <div class="mr-auto"></div>
            <ul class="navbar-nav">
                <#if user?exists>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle user-info" href="javascript:;" id="headicon-dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="${(user.avatar)!"images/default_headicon.png"}" class="rounded-circle" width="28" height="28" alt="${(user.nickname)!""}">
                        ${user.nickname!""}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="headicon-dropdown">
                            <a class="dropdown-item" href="profile/basicInfo"><i class="fa fa-user"></i>&nbsp;&nbsp;个人资料</a>
                            <a class="dropdown-item" href="profile/avatar"><i
                                    class="fa fa-image"></i>&nbsp;&nbsp;修改头像</a>
                            <a class="dropdown-item" href="profile/password"><i
                                    class="fa fa-pencil"></i>&nbsp;&nbsp;修改密码</a>
                            <a class="dropdown-item" href="javascript:logout();"><i class="fa fa-log-out"></i>&nbsp;&nbsp;退出</a>
                        </div>
                    </li>
                    <#if user?exists&&user.userType==1>
                        <li class="nav-item active">
                            <a class="nav-link" href="/admin/siteSet/siteInfo">管理中心</a>
                        </li>
                    </#if>

                </#if>
            </ul>
        </div>
    </div>
</nav>
