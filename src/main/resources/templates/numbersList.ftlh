<#import "parts/common.ftlh" as c>

<@c.page>
    Factorized number

    <#if usersList??>
        <#list usersList as number, fact>
            <div class="card my-3">
                <div class="m-2">
                    <span>${number}</span>
                </div>
                <div class="card-footer text-muted">
                    <i>${fact}</i>
                </div>
            </div>
        <#else> No numbers
        </#list>
    </#if>
</@c.page>