<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}" />
    <title>
        <g:message code="default.show.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#show-saleAd" class="skip" tabindex="-1">
                    <g:message code="default.link.skip.label" default="Skip to content&hellip;" />
                </a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}">
                                <g:message code="default.home.label" />
                            </a></li>
                        <li>
                            <g:link class="list" action="index">
                                <g:message code="default.list.label" args="[entityName]" />
                            </g:link>
                        </li>
                        <li>
                            <g:link class="create" action="create">
                                <g:message code="default.new.label" args="[entityName]" />
                            </g:link>
                        </li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="show-saleAd" class="col-12 content scaffold-show" role="main">
                    <h1>
                        <g:message code="default.show.label" args="[entityName]" />
                    </h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <%-- <f:display bean="saleAd" /> --%>
                        <div class="sale-ad">
        <h2>${saleAd.title}</h2>
        <p><strong>Description:</strong> ${saleAd.description}</p>
        <p><strong>Price:</strong> $${saleAd.price}</p>
        <p><strong>Address:</strong> ${saleAd.address}</p>
        <p><strong>Category:</strong> ${saleAd.category?.name}</p>
        <p><strong>Author:</strong> ${saleAd.author?.username}</p>

        <h3>Illustrations:</h3>
        <div class="illustrations">
            <g:each in="${saleAd.illustrations}" var="illustration">
                <div class="illustration">
                    <img src="${createLinkTo(dir: 'assets/illustrations', file: illustration.fileName)}" alt="${illustration.fileName}" />
                    <p>${illustration.fileName}</p>
                </div>
            </g:each>
        </div>
                    <g:form resource="${this.saleAd}" method="DELETE">
                        <fieldset class="buttons">
                            <g:link class="edit" action="edit" resource="${this.saleAd}">
                                <g:message code="default.button.edit.label" default="Edit" />
                            </g:link>
                            <input class="delete" type="submit"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
</body>
</html>