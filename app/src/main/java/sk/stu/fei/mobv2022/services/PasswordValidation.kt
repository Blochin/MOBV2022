package sk.stu.fei.mobv2022.services

class PasswordValidation {

    companion object Factory {
        fun valid(password: String, confirmPassword: String): Boolean {
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
    }
}