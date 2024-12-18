package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional


@Secured(['ROLE_ADMIN'])

class UserController {

    UserService userService
    SpringSecurityService springSecurityService


    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }

    def create() {
        respond new User(params), model: [roleList: Role.list()]
    }
    
    @Transactional
    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            
            def roleId = params.role?.id
            if (roleId) {
                userService.save(user)
                UserRole.where { user == user }.deleteAll()
                def role = Role.get(roleId)
                if (role) {
                    UserRole.create(user, role, true) 
                    } else {
                    log.warn "Role with id $roleId not found"
                }
            }

        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:'index'
            }
            '*' { respond user, [status: CREATED] }
        }
    }
    
    @Secured(['ROLE_ADMIN','ROLE_CLIENT'])
    def edit(Long id) {

        User logginUser = (User)springSecurityService.getCurrentUser()
        def userRole = Role.findByAuthority('ROLE_CLIENT')
        if (logginUser.getAuthorities().contains(userRole) && id != logginUser.id)
            redirect(url: "/")
        else
            respond userService.get(id), model: [roleList: Role.list()]

    }


    @Secured(['ROLE_ADMIN','ROLE_CLIENT'])
    @Transactional
    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            def roleId = params.role?.id
            if (roleId) {
                userService.save(user)
                UserRole.where { user == user }.deleteAll()
                def role = Role.get(roleId)
                if (role) {
                    UserRole.create(user, role, true) 
                    } else {
                    log.warn "Role with id $roleId not found"
                }
            }
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:'index'
            }
            '*' { respond user, [status: OK] }
        }
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        UserRole.where { user == User.get(id) }.deleteAll()
        Message.where { author == User.get(id) || dest == User.get(id)}.deleteAll()
        Message.where { dest == User.get(id) }.deleteAll()
        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:'index', method:'GET'
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
