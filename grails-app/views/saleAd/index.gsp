<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title>
    <g:message code="default.list.label" args="[entityName]"/>
    </title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <div class="flex w-full justify-between items-center" role="navigation">

                <h1 class="font-bold text-lg">
                    <g:message code="default.list.label" args="[entityName]"/>
                </h1>

                <g:link class="create" action="create"
                        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                    <g:message code="default.new.label" args="[entityName]"/>
                </g:link>
            </div>
        </section>
        <section class="mt-4">
            <div id="list-saleAd" class="col-12 content scaffold-list" role="main">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>

            <!-- table -->
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                        <thead
                                class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">Title</th>
                            <th scope="col" class="px-6 py-3">Price</th>
                            <th scope="col" class="px-6 py-3">Category</th>
                            <th scope="col" class="px-6 py-3">Date Created</th>
                            <th scope="col" class="px-6 py-3">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${saleAdList}" var="saleAd">
                            <tr
                                    class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                                <th scope="row"
                                    class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    ${saleAd.title}
                                </th>
                                <td class="px-6 py-4">
                                    $${saleAd.price}
                                </td>
                                <td class="px-6 py-4">
                                    ${saleAd.category?.name}
                                </td>
                                <td class="px-6 py-4">
                                    ${saleAd.dateCreated}
                                </td>
                                <td class="px-6 py-4 flex space-x-2">
                                    <g:link controller="saleAd" action="show" id="${saleAd.id}"
                                            class="font-medium text-blue-600 dark:text-blue-500 hover:underline">
                                        View
                                    </g:link>
                                    <g:link controller="saleAd" action="edit" id="${saleAd.id}"
                                            class="font-medium text-blue-600 dark:text-blue-500 hover:underline">
                                        Edit
                                    </g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>

            <!-- pagination -->
                <g:if test="${saleAdCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${saleAdCount ?: 0}"/>
                    </div>
                </g:if>
            </div>
        </section>
    </div>
</div>
</body>

</html>