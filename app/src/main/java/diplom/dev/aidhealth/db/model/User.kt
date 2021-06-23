package diplom.dev.aidhealth.db.model

class User {

    var email: String = ""//
    var password: String = ""//
    var firstName: String = ""//
    var lastName: String = ""//

    constructor(email: String, password: String, firstName: String, lastName: String){
        this.email = email
        this.password = password
        this.firstName = firstName
        this.lastName = lastName

    }
    constructor(){

    }

}