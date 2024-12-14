package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Secured(['ROLE_ADMIN',"ROLE_CLIENT","ROLE_MODO"])
class MessageController {

    MessageService messageService
    SpringSecurityService springSecurityService

    static allowedMethods = [sendMessage:"POST", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, String searchString) {        
        params.max = Math.min(max ?: 10, 100)
               

        def userList = []
        if (searchString != null && searchString != "") {
            userList = User.createCriteria().list {
                or {
                    ilike('username', "%${searchString}%")
                    ilike('email', "%${searchString}%")
                }
            }
        } else {

        def user = (User)springSecurityService.getCurrentUser()
        if (user == null) {
            notFound()
            return
        }
        def messagesBySender =  Message.where { dest == user }.list().groupBy { it.author }
        userList = messagesBySender.keySet()
        }

        respond messageService.list(params), model:[userList:userList, searchString: searchString]
    }
    

    @Transactional
    def conversation(Long id) {


        def currentUser = springSecurityService.currentUser
        def otherUser = User.get(id)

        if (!currentUser || !otherUser) {
            flash.message = "User not found."
            redirect(action: 'index')
            return
        }

        // Get all messages between the current user and the other user
        def conversation = Message.createCriteria().list {
            or {
                and {
                    eq('author', currentUser)
                    eq('dest', otherUser)
                }
                and {
                    eq('author', otherUser)
                    eq('dest', currentUser)
                }
            }
            order('dateCreated', 'asc')
        }

        // Mark all received messages as read
        Message.findAll{ dest == currentUser && author == otherUser && isRead==false }.each {
            it.isRead = true
            it.save(flush: true)
        }

        render(view: 'conversation', model: [user: currentUser, otherUser: otherUser, messages: conversation])
    }



    /**
     * Send a message.
     */
    @Transactional
    def sendMessage() {
        def currentUser = springSecurityService.currentUser
        def dest = User.get(params.destId)
        def content = params.content

        if (!currentUser || !dest || !content) {
            flash.message = "Message could not be sent. Please check your input."
            redirect(action: 'index')
            return
        }

        def message = new Message(author: currentUser, dest: dest, content: content, dateCreated: new Date())
        if (message.save(flush: true)) {
            flash.message = "Message sent!"
        } else {
            flash.message = "Failed to send the message."
        }

        redirect(action: 'conversation', id: dest.id)
    }

    // 

    def show(Long id) {
        respond messageService.get(id)
    }

    def create() {
        respond new Message(params)
    }

    def save(Message message) {
        if (message == null) {
            notFound()
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'message.label', default: 'Message'), message.id])
                redirect message
            }
            '*' { respond message, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond messageService.get(id)
    }

    def update(Message message) {
        if (message == null) {
            notFound()
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'message.label', default: 'Message'), message.id])
                redirect message
            }
            '*'{ respond message, [status: OK] }
        }
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        if(messageService.get(id).author.id != springSecurityService.currentUser.id) {
            notFound()
            return
        }

        print("deleting message $id")
        messageService.delete(id)
        
        
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), id])
                
                redirect(uri: request.getHeader('referer') )
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
