jQuery(function($){
    let studentName = $.cookie('student-name');

    let processing = false;

    if (undefined === studentName || 'undefined' === studentName) {
        $('#student-name').show();
    } else {
        $('#student-answer').show();
    }

    $('#student-registration').on( "click", function() {
        let studentName = $('#student-id').val();
        $.cookie("student-name", studentName, {expires: 365});
        location.reload();
    });

    $('#student-request').on( "click", function() {

        if (processing === false) {
            processing = true;
            let studentName = $.cookie('student-name');

            $.post( "student", { studentName: studentName } ).done(function() {
                processing = false;
            });
        }
    });

    $('#student-exit').on( "click", function() {
        $.cookie("student-name", 'undefined');
        location.reload();
    });
});