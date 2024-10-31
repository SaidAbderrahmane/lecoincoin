package fr.bihar.lecoincoin

class Message {

    String content
    Date dateCreated
    User author
    User dest

    static constraints = {
        content nullable: false, blank: false
        author nullable: false
        dest nullable: false
    }

    static mapping = {
        content type: 'text'
    }
}
