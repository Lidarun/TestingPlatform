$(document).ready(function () {

    var questionsArray = new Array();

    $(document).on("click", ".module-link", function (event) {
        event.preventDefault();
        var moduleId = $(this).data("module-id");
        loadQuestions(moduleId);
    });
    var courseId = $("#courseId").val();
    function loadQuestions(moduleId) {
        $.ajax({
            url: "/quiz/" + courseId + "/module/" + moduleId,
            type: "GET",
            dataType: "json",
            success: function (questions) {
                questionsArray = questions;
                sessionStorage.setItem("questionsArray", JSON.stringify(questions));

                window.location.href = "/quiz";

                console.log(questions)
                console.log(questionsArray[0])
            },
            error: function (error) {
                console.log("?????? ??? ???????? ????????: ", error);
            }
        });
    }

    $(document).on("click", ".module-link", function (event) {
        event.preventDefault();

        var moduleId = $(this).data("module-id");
        loadQuestions(moduleId);
    });
});