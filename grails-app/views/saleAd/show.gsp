<%@ page import="grails.plugin.springsecurity.SpringSecurityService" %>
<g:set var="springSecurityService" bean="springSecurityService" />

<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title>
    <g:message code="default.show.label" args="[entityName]"/>
    </title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="max-w-6xl mx-auto px-6 py-4">
            <a href="#show-saleAd" class="skip text-sm text-gray-600 underline hover:text-gray-800" tabindex="-1">
                <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
            </a>

            <nav class="mt-4" role="navigation">
                <ul class="flex w-full space-x-6 text-lg text-gray-800">
                    <li>
                        <a class="home text-blue-600 hover:text-blue-800 font-medium"
                           href="${createLink(uri: '/')}">
                            <g:message code="default.home.label"/>
                        </a>
                    </li>
                    <li>
                        <g:link class="list text-blue-600 hover:text-blue-800 font-medium" action="index">
                            <g:message code="default.list.label" args="[entityName]"/>
                        </g:link>
                    </li>
                    <li class="ml-auto">
                        <g:link class="create text-blue-600 hover:text-blue-800 font-medium" action="create">
                            <g:message code="default.new.label" args="[entityName]"/>
                        </g:link>
                    </li>
                </ul>
            </nav>
        </section>

        <section class="max-w-4xl mx-auto p-6">
            <div id="show-saleAd" class="content scaffold-show" role="main">
                <h1 class="text-4xl font-bold mb-4 text-center">
                    <g:message code="default.show.label" args="[entityName]"/>
                </h1>
                <g:if test="${flash.message}">
                    <div class="bg-green-100 text-green-800 p-4 rounded-lg mb-6" role="status">
                        ${flash.message}
                    </div>
                </g:if>

                <div class="sale-ad bg-white shadow-md rounded-lg p-6 space-y-6">
                    <h2 class="text-2xl font-semibold text-gray-800">${saleAd.title}</h2>

                    <div class="text-lg text-gray-700">
                        <p><strong>Description:</strong> ${saleAd.description}</p>

                        <p><strong>Price:</strong> $${saleAd.price}</p>

                        <p><strong>Address:</strong> ${saleAd.address}</p>

                        <p><strong>Category:</strong> ${saleAd.category?.name}</p>

                        <p><strong>Author:</strong> ${saleAd.author?.username}</p>
                    </div>

                    <h3 class="text-xl font-semibold text-gray-800">Illustrations:</h3>

                    <div class="illustrations grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-4">
                        <g:if test="${saleAd.illustrations.isEmpty()}">
                            <div class="illustration bg-gray-100 rounded-lg overflow-hidden shadow-sm">
                                <img class="w-full h-48 object-cover"
                                     src="${createLinkTo(dir: 'assets/illustrations', file: grailsApplication.config.illustrations.defaultImage)}"
                                     alt="Default Illustration"/>
                            </div>
                        </g:if>
                        <g:else>
                            <g:each in="${saleAd.illustrations}" var="illustration">
                                <div class="illustration bg-gray-100 rounded-lg overflow-hidden shadow-sm">
                                    <img class="w-full h-48 object-cover"
                                         src="${createLinkTo(dir: 'assets/illustrations', file: illustration.fileName)}"
                                         alt="${illustration.fileName}"/>
                                </div>
                            </g:each>
                        </g:else>

                    </div>
                    <g:if test="${springSecurityService.currentUser.getAuthorities()*.authority.any { it in ['ROLE_ADMIN', 'ROLE_MODO'] }
                            || (saleAd.author?.id == springSecurityService.currentUser?.id)}">

                        <div class="flex justify-between items-center mt-6">
                            <g:link class="text-blue-600 hover:text-blue-800 font-medium" action="edit"
                                    resource="${this.saleAd}">
                                <g:message code="default.button.edit.label" default="Edit"/>
                            </g:link>
                            <g:form resource="${this.saleAd}" method="DELETE">
                                <input
                                        class="bg-red-600 text-white py-2 px-4 rounded-lg cursor-pointer hover:bg-red-700"
                                        type="submit"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                            </g:form>
                        </div>
                    </g:if>
                </div>
            </div>
        </section>
    </div>
</div>
</body>

</html>