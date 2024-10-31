<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                       default="Skip to content&hellip;"/></a>

            <div class="nav" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                    <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                          args="[entityName]"/></g:link></li>
                </ul>
            </div>
        </section>
        <section class="row">
            <div id="list-user" class="col-12 content scaffold-list" role="main">
                <h1><g:message code="default.list.label" args="[entityName]"/></h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <table>
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th># Annonces</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userList}" var="user">
                        <tr>
                            <td>
                                <g:link controller="user" action="show" id="${user.id}">${user.username}</g:link>
                            </td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.getAuthorities()*.authority.join(',')}</td>
                            <td>${user.saleAds.size()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                <g:if test="${userCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${userCount ?: 0}"/>
                    </div>
                </g:if>
            </div>
        </section>
    </div>
</div>
</body>
</html>