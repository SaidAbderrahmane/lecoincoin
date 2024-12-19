<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
    <title>
        <g:message code="default.show.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
            <section class="max-w-7xl mx-auto px-6 py-4">
                <!-- Skip link for accessibility -->
                <a href="#show-category" class="skip text-sm text-gray-600 underline hover:text-gray-800" tabindex="-1">
                    <g:message code="default.link.skip.label" default="Skip to content&hellip;" />
                </a>

                <!-- Navigation Section -->
                <div class="nav mt-6" role="navigation">
                    <ul class="flex space-x-6 text-lg text-gray-800">
                        <li>
                            <a class="home text-blue-600 hover:text-blue-800 font-medium"
                                href="${createLink(uri: '/')}">
                                <g:message code="default.home.label" />
                            </a>
                        </li>
                        <li>
                            <g:link class="list text-blue-600 hover:text-blue-800 font-medium" action="index">
                                <g:message code="default.list.label" args="[entityName]" />
                            </g:link>
                        </li>
                        <li>
                            <g:link class="create text-blue-600 hover:text-blue-800 font-medium" action="create">
                                <g:message code="default.new.label" args="[entityName]" />
                            </g:link>
                        </li>
                    </ul>
                </div>
            </section>

            <section class="max-w-7xl mx-auto px-6 py-8">
                <!-- Category Details -->
                <div id="show-category" class="col-12 content scaffold-show" role="main">
                    <h1 class="text-3xl font-semibold text-gray-900 mb-6">
                        <g:message code="default.show.label" args="[entityName]" />
                    </h1>

                    <!-- Flash Message -->
                    <g:if test="${flash.message}">
                        <div class="message bg-green-100 text-green-700 p-4 rounded-md mb-6" role="status">
                            ${flash.message}
                        </div>
                    </g:if>

                    <!-- Category Information -->
                    <div class="mb-6 bg-gray-50 p-4 rounded-md shadow-md">
                        <h2 class="text-xl font-semibold text-gray-800 mb-4">Category Details</h2>

                        <!-- Category Name -->
                        <div class="mb-4">
                            <span class="font-medium text-gray-700">Name:</span>
                            <p class="text-gray-800">${category.name}</p>
                        </div>

                        <!-- Parent Category -->
                        <g:if test="${category.parent}">
                            <div class="mb-4">
                                <span class="font-medium text-gray-700">Parent Category:</span>
                                <p class="text-gray-800">
                                    <g:link class="text-blue-600 hover:text-blue-800" action="show"
                                        id="${category.parent.id}">
                                        ${category.parent.name}
                                    </g:link>
                                </p>
                            </div>
                        </g:if>

                        <!-- Sale Ads Count -->
                        <div class="mb-4">
                            <span class="font-medium text-gray-700">Number of Sale Ads:</span>
                            <p class="text-gray-800">${category.saleAds?.size()}</p>
                        </div>
                    </div>

                    <!-- Edit/Delete Buttons -->
                    <div class="flex justify-end space-x-4">
                        <g:link
                            class="edit bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300"
                            action="edit" resource="${category}">
                            <g:message code="default.button.edit.label" default="Edit" />
                        </g:link>

                        <g:form resource="${category}" method="DELETE">
                            <input
                                class="delete bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-red-300"
                                type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </g:form>
                    </div>
                </div>
            </section>
        </div>
    </div>
</body>

</html>