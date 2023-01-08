let whoo="", tId="", tTile="", tPriority="", tName="", eId="", eTitle="", eDate="", eName="";
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
                }
                ;
                $("#" + whoo).addClass("filter-active");
            }
            $("#" + whoo + " >form").hide();
            $("#" + whoo + " >form").find("input").val("");
        });
        $(".cardStakeholder").click((e) => {
            let id = e.target.id;
            let element = users.find(ele => ele.id == id);
            id=id.toString().replaceAll('-','');
            if (element != null) {
                $('#modalStakeholder form').attr("method", "post");
                $('#modalStakeholder form').attr("action", "/stakeholders/update/" + id);
                $('#modalStakeholder input[name^="name"]').val(element.name);
                $('#modalStakeholder input[name^="name"]').addClass("input-form-focus");
                $('#modalStakeholder input[name^="surname"]').val(element.surname);
                $('#modalStakeholder input[name^="surname"]').addClass("input-form-focus");
                $('#modalStakeholder select').val(element.role);
                if (element.role != null && element.role != "") {
                    $('#modalStakeholder select').addClass("input-form-focus");
                }
                $('#modalStakeholder .modal-footer > a').show();
                $('#modalStakeholder .modal-footer > a').attr("href", "/stakeholders/delete/" + id);
                $('#modalStakeholder .modal-subtitles > p').text("Working with the stakeholder...")
                $('#modalStakeholder').modal('show');
            }
        });

        $(".createStakeholder").click(() => {
            $('#modalStakeholder .modal-subtitles > p').text("Working with the stakeholder...")
            $('#modalStakeholder form').attr("method", "post");
            $('#modalStakeholder form').attr("action", "/stakeholders/create");
            $('#modalStakeholder .modal-footer > a').hide();
            $('#modalStakeholder').modal('show');
        })

        $('#modalStakeholder').on('hidden.bs.modal', () => {
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

function modalCreateStakeholder() {
    $('#modalStakeholder').modal('show');
};