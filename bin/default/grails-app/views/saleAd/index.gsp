<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}" />
    <title>
        <g:message code="default.list.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}">
                                <g:message code="default.home.label" />
                            </a></li>
                        <li>
                            <g:link class="create" action="create">
                                <g:message code="default.new.label" args="[entityName]" />
                            </g:link>
                        </li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-saleAd" class="col-12 content scaffold-list" role="main">
                    <h1>
                        <g:message code="default.list.label" args="[entityName]" />
                    </h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>



<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <g:each var="property" in="${SaleAdList.get(0).metaClass.properties.findAll { !it.synthetic }.name}">
                    <th scope="col" class="px-6 py-3">
                        ${property.capitalize()}
                    </th>
                </g:each>
                <th scope="col" class="px-6 py-3">Action</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${saleAdList}" var="saleAd">
                <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                    <g:each var="property" in="${SaleAd.metaClass.properties.findAll { !it.synthetic }.name}">
                        <td class="px-6 py-4">
                            ${saleAd[property]}
                        </td>
                    </g:each>
                    <td class="px-6 py-4">
                        <g:link class="font-medium text-blue-600 dark:text-blue-500 hover:underline" action="edit" params="[id: saleAd.id]">Edit</g:link>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>


                    <g:if test="${saleAdCount > params.int('max')}">
                        <div class="pagination">
                            <g:paginate total="${saleAdCount ?: 0}" />
                        </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
</body>

</html>