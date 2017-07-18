/**
 * Created by xiaoluo on 17-7-18.
 */
$(function () {
    var phone = $("#phone");
    var button = $("#submit");

    button.on("click", function () {
        $.ajax({
            contentType: "application/json; charset=utf-8",
            url: '../account/account.form?phone=' + phone.val(),
            success: function() {
                alert("success");
            },
            error: function (xhr, desc, err) {
                console.log(xhr);
                console.log("Details: " + desc + "\nError:" + err);
            }
        });
    });

});