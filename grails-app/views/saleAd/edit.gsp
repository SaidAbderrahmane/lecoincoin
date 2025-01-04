<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title>
    <g:message code="default.edit.label" args="[entityName]"/>
    </title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="p-4">
            <div class="nav flex w-full justify-between" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}">
                        <g:message code="default.home.label"/>
                    </a></li>
                </ul>
                <button data-modal-target="popup-modal" data-modal-toggle="popup-modal"
                        class="font-medium text-red-600 dark:text-red-500 hover:underline pl-2">
                    Delete
                </button>

            </div>
        </section>
        <section class="row">
            <div id="edit-saleAd" class="col-12 content scaffold-edit" role="main">
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
                <div class="max-w-sm mx-auto">

                    <g:form resource="${this.saleAd}" method="PUT" enctype="multipart/form-data">
                        <g:hiddenField name="version" value="${this.saleAd?.version}"/>
                        <legend class="text-lg font-medium text-gray-900 dark:text-white mb-5">Edit SaleAd</legend>

                        <div class="mb-5">
                            <label for="title"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Title</label>
                            <input name="title" id="title" type="text" value="${saleAd.title}" required="required"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <label for="description"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Description</label>
                            <g:textArea name="description" value="${saleAd.description}" required="required"
                                        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                            </g:textArea>
                        </div>

                        <div class="mb-5">
                            <label for="price"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Price</label>
                            <input name="price" id="price" type="number" min="1" step="1" value="${saleAd.price}"
                                   required="required"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <label for="active"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Active</label>
                            <input type="checkbox" name="active" id="active"
                                   class="rounded" ${saleAd.active ? 'checked' : ''}/>
                        </div>

                        <div class="mb-5">
                            <label for="category.id"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Category</label>
                            <g:select from="${categoryList}" id="category.id" name="category.id"
                                      value="${saleAd.category.id}"
                                      optionKey="id" optionValue="name" required="required"
                                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="mb-5">
                            <label for="author.id"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Author</label>
                            <g:select from="${userList}" id="author.id" name="author.id" value="${saleAd.author.id}"
                                      optionKey="id"
                                      optionValue="username" required="required"
                                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>

                        <div class="rounded-lg border p-4 mb-5">
                            <div class="mb-5">
                                <!-- Address Field -->
                                <label for="address.address"
                                       class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Address</label>
                                <input type="text" name="address.address" id="address.address" required="required"
                                       value="${saleAd.address.address}"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            </div>

                            <div class="mb-5">
                                <!-- Post Code Field -->
                                <label for="address.postCode"
                                       class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Post Code</label>
                                <input type="text" name="address.postCode" id="address.postCode" maxlength="10"
                                       required="required"
                                       value="${saleAd.address.postCode}"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            </div>

                            <div class="mb-5">
                                <!-- City Field -->
                                <label for="address.city"
                                       class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">City</label>
                                <input type="text" name="address.city" id="address.city" required="required"
                                       value="${saleAd.address.city}"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            </div>

                            <div class="mb-5">
                                <!-- Country Field -->
                                <label for="address.country"
                                       class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Country</label>
                                <g:countrySelect name="address.country" id="address.country"
                                                 value="${saleAd.address.country}"
                                                 required="required"
                                                 class="bg-gray-50 mb-2 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                                 noSelection="['': 'Choose your country']"/>
                            </div>

                        </div>

                        <div class="mb-5">
                            <label for="files"
                                   class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Illustrations</label>
                            <input type="file" name="files" id="files" multiple="multiple"
                                   class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"/>

                            <g:each in="${saleAd.illustrations}" var="illustration">
                                <div class="illustration bg-gray-100 rounded-lg overflow-hidden shadow-sm mt-2">
                                    <img class="w-full h-48 object-cover"
                                         src="${createLinkTo(dir: 'assets/illustrations', file: illustration.fileName)}"
                                         alt="${illustration.fileName}"/>
                                </div>

                                <button data-illustration-id="${illustration.id}"
                                        data-modal-target="popup-modal-illustration"
                                        data-modal-toggle="popup-modal-illustration"
                                        class="font-medium text-red-600 dark:text-red-500 hover:underline">
                                    Delete
                                </button>
                            </g:each>

                        </div>

                        <div class="flex justify-between">
                            <button type="submit"
                                    class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                Update
                            </button>
                        </div>
                    </g:form>

                </div>
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
                delete this saleAd?</h3>
                <g:form action="delete" method="DELETE">
                    <input type="hidden" name="id" value="${saleAd.id}"/>
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

<div id="popup-modal-illustration" tabindex="-1"
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

                <div class="flex space-x-4 items-center justify-center">
                    <g:form action="delete" controller="illustration">
                        <g:hiddenField id="illustrationId" name="id" value=""/>
                        <g:hiddenField name="saleAdId" value="${saleAd.id}"/>
                        <button data-modal-hide="popup-modal-illustration" type="submit"
                                class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                            Yes, I'm sure
                        </button>
                    </g:form>
                    <button data-modal-hide="popup-modal-illustration" type="button"
                            class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">No,
                    cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const deleteButtons = document.querySelectorAll('button[data-illustration-id]');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function (e) {
                e.preventDefault()
                const illustrationId = this.getAttribute('data-illustration-id');
                console.log(illustrationId);

                const illustrationIdInput = document.querySelector('#illustrationId');
                illustrationIdInput.value = illustrationId;
            });
        });
    });
</script>
</body>

</html>