jQuery(function($){


    const interval = setInterval(function() {
        $.get( "get-students" ).done(function(data) {
            $('#students-list').html('');
            $(data).each(function( index, value ) {
                $('<div>' + value.name + '</div>' ).appendTo('#students-list');
            });
        });
    }, 500);


    $('#teacher-registration').on( "click", function() {
        let codeInRequest = $('#code-in-request').val();

        $.post( "auth", { codeInRequest: codeInRequest } ).done(function(data) {
            if (data === true) {
                $('#teacher-code').hide();
                $('#teacher-next').show();
                $('#students-list').show();
            } else {
                alert("Codigo incorrecto");
            }
        });
    });

    $('#teacher-continue').on( "click", function() {
        $.post( "next" );
    });

});