jQuery(function($){


    $('#teacher-registration').on( "click", function() {
        let codeInRequest = $('#code-in-request').val();

        $.post( "auth", { codeInRequest: codeInRequest } ).done(function(data) {
            if (data === true) {
                alert('ok')
            } else {
                alert('false')
            }
        });

    });

});