package tj.m.kotlinprojects.data.firebase.model


data class Reminder(
    var body: String = "",
    var date: String = "",
    var helperUID: String = ""
)