package org.example

class User private constructor(
    val name: String,
    val age: Int,
    val email: String?) {

    class Builder {
        private var name: String = ""
        private var age: Int = 0
        private var email: String? = null

        fun name(v: String) = apply {
            name = v
        }

        fun age(v: Int) = apply {
            age = v
        }

        fun email(v: String) = apply {
            email = v
        }

        fun build(): User {
            require(name.isNotBlank()) { "name is required" }
            return User(name = name, age = age, email = email)
        }
    }

    override fun toString(): String {
        return "User(name='$name', age='$age', email='$email')"
    }
}

fun main() {

    run {
        val user = User.Builder()
            .name("Tom")
            .age(30)
            .build()
        System.out.println(user)
    }

    run {
        val user = User.Builder()
            .name("Tom")
            .apply {
                age(30)
                email("tom@example.com")
            }
            .build()
        System.out.println(user)
    }
}
