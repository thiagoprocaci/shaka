package com.shaka.authentication

class SecUser {

    transient springSecurityService

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        password column: '`password`'
   //     tablePerhierarchy(false)
    }

    Set<SecRole> getAuthorities() {
        SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Set
    }

    def beforeInsert() {
      //  encodePassword()
        enabled = true
    }

    def beforeUpdate() {
    //    if (isDirty('password')) {
     //       encodePassword()
      //  }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
