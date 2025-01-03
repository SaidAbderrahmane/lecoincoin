<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title>
    <g:message code="default.edit.label" args="[entityName]"/>
    </title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#edit-category" class="skip" tabindex="-1">
                <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
            </a>

            <div class="nav" role="navigation">
                <ul class="flex items-center w-full gap-4">
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
                    <li class="ml-auto flex space-x-4">
%{--                        <g:if test="${!this.category.isRoot()}">--}%
                            <button data-modal-target="popup-modal" data-modal-toggle="popup-modal"
                                    class="font-medium text-red-600 dark:text-red-500 hover:underline pl-2">
                                Delete
                            </button>
%{--                        </g:if>--}%
                        <g:link class="create bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700"
                                action="create">
                            <g:message code="default.new.label" args="[entityName]"/>
                        </g:link>
                    </li>
                </ul>
            </div>
        </section>

        <section class="max-w-6xl mx-auto px-6 py-8">
            <div id="edit-category" class="col-12 content scaffold-edit" role="main">
                <h1 class="text-3xl font-semibold text-gray-900 mb-6">
                    <g:message code="default.edit.label" args="[entityName]"/>
                </h1>

                <g:if test="${flash.message}">
                    <div class="message bg-green-100 text-green-700 p-4 rounded-md mb-4" role="status">
                        ${flash.message}
                    </div>
                </g:if>

                <g:hasErrors bean="${this.category}">
                    <ul class="errors list-disc list-inside text-red-600 mb-4" role="list">
                        <g:eachError bean="${this.category}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">
                                data-field-id="${error.field}"</g:if>>
                                <g:message error="${error}"/>
                            </li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>

                <g:form resource="${this.category}" method="PUT" class="max-w-sm mx-auto">
                    <g:hiddenField name="version" value="${this.category?.version}"/>

                    <fieldset class="form mb-6">
                        <div class="mb-4">
                            <label for="name" class="mb-2 block text-sm font-medium text-gray-700">Category Name</label>
                            <g:textField name="name" id="name"
                                         class="block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                                         required="true" value="${this.category?.name}"/>
                        </div>

                        <div class="mb-4">
                            <label for="parent" class="block mb-2 text-sm font-medium text-gray-700">Parent
                            Category</label>
                            <g:select name="parent" from="${categoryList}" optionKey="id" optionValue="name"
                                      value="${this.category?.parent?.id}" noSelection="${['null': '---']}"
                                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>
                    </fieldset>

                    <fieldset class="flex justify-end">
                        <button type="submit"
                                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            <g:message code="default.button.update.label" default="Update"/>
                        </button>
                    </fieldset>
                </g:form>
            </div>
        </section>
    </div>
</div>

<div id="popup-modal" tabindex="-1"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
        <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <button type="button"
                    class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                    data-modal-hide="popup-modal">
                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                     viewBox="0 0 14 14">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                </svg>
                <span class="sr-only">Close modal</span>
            </button>

            <div class="p-4 md:p-5 text-center">
                <svg class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true"
                     xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                </svg>

                <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Are you sure you want to
                delete this user?</h3>
                <g:form action="delete" method="DELETE">
                    <input type="hidden" name="id" value="${params.id}"/>
                    <button data-modal-hide="popup-modal" type="submit"
                            class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                        Yes, I'm sure
                    </button>
                    <button data-modal-hide="popup-modal" type="button"
                            class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">No,
                    cancel</button>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>

</html>