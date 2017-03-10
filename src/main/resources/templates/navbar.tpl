<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/home">The Lost Time DKP</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/home">首页</a></li>
                <li><a href="/user">公会人员</a></li>
                <li><a href="/dkp">DKP详情</a></li>
                <li><a href="/chart">DKP排名</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>
<script>
    function sysopen(url, target) {
        var a = document.createElement("a");
        a.setAttribute("href", url);
        a.setAttribute("target", target || "_blank");
        document.body.appendChild(a);
        a.click();
    }
</script>