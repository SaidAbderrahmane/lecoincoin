<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title>
    <g:message code="default.create.label" args="[entityName]"/>
    </title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#create-saleAd" class="skip" tabindex="-1">
                <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
            </a>

            <div class="nav" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}">
                        <g:message code="default.home.label"/>
                    </a></li>
                    <li>
                        <g:link class="list" action="index">
                            <g:message code="default.list.label" args="[entityName]"/>
                        </g:link>
                    </li>
                </ul>
            </div>
        </section>
        <section class="row">
            <div id="create-saleAd" class="col-12 content scaffold-create" role="main">
                <h1>
                    <g:message code="default.create.label" args="[entityName]"/>
                </h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.saleAd}">
                    <ul class="errors" role="list">
                        <g:eachError bean="${this.saleAd}" var="error">
                            <li <g:if test="${error in FieldError}">
                                data-field-id="${error.field}"</g:if>>
                                <g:message error="${error}"/>
                            </li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form enctype="multipart/form-data" action="save" class="max-w-sm mx-auto">
                    <legend class="text-lg font-medium text-gray-900 dark:text-white mb-5">Create a
                    SaleAd</legend>

                    <div class="mb-5">
                        <label for="title"
                               class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Title</label>
                        <g:textField name="title" value="${saleAd?.title}" required="required"
                                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                    </div>


                    <div class="mb-5">
                        <label for="description"
                               class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Description</label>
                        <g:textArea name="description" value="${saleAd?.description}" required="required"
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                        </g:textArea>
                    </div>

                    <div class="mb-5">
                        <label for="price"
                               class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Price</label>
                        <g:field type="number" name="price" value="${saleAd?.price}" required="required"
                                 class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                    </div>


                    <div class="mb-5">
                        <label for="category.id"
                               class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Category</label>
                        <g:select name="category.id" from="${categoryList}" optionKey="id" required="required"
                                  optionValue="${{ it.parent ? '---- ' + it.name : it.name }}"
                                  value="${saleAd?.category?.id}"
                                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                    </div>

                    <div class="rounded-lg border p-4 mb-5">
                        <div class="mb-5">
                            <!-- Address Field -->
                            <label for="address.address"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Address</label>
                            <input type="text" name="address.address" id="address.address" required="required"
                                   value="${address?.address ?: ''}"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <!-- Post Code Field -->
                            <label for="address.postCode"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Post Code</label>
                            <input type="text" name="address.postCode" id="address.postCode" maxlength="10"
                                   required="required"
                                   value="${address?.postCode ?: ''}"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <!-- City Field -->
                            <label for="address.city"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">City</label>
                            <input type="text" name="address.city" id="address.city" required="required"
                                   value="${address?.city ?: ''}"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <!-- Country Field -->
                            <label for="address.country"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Country</label>
                            <g:countrySelect name="address.country" id="address.country"
                                             value="${address?.country ?: ''}"
                                             required="required"
                                             class="bg-gray-50 mb-2 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                             noSelection="['': 'Choose your country']"/>
                        </div>
                    </div>


                    <div class="mb-5">
                        <label for="files"
                               class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Upload
                        Images</label>
                        <input type="file" name="files" id="files" multiple="multiple"
                               class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"/>
                    </div>

                    <button type="submit"
                            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save</button>
                </g:form>
            </div>
        </section>
    </div>
</div>
</body>

</html>