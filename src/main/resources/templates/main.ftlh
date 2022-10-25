<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}"
                       placeholder="Factorize new number">
                <button type="submit" class="btn btn-primary ml-2">Factorize</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Search number
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="number" placeholder="Enter number"/>
                </div>
                <div class="form-group">
                    <input type="hidden" class="form-control" name="_csrf" value="${_csrf.token}"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Factorize</button>
                </div>
            </form>
        </div>
    </div>


    <#if message??>
        <strong>${message}</strong>
    </#if>

    <div class="card-columns">
        <#if numbers??>
            <#list numbers as number>
                <div class="card my-3">
                    <div class="m-2">
                        <span>${number.bigInteger}</span>
                    </div>
                    <div class="card-footer text-muted">
                        <#list number.authorName as us >
                            <i>${number.factNumber}</i>
<#--                            <strong>${us}</strong>-->
                        </#list>
                    </div>
                </div>
            <#else> No numbers
            </#list>
        </#if>
    </div>
    <div>List user factorizations</div>
    <#if users??>
        <#list users as user>
            <strong>${user}</strong>
        </#list>
    </#if>

</@c.page>