<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}" />
    <title>
        <g:message code="default.create.label" args="[entityName]" />
    </title>
</head>

<body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#create-saleAd" class="skip" tabindex="-1">
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
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="create-saleAd" class="col-12 content scaffold-create" role="main">
                    <h1>
                        <g:message code="default.create.label" args="[entityName]" />
                    </h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.saleAd}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.saleAd}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">
                                    data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
<%-- 
                    <g:form controller="saleAd" action="save" method="post" class="bg-white p-6 rounded shadow">
                        <div class="mb-4">
                            <label for="title" class="block text-gray-700">Title</label>
                            <g:field name="title" class="mt-1 block w-full p-2 border border-gray-300 rounded"
                                required="true" />
                        </div>

                        <div class="mb-4">
                            <label for="description" class="block text-gray-700">Description</label>
                            <g:textArea name="description" value="${saleAd?.description}"
                                class="mt-1 block w-full p-2 border border-gray-300 rounded" required="true" />
                        </div>

                        <div class="mb-4">
                            <label for="price" class="block text-gray-700">Price</label>
                            <g:field name="price" value="${saleAd?.price}"
                                class="mt-1 block w-full p-2 border border-gray-300 rounded" required="true" />
                        </div>

                        <div class="mb-4">
                            <label for="active" class="inline-flex items-center">
                                <g:checkBox name="active" value="${saleAd?.active}" class="mr-2" /> Active
                            </label>
                        </div>

                        <div class="mb-4">
                            <label for="address" class="block text-gray-700">Address</label>
                            <g:select name="address.postCode" from="${addressList}" optionKey="id" optionValue="address"
                                value="${saleAd?.address?.id}"
                                class="mt-1 block w-full p-2 border border-gray-300 rounded" />
                        </div>

                        <div class="mb-4">
                            <label for="category" class="block text-gray-700">Category</label>
                            <g:select name="category.id" from="${categoryList}" optionKey="id" optionValue="name"
                                value="${saleAd?.category?.id}"
                                class="mt-1 block w-full p-2 border border-gray-300 rounded" />
                        </div>

                        <div>
                            <g:submitButton name="create" class="bg-blue-500 text-white font-bold py-2 px-4 rounded"
                                value="Create Sale Ad" />
                        </div>
                    </g:form> --%>



                    <g:form resource="${this.saleAd}" method="POST">
                        <fieldset class="form">
                            <f:all bean="saleAd" />
                        </fieldset>
                        <fieldset class="buttons">
                            <g:submitButton name="create" class="save"
                                value="${message(code: 'default.button.create.label', default: 'Create')}" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
</body>

</html>