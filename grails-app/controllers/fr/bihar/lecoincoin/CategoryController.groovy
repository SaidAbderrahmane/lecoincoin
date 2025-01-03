package fr.bihar.lecoincoin


import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.*

/**
 * CategoryController handles CRUD operations for Category entities.
 */
@Secured(['ROLE_ADMIN','ROLE_MODO'])
class CategoryController {

    CategoryService categoryService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond categoryService.list(params), model:[categoryCount: categoryService.count()]
    }

    def show(Long id) {
        respond categoryService.get(id)
    }

    def create() {
        respond new Category(params), model: [categoryList: Category.list()]
    }

    def save(Category category) {
        if (category == null) {
            notFound()
            return
        }

        try {
            categoryService.save(category)
        } catch (ValidationException e) {
            respond category.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), category.id])
                redirect category
            }
            '*' { respond category, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond categoryService.get(id)
    }

    def update(Category category) {
        if (category == null) {
            notFound()
            return
        }

        try {
            categoryService.save(category)
        } catch (ValidationException e) {
            respond category.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), category.id])
                redirect category
            }
            '*' { respond category, [status: OK] }
        }
    }

    @Transactional
    def delete(Long id) {
        if (id == null || categoryService.get(id) == null) {
            notFound()
            return
        }

        Category category = categoryService.get(id)

        // if root category, forbid deletion
        if (category.id == 1) {
            response.sendError(FORBIDDEN.value())
            return
        }

        // if parent, set its children's parent attribute to null
        category.children.each { it.parent = null }
        category.children*.save()

        // move all sale ads to root category
        Category root = Category.get(1)
        category.saleAds.each { it.category = root }
        category.saleAds*.save()

        categoryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
                redirect action:'index', method:'GET'
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
