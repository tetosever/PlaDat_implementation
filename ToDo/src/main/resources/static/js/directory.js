$(document).ready(
    () => {
        //Here you can use users from Thymeleaf
        $(".cardDirectory").click((e) => {
            $("#modalDirectory select option").show();
            let id = e.target.id;
            let element = directories.find(ele => ele.id == id);
            id=id.toString().replaceAll('-','');
            if (element != null) {
                $('#modalDirectory form').attr("method", "post");
                $('#modalDirectory form').attr("action", "/directories/update/" + id);
                $('#modalDirectory input[name^="name"]').val(element.name);
                $('#modalDirectory input[name^="name"]').addClass("input-form-focus");
                $("#modalDirectory select").html("");
                directoryListForUpdate.forEach((element) => {
                    $('#modalDirectory select').append($('<option>', {
                        value: element.id,
                        text: element.name
                    }));
                });
                $("#modalDirectory select option[value='"+element.id+"']").hide();
                if(element.directory!=null)
                {
                    $('#modalDirectory select').val(element.directory.id);
                    if (element.directory.id != null && element.directory.id != "") {
                        $('#modalDirectory select').addClass("input-form-focus");
                    }
                }
                else{
                    $('#modalDirectory select').val("");
                }
                $('#modalDirectory .modal-footer > a').show();
                $('#modalDirectory .modal-footer > a').attr("href", "/directories/delete/" + id);
                $('#modalDirectory .modal-subtitles > p').text("Working with the directory...")
                $('#modalDirectory').modal('show');
            }
        });

        $(".createDirectory").click(() => {
            $("#modalDirectory select").html("");
            allDirectories.forEach((element) => {
                $('#modalDirectory select').append($('<option>', {
                    value: element.id,
                    text: element.name
                }));
            });
            $("#modalDirectory select option").show();
            $('#modalDirectory .modal-subtitles > p').text("Creating the directory...")
            $('#modalDirectory form').attr("method", "post");
            $('#modalDirectory form').attr("action", "/directories/create");
            $('#modalDirectory .modal-footer > a').hide();
            $('#modalDirectory').modal('show');
            $("#modalDirectory select").val("");
        })

        $('#modalDirectory').on('hidden.bs.modal', () => {
            $(".input-form").val("");
        })
    }
)