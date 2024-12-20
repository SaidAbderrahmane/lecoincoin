<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
    <title>
        <g:message code="default.create.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
            <section class="max-w-6xl mx-auto px-6 py-4">
                <a href="#create-category" class="skip text-sm text-gray-600 underline hover:text-gray-800"
                    tabindex="-1">
                    <g:message code="default.link.skip.label" default="Skip to content&hellip;" />
                </a>

                <div class="nav mt-4" role="navigation">
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
                    </ul>
                </div>
            </section>
            <section class="max-w-6xl mx-auto px-6 py-8">
                <div id="create-category" class="col-12 content scaffold-create" role="main">
                    <h1 class="text-3xl font-semibold text-gray-900 mb-6">
                        <g:message code="default.create.label" args="[entityName]" />
                    </h1>

                    <g:if test="${flash.message}">
                        <div class="message bg-green-100 text-green-700 p-4 rounded-md mb-4" role="status">
                            ${flash.message}
                        </div>
                    </g:if>

                    <g:hasErrors bean="${this.category}">
                        <ul class="errors list-disc list-inside text-red-600 mb-4" role="alert">
                            <g:eachError bean="${this.category}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">
                                    data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <g:form resource="${category}" action="save" class="max-w-sm mx-auto">
                        <fieldset class="form mb-6">
                            <div class="mb-4">
                                <label for="name" class="block text-sm font-medium text-gray-700">Category Name</label>
                                <g:textField name="name" id="name"
                                    class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                                    required="true" />
                            </div>

                            <div class="mb-4">
                                <label for="parent" class="block text-sm font-medium text-gray-700">Parent
                                    Category</label>

                                <g:select name="parent" from="${categoryList}" optionKey="id" optionValue="name"
                                    value="${category?.parent?.id}" noSelection="${['null': '---']}"
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                            </div>
                        </fieldset>

                        <fieldset class="buttons flex justify-end">
                            <button type="submit"
                                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                <g:message code="default.button.create.label" default="Create" />
                            </button>
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
</body>

</html>