$(document).ready(() => {
    $(".input-form").val("");

    $(".input-form").focus((e) => {
        $(e.target).addClass("input-form-focus");
    })

    $(".input-form").focusout((e) => {
        if ($(e.target).val() == null || $(e.target).val() == "") {
            $(e.target).val("");
            $(e.target).removeClass('input-form-focus');
        } else {
            $(e.target).val($(e.target).val());
        }
    });

    $(".modal").on('hide.bs.modal', () => {
        $(".input-form").val("");
        $(".input-form").removeClass('input-form-focus');
    });
});