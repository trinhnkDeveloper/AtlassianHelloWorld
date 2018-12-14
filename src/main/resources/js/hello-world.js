$(document).ready(function () {
    showAllPages();
    successCreateSpace("status");
    showDialogAttachment();
});
/*
 *  FUNCTION 
 * 
 */
function getUrlParameter(sParam) {
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
}

function successCreateSpace(sParam) {
    var status = getUrlParameter("status");
    console.log(status);

    if (status == "success") {
        require('aui/flag')({
            type: "success",
            title: 'New Space',
            body: "New Space has been created."
        });
    } else if (status == "error") {
        $("#message01").show();
    }
}

/*
 *  Show pages (currently under construction)
 */
function showAllPages() {
    $(":radio").click(function (e) {
        $('#sync-product-single-select').empty();
        var a = $(e.target);
        $.getJSON(a.attr("value"), function (data) {
            console.log(data);
            createComboBox(data);
        });
    });
}

function createComboBox(data) {
    $.each(data, function (index, object) {
        for (var key in object) {
            var option = document.createElement('option');
            option.setAttribute("value", key);
            var optionText = document.createTextNode(object[key]);
            option.appendChild(optionText);
            $('#sync-product-single-select').append(option);
        }
    });
    //show 
    $("#pages").css("display", "block");
}
/*
 * Pop up attachment
 * 
 */
function showDialogAttachment() {
    // Shows the dialog when the "Show dialog" select is changed
    AJS.$("#sync-product-single-select").change(function () {
        var pageId = $("#sync-product-single-select").val();
        popupAttachment(pageId);
        AJS.dialog2("#demo-dialog").show();
    });
}

function popupAttachment(pageid) {
    $.getJSON("spacecontent.action", {"pageid": pageid, "option": "2"}, function (data) {
        createTable(data);
    });
}

function createTable(data) {
    $.each(data, function (index, object) {
        var bodyRow = document.createElement("tr");
        var datName = document.createElement("td");
        var link = document.createElement("a");
        link.setAttribute("href", AJS.contextPath() + object.downloadPath);
        link.setAttribute("download", object.name);
        var textName = document.createTextNode(object.name);
        datName.appendChild(link);
        link.appendChild(textName);

        var datSize = document.createElement("td");
        var textSize = document.createTextNode(object.size);
        datSize.appendChild(textSize);

        var datCreator = document.createElement("td");
        var textCreator = document.createTextNode(object.creator);
        datCreator.appendChild(textCreator);

        var datDate = document.createElement('td');
        var textDate = document.createTextNode(object.creationDate);
        datDate.appendChild(textDate);

        bodyRow.appendChild(datName);
        bodyRow.appendChild(datSize);
        bodyRow.appendChild(datCreator);
        bodyRow.appendChild(datDate);
        console.log(bodyRow);
        $('#attachment-table-body').append(bodyRow);
    });

}