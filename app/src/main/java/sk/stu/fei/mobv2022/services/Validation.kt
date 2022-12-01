package sk.stu.fei.mobv2022.services

class Validation {

    companion object Factory {
        fun validPassword(password: String, confirmPassword: String): Boolean {
            if (password.isBlank()) {
                return false;
            }
            if (password.length < 3) {
                return false
            }
            if (password.compareTo(confirmPassword) != 0) {
                return false
            }
            return true;
        }

        fun validUser(userName: String): Boolean {
            if(userName.isBlank()){
                return false;
            }
            return true;
        }
    }
}