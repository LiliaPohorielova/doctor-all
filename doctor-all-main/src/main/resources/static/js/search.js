$(document).ready(function () {
    $('#doctorNamed').autocomplete({
            source: function (request, response) {
                $.get("http://localhost:8080/doctors/suggestions?", {q: request.term}, function (data, status) {
                    console.log("get(\"http://localhost:8080/doctors/suggestions?\"");
                    $("#results").html("");
                    if (status === 'success') {
                        response(data);
                    }
                });
            }
        }
    );

    $("#btnDoctorSearch").click(function () {
        const inputText = $("#doctorNamed").val();
        if (inputText.length === 0) {
            alert("Warning! Enter doctor name, please!");
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