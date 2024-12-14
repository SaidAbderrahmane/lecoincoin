<%@ page import="fr.bihar.lecoincoin.Message" %>
<%@ page import="fr.bihar.lecoincoin.User" %>
<%-- <!doctype html> --%>
<html lang="en" class="scroll-smooth">
<head>
    <meta name="layout" content="main" />
    <title>Chat</title>
    <asset:stylesheet src="tailwind.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css" integrity="sha512-5Hs3dF2AEPkpNAR7UiOHba+lRSJNeM2ECkwxUIxC1Q/FLycGTbNapWXB4tP889k5T5Ju8fs4b1P5z/iB4nMfSQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body class="bg-gray-100 h-screen flex flex-col">
<div class="flex-1 overflow-y-auto p-4">
    <div class="flex flex-col"> 
        <g:each in="${messages}" var="message">
        <div class="d-flex flex-row-reverse">
            <div class="${message.author == user ? 'bg-blue-200' : 'bg-gray-200'} rounded p-2 mb-2">
                <div class="flex items-center">
                    <div class="flex-1">
                        <span class="font-bold">${message.author.username}</span>
                    </div>
                    <div class="text-xs text-gray-600">${message.dateCreated}</div>
                    <g:if test="${message.author == user}">
                    <g:form resource="${this.message}" controller="message" action="delete" method="DELETE">
                    <form controller="message" action="delete" method="DELETE">
                    <input type="hidden" name="id" value="${message.id}"/>
                    <button type="submit" class="text-red-500 hover:text-red-700 px-3">
                    <i class="fa-solid fa-trash"></i>                 
                    </button>
                    </g:form>
                    </g:if>
                </div>
                <div>${message.content}</div>
            </div>
        </div>
        </g:each>
    </div>
</div>
<div class="bg-white p-4">
    <form action="${createLink(controller: 'message', action: 'sendMessage')}" method="post">
        <input type="hidden" name="destId" value="${otherUser.id}"/>
        <div class="flex">
            <input type="text" name="content" placeholder="Type a message..." class="flex-1 px-4 py-2 rounded"/>
            <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Send</button>
        </div>
    </form>
</div>
</body>

</html>
