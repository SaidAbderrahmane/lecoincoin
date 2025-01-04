<%@ page import="fr.bihar.lecoincoin.User" %>
<%@ page import="fr.bihar.lecoincoin.Message" %>
<%@ page import="grails.plugin.springsecurity.SpringSecurityService" %>
<g:set var="springSecurityService" bean="springSecurityService" />

<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
    <title>
        <g:message code="default.list.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
                <div
                    class="flex items-center justify-between flex-column flex-wrap md:flex-row space-y-4 md:space-y-0 pb-4 bg-white dark:bg-gray-900">
                    <label for="table-search" class="sr-only">Search</label>
                    <div class="relative">
                        <g:form method="GET" action="index" controller="message">
                            <div class="flex flex-row">
                                <div
                                    class="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
                                    <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                                    </svg>
                                </div>
                                <input type="text" id="table-search-users" name="searchString" value="${searchString}"
                                    class="block p-2 mr-5 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
                                    placeholder="Search for users">
                                <button type="submit"
                                    class="text-white bg-blue-700 hover:bg-blue-800 rounded-lg text-sm px-5 py-2.5 text-center">Search</button>
                            </div>
                            </g:form>
                    </div>
                </div>

            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                Name
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Role
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Status
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Action
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${userList}" var="user">
                            <tr
                                class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                                <th scope="row"
                                    class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    <img class="w-10 h-10 rounded-full"
                                        src="https://flowbite.com/docs/images/people/profile-picture-5.jpg"
                                        alt="user image">
                                    <div class="ps-3">
                                        <div class="text-base font-semibold">
                                            ${user.username}
                                        </div>
                                        <div class="font-normal text-gray-500">
                                            ${user.email}
                                        </div>
                                    </div>
                                </th>
                                <td class="px-6 py-4">
                                    ${user.getAuthorities()*.authority.join(',')}
                                </td>
                                <td class="px-6 py-4">
                                    <span
                                        class="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-blue-800 bg-blue-100 rounded-full dark:bg-blue-900 dark:text-blue-300">
                                        ${Message.countByAuthorAndDestAndIsRead(user,springSecurityService.currentUser,
                                        false)}
                                    </span>
                                </td>
                                <td class="px-6 py-4">
                                    <g:link controller="message" action="conversation" id="${user.id}"
                                        class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Enter
                                        Chat</g:link>
                                </td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>

            <%-- <section class="row">

                    <div id="list-message" class="col-12 content scaffold-list" role="main">
                        <h1>
                            <g:message code="default.list.label" args="[entityName]" />
                        </h1>
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <f:table collection="${messageList}" />

                        <g:if test="${messageCount > params.int('max')}">
                            <div class="pagination">
                                <g:paginate total="${messageCount ?: 0}" />
                            </div>
                        </g:if>
                    </div>
                    </section> --%>
        </div>
    </div>
</body>

</html>
