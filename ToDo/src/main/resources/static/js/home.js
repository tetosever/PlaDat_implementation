let whoo = "", tId = "", tTile = "", tPriority = "", tName = "", eId = "", eTitle = "", eDate = "",
    eName = "";
$(document).ready(
    () => {
        $(".filter").click((e) => {
            let id = e.target.id;
            if (id != null && id != "") {
                whoo = id;
                switch (whoo) {
                    case "f-t-id": {
                        $("#"+whoo+" > form > input").val(tId);
                        break;
                    }
                    case "f-t-title": {
                        $("#"+whoo+" > form > input").val(tTile);
                        break;
                    }
                    case "f-t-priority": {
                        $("#"+whoo+" > form > select").val(tPriority);
                        break;
                    }
                    case "f-t-name": {
                        $("#"+whoo+" > form > input").val(tName);
                        break;
                    }
                    case "f-e-id": {
                        $("#"+whoo+" > form > input").val(eId);
                        break;
                    }
                    case "f-e-title": {
                        $("#"+whoo+" > form > input").val(eTitle);
                        break;
                    }
                    case "f-e-date": {
                        $("#"+whoo+" > form > input").val(eDate);
                        break;
                    }
                    case "f-e-user": {
                        $("#"+whoo+" > form > input").val(eName);
                        break;
                    }
                };
                $("#" + whoo + " > .new-filter").css("display", "block");
            }
        });
        $(".new-filter > span").click((e) => {
            let text;
            if($("#" + whoo + ">form > input").length)
            {
                text=$("#" + whoo + ">form>input").val().trim();
            }
            else
            {
                text=$("#" + whoo + ">form>select").val();
            }
            if (text != "") {
                switch (whoo) {
                    case "f-t-id": {
                        tId = text;
                        break;
                    }
                    case "f-t-title": {
                        tTile = text;
                        break;
                    }
                    case "f-t-priority": {
                        tPriority = text;
                        break;
                    }
                    case "f-t-name": {
                        tName = text;
                        break;
                    }
                    case "f-e-id": {
                        eId = text;
                        break;
                    }
                    case "f-e-title": {
                        eTitle = text;
                        break;
                    }
                    case "f-e-date": {
                        eDate = text;
                        break;
                    }
                    case "f-e-user": {
                        eName = text;
                        break;
                    }
                };
                $("#" + whoo).addClass("filter-active");
                sendRequest(whoo);
            } else {
                switch (whoo) {
                    case "f-t-id": {
                        tId = "";
                        break;
                    }
                    case "f-t-title": {
                        tTile = "";
                        break;
                    }
                    case "f-t-priority": {
                        tPriority = "";
                        break;
                    }
                    case "f-t-name": {
                        tName = "";
                        break;
                    }
                    case "f-e-id": {
                        eId = "";
                        break;
                    }
                    case "f-f-title": {
                        eTitle = "";
                        break;
                    }
                    case "f-e-date": {
                        eDate = "";
                        break;
                    }
                    case "f-e-user": {
                        eName = "";
                        break;
                    }
                }
                $("#" + whoo).removeClass("filter-active");

            }
            $("#" + whoo + " >form").css("display", "none");
            $("#" + whoo + " >form").find("input").val("");
            $("#" + whoo + " >form").find("select").val("");
        });
        $(".card-event").click((e) => {
            let id = e.target.id;
            let element = events.find(ele => ele.id == id);
            id = id.toString().replaceAll('-', '');
            if (element != null) {
                $('#modalEvent form').attr("method", "post");
                $('#modalEvent form').attr("action", "/events/update/" + id);
                $('#modalEvent input[name^="title"]').val(element.title);
                $('#modalEvent input[name^="title"]').addClass("input-form-focus");
                $('#modalEvent input[name^="start_date"]').val(element.start_date);
                if (element.start_date != null && element.start_date != "") {
                    $('#modalEvent input[name^="start_date"]').addClass("input-form-focus");
                }
                $('#modalEvent input[name^="end_date"]').val(element.end_date);
                if (element.end_date != null && element.end_date != "") {
                    $('#modalEvent input[name^="end_date"]').addClass("input-form-focus");
                }
                $('#modalEvent input[name^="place"]').val(element.place);
                if (element.place != null && element.place != "") {
                    $('#modalEvent input[name^="place"]').addClass("input-form-focus");
                }
                let tmp = element.usersList.map(a => a.id);
                $('#modalEvent .ss1').val(tmp);
                if (tmp != null && tmp != []) {
                    $('#modalEvent .ss1').addClass("input-form-focus");
                }
                $('#modalEvent .ss2').val(element.directory.id);
                if (element.directory.id != null && element.directory.id != "") {
                    $('#modalEvent .ss2').addClass("input-form-focus");
                }
                $('#modalEvent .modal-footer > a').css("display", "block");
                $('#modalEvent .modal-footer > a').attr("href", "/events/delete/" + id);
                $('#modalEvent .modal-subtitles > p').text("Working with the event...")
                $('#modalEvent').modal('show');
            }
        });

        $(".createEvent").click(() => {
            $('#modalEvent .modal-subtitles > p').text("Creating an event...")
            $('#modalEvent form').attr("method", "post");
            $('#modalEvent form').attr("action", "/events/create");
            $('#modalEvent .modal-footer > a').css("display", "none");
            $('#modalEvent').modal('show');
        })

        $('#modalEvent').on('hidden.bs.modal', () => {
            $(".input-form").val("");
        })
        $(".card-task").click((e) => {
            let id = e.target.id;
            let element = tasks.find(ele => ele.id == id);
            id = id.toString().replaceAll('-', '');
            if (element != null) {
                $('#modalTask form').attr("method", "post");
                $('#modalTask form').attr("action", "/tasks/update/" + id);
                $('#modalTask input[name^="title"]').val(element.title);
                $('#modalTask input[name^="title"]').addClass("input-form-focus");
                $('#modalTask input[name^="description"]').val(element.description);
                if (element.description != null && element.description != "") {
                    $('#modalTask input[name^="description"]').addClass("input-form-focus");
                }
                $('#modalTask .ss3').val(element.priority);
                if (element.priority != null && element.priority != "") {
                    $('#modalTask .ss3').addClass("input-form-focus");
                }
                let tmp = element.usersList.map(a => a.id);
                $('#modalTask .ss1').val(tmp);
                if (tmp != null && tmp != []) {
                    $('#modalTask .ss1').addClass("input-form-focus");
                }
                $('#modalTask .ss2').val(element.directory.id);
                if (element.directory.id != null && element.directory.id != "") {
                    $('#modalTask .ss2').addClass("input-form-focus");
                }
                $('#modalTask .modal-footer > a').css("display", "block");
                $('#modalTask .modal-footer > a').attr("href", "/tasks/delete/" + id);
                $('#modalTask .modal-subtitles > p').text("Working with the task...")
                $('#modalTask').modal('show');
            }
        });

        $(".createTask").click(() => {
            $('#modalTask .modal-subtitles > p').text("Creating a task...")
            $('#modalTask form').attr("method", "post");
            $('#modalTask form').attr("action", "/tasks/create");
            $('#modalTask .modal-footer > a').css("display", "none");
            $('#modalTask').modal('show');
        })

        $('#modalTask').on('hidden.bs.modal', () => {
            $(".input-form").val("");
        })
    }
)

$(document).mouseup((e) => {
    let element = $(".new-filter");
    if (!element.is(e.target) && element.has(e.target).length === 0) {
        element.css("display", "none");
        element.find("input").val("");
        element.find("select").val("");
    }
});

function sendRequest(w) {
    if ($("#"+w).hasClass("f-events")) {
        $.get("/events/read/filter?id=" + eId + "&title=" + eTitle + "&start_date=" + eDate + "&user=" + eName, function (data) {
            alert("Load was performed.");
        });
    } else {
        $.get("/tasks/read/filter?id=" + tId + "&title=" + tTile + "&priority=" + tPriority + "&user=" + eName, function (data) {
            alert("Load was performed.");
        });
    }

}