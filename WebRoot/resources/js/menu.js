$(function () {
    $("#userInfoDiv ul h3").on("mouseover", function () {
        $(this).css("cursor", "pointer");
    });

    $("#userInfoDiv ul li").hide();

    $("#userInfoDiv ul  h3").toggle(function () {
        $(this).nextAll("li").slideDown();
    }, function () {
        $(this).nextAll("li").slideUp();
    });

    $("table").on("mouseout", "tr", function () {
        $(this).removeClass("trbackground");
    });
    $("table").on("mouseover", "tr", function () {
        $(this).addClass("trbackground");
    });

});