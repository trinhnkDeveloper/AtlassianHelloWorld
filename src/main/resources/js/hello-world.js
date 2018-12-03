$(document).ready(function () {
    
    //success created new space
    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    };

    var status = getUrlParameter("status");
    console.log(status);

    if (status == "success") {
        require('aui/flag')({
            type: "success",
            title: 'New Space',
            body: "New Space has been created."
        });
    }else if(status == "error"){
        require('aui/flag')({
            type: "error",
            title: "Error",
            body: "Cannot create new space."
        });
    }
    //=====================end of method=======================
    
    
    //Check input space name
    $("#txtSpaceName").focusout(function () {
        var temp = $("#txtSpaceName").val();
        console.log(temp);
        if (temp.length <= 0) {
            showError("space-name-error", "Space name is empty");
        } else {
            removeError("space-name-error");
        }
    });

    //check input space key
    $("#txtSpaceKey").focusout(function () {
        var temp = $("#txtSpaceKey").val();
        var valid = true;
        if (temp.match(/^[a-zA-Z0-9]+$/) && temp.length <= 255) {
            var listKey = $(".spaceKey");
            for (var i = 0; i < listKey.length; i++) {
                if (temp.match($(listKey[i]).text())) {
                    valid = false;
                    showError("space-key-error", "Dulicate Space Key");
                    break;
                }
            }
        } else {
            valid = false;
            showError("space-key-error", "space key khong hop le, space key chi duoc [a-z][0-9]");
        }
        if (valid) {
            removeError("space-key-error");
        }
    });

    //remove error notice
    function removeError(spaceErrorClass) {
        if ($("#message01").hasClass(spaceErrorClass)) {
            $("#message01").removeClass(spaceErrorClass);
            $("#message01").slideUp(1000);
            $("#body").empty();
            $("#addBtn").removeAttr("disabled");
        }
    }

    //show message error
    function showError(spaceErrorClass, message) {
        $("#body").empty();
        $("#message01").slideDown(1000);
        $("#message01").addClass(spaceErrorClass);
        $("#body").append(message);
        $("#addBtn").attr("disabled", "disabled");
    }
});




























