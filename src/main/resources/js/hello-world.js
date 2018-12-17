/**
 * 
 * GLOBAL VARIABLES
 */
var __SUCCESS = "success", __ERROR = "error", __DELETED = "deleted";
/*
 * RUN SCRIPT
 */
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

    if (status == __SUCCESS) {
        require('aui/flag')({
            type: "success",
            title: 'New Space',
            body: "New Space has been created."
        });
    } else if (status == __ERROR) {
        $("#message01").show();
    } else if( status == __DELETED){
        require('aui/flag')({
            type: "success",
            title: 'New Space',
            body: "Your Space has been deleted."
        });
    }
}

/*
 *  Show pages (currently under construction)
 */
function showAllPages() {
    $(":radio").click(function (e) {
        $('#page-select').empty();
        var page = $(e.target);
        //var spaceName = page.attr("name");
        $.getJSON(page.attr("value"), function (data) {
            $("#combobox-label").text( AJS.I18n.getText('helloworld.lang.choosePage') + ":");
            createComboBox(data);
        });
    });
}

function createComboBox(data) {
    $.each(data, function (index, object) {
        for (var key in object) {
            var temp = document.createElement('option');
            $('#page-select').append(temp);
            var option = document.createElement('option');
            option.setAttribute("value", key);
            option.setAttribute("name", object[key]);
            var optionText = document.createTextNode(object[key]);
            option.appendChild(optionText);
            $('#page-select').append(option);
        }
    });
    //show combobox
    $("#pages").css("display", "block");
}   
/*
 * Pop up attachment
 * 
 */
function showDialogAttachment() {
    // Shows the dialog when the "Show dialog" select is changed
    AJS.$("#page-select").change(function () {
        var pageId = $("#page-select option:selected").val();
        var pageName = $("#page-select option:selected").text();
        console.log(pageName);
        $("#dialog-header").text(pageName);
        popupAttachment(pageId);
        AJS.dialog2("#attachment-dialog").show();
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