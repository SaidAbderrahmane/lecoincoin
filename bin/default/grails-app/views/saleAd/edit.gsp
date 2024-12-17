<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'saleAd.label', default: 'SaleAd')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#edit-saleAd" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                         default="Skip to content&hellip;"/></a>

            <div class="nav" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                    <li><g:link class="list" action="index"><g:message code="default.list.label"
                                                                       args="[entityName]"/></g:link></li>
                    <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                          args="[entityName]"/></g:link></li>
                </ul>
            </div>
        </section>
        <section class="row">
            <div id="edit-saleAd" class="col-12 content scaffold-edit" role="main">
                <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.saleAd}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.saleAd}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form resource="${this.saleAd}" method="PUT">
                    <g:hiddenField name="version" value="${this.saleAd?.version}"/>
                    <fieldset class="form">
                        <div class="fieldcontain required">
                            <label>Title</label>
                            <input name="title" type="text" value="${saleAd.title}"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Description</label>
                            <g:textArea name="description" type="text" value="${saleAd.description}"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Price</label>
                            <input name="price" type="number" min="1" step="1" value="${saleAd.price}"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Active</label>
                            <g:if test="${saleAd.active ? true : false}">
                                <input type="checkbox" name="active" id="active" checked>
                            </g:if>
                            <g:else>
                                <input type="checkbox" name="active" id="active" checked>
                            </g:else>
                        </div>

                        <div class="fieldcontain required">
                            <label>Category</label>
                            <g:select from="${categoryList}" name="category.id"
                                      value="${saleAd.category.id}" optionKey="id" optionValue="name"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Author</label>
                            <g:select from="${userList}" name="author.id"
                                      value="${saleAd.author.id}" optionKey="id" optionValue="username"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Address</label>
                            <input name="address.address" type="text" value="${saleAd.address.address}"/>
                            <label>Postcode</label>
                            <input name="address.postCode" type="text" value="${saleAd.address.postCode}"/>
                            <label>City</label>
                            <input name="address.city" type="text" value="${saleAd.address.city}"/>
                            <label>Country</label>
                            <input name="address.country" type="text" value="${saleAd.address.country}"/>
                        </div>

                        <div class="fieldcontain required">
                            <label>Illustration</label>
                            <input type="file" name="toto"/>
                            <g:each in="${saleAd.illustrations}" var="illustration">
                                <img src="${grailsApplication.config.illustrations.baseUrl + illustration.fileName}"/>
                            </g:each>
                        </div>
                    </fieldset>
                    <fieldset class="buttons">
                        <input class="save" type="submit"
                               value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    </fieldset>
                </g:form>
            </div>
        </section>
    </div>
</div>
</body>
</html>
