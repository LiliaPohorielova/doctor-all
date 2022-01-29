$(document).ready(function () {
    $('#doctorNamed').autocomplete({
            source: function (request, response) {
                $.get("http://localhost:8080/doctor/suggestions?", {q: request.term}, function (data, status) {
                    $("#results").html("");
                    if (status === 'success') {
                        response(data);
                    }
                });
            }
        }
    );

    $("#btnBookSearch").click(function () {
        const inputText = $("#doctorNamed").val();
        if (inputText.length === 0) {
            alert("Enter product name or description");
        } else {
            let doctorSearch = document.getElementById('doctorSearch');
            if (doctorSearch) {
                let input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "doctorSearch");
                input.setAttribute("value", inputText);
                doctorSearch.appendChild(input);
            }
        }
    });
});