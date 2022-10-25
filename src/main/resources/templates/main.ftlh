<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
        <span><a href="/user">User list</a></span>
    </div>
    <div>
        <form method="post">
            <input type="text" name="number" placeholder="Enter number"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Factorize</button>
        </form>
    </div>
    <div>List numbers</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!}">
        <button type="submit">Find</button>
    </form>

    <#if message??>
        <strong>${message}</strong>
    </#if>

    <#if numbers??>
        <#list numbers as number>
            <div>
                <b>${number.id}</b>
                <span>${number.bigInteger}</span>
                <i>${number.factNumber}</i>
                <#list number.authorName as us >
                    <strong>${us}</strong>
                </#list>
            </div>
        <#else> No numbers
        </#list>
    </#if>
    <div>List user factorizations</div>
    <#if users??>
        <#list users as user>
            <strong>${user}</strong>
        </#list>
    </#if>

</@c.page>