let whoo="", tId="", tTile="", tPriority="", tName="", eId="", eTitle="", eDate="", eName="";
let directories, users;
$(document).ready(
    () => {
        $(".new-filter").hide();
        $(".filter").click((e) =>
        {
            let id = e.target.id;
            if(id!=null && id!="")
            {
                whoo=id;
                $("#"+whoo+" > .new-filter").show();
            }
        });
        $(".new-filter > span").click((e) => {
            let text = $("#" + whoo + ">form>input").val().trim();
            if (text != "") {
                switch (whoo) {
                    case "f-t-id": {
                        break;
                    }
                    case "f-t-title": {
                        break;
                    }
                    case "f-t-priority": {
                        break;
                    }
                    case "f-t-user": {
                        break;
                    }
                    case "f-t-id": {
                        break;
                    }
                    case "f-t-id": {
                        break;
                    }
                    case "f-t-id": {
                        break;
                    }
                };
                $("#" + whoo).addClass("filter-active");
            }
            else
            {
                $("#" + whoo).removeClass("filter-active");
            }
            $("#" + whoo + " >form").hide();
            $("#" + whoo + " >form").find("input").val("");
        });
        $(".card-event").click((e) => {
            let id = e.target.id;
            let element = events.find(ele => ele.id == id);
            id=id.toString().replaceAll('-','');
            if (element != null) {
                $('#modalEvent form').attr("method", "post");
                $('#modalEvent form').attr("action", "/events/update/" + id);
                $('#modalEvent input[name^="title"]').val(element.title);
                $('#modalEvent input[name^="title"]').addClass("input-form-focus");
                $('#modalEvent input[name^="description"]').val(element.description);
                $('#modalEvent input[name^="description"]').addClass("input-form-focus");
                $('#modalEvent select:nth-child(3)').val(element.priority);
                if (element.priority != null && element.priority != "") {
                    $('#modalEvent select:nth-child(3)').addClass("input-form-focus");
                }
                $('#modalEvent select:nth-child(3)').val(element.priority);
                if (element.priority != null && element.priority != "") {
                    $('#modalEvent select:nth-child(3)').addClass("input-form-focus");
                }$('#modalEvent select:nth-child(3)').val(element.usersList);
                if (element.usersList != null && element.usersList != "") {
                    $('#modalEvent select:nth-child(3)').addClass("input-form-focus");
                }
                $('#modalEvent select:nth-child(3)').val(element.directory.id);
                if (element.priority != null && element.priority != "") {
                    $('#modalEvent select:nth-child(3)').addClass("input-form-focus");
                }
                $('#modalEvent .modal-footer > a').show();
                $('#modalEvent .modal-footer > a').attr("href", "/events/delete/" + id);
                $('#modalEvent .modal-subtitles > p').text("Working with the event...")
                $('#modalEvent').modal('show');
            }
        });

        $(".createEvent").click(() => {
            $('#modalEvent .modal-subtitles > p').text("Working with the event...")
            $('#modalEvent form').attr("method", "post");
            $('#modalEvent form').attr("action", "/events/create");
            $('#modalEvent .modal-footer > a').hide();
            $('#modalEvent').modal('show');
        })

        $('#modalEvent').on('hidden.bs.modal', () => {
            $(".input-form").val("");
        })
        $(".card-task").click((e) => {
            let id = e.target.id;
            let element = users.find(ele => ele.id == id);
            id=id.toString().replaceAll('-','');
            if (element != null) {
                $('#modalTask form').attr("method", "post");
                $('#modalTask form').attr("action", "/tasks/update/" + id);
                $('#modalTask input[name^="name"]').val(element.name);
                $('#modalTask input[name^="name"]').addClass("input-form-focus");
                $('#modalTask input[name^="surname"]').val(element.surname);
                $('#modalTask input[name^="surname"]').addClass("input-form-focus");
                $('#modalTask select').val(element.role);
                if (element.role != null && element.role != "") {
                    $('#modalTask select').addClass("input-form-focus");
                }
                $('#modalTask .modal-footer > a').show();
                $('#modalTask .modal-footer > a').attr("href", "/tasks/delete/" + id);
                $('#modalTask .modal-subtitles > p').text("Working with the task...")
                $('#modalTask').modal('show');
            }
        });

        $(".createTask").click(() => {
            $('#modalTask .modal-subtitles > p').text("Creating the task...")
            $('#modalTask form').attr("method", "post");
            $('#modalTask form').attr("action", "/tasks/create");
            $('#modalTask .modal-footer > a').hide();
            $('#modalTask').modal('show');
        })

        $('#modalTask').on('hidden.bs.modal', () => {
            $(".input-form").val("");
        })
    }
)

$(document).mouseup((e) =>
{
    let element = $(".new-filter");
    if (!element.is(e.target) && element.has(e.target).length === 0)
    {
        element.hide();
        element.find("input").val("");
    }
});