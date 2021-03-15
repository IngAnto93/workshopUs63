var getBooksUrl = "/workshop-us63/books";
var responseBooks;

$(document).ready(function () {
    getBooks();
});

var getBooks = function () {
    $.ajax({
        url: getBooksUrl,
        type: "GET",
        timeout: 30000,
        success: function (result, textStatus, jqXHR) {
        	if (result) {
				responseBooks = result;
                responseBooks.forEach((book) => {
                    $("#books").append(
                    '<div class="row" style="margin-left:2.5rem">\n' +
                    '<div class="col-md-7">\n' +
                    '<label>' + book.title +
                    '</label>' + " - " +
                    '<label>' + book.author + '</label>' + " - " +
                    '<label>' + book.genre + '</label>' + '\n' +
                    '</div>' + '\n'
                    );
                });
            }
        }
    });
};

var getBooksByType = function () {
    $.ajax({
        url: getBooksUrl + "/" + $("#type").val(),
        type: "GET",
        timeout: 30000,
        success: function (result, textStatus, jqXHR) {
            $("#booksByType").empty();
            if (result) {
                responseBooks = result;
                responseBooks.forEach((book) => {
                    $("#booksByType").append(
                    '<div class="row" style="margin-left:2.5rem">\n' +
                    '<div class="col-md-7">\n' +
                    '<label>' + book.title +
                    '</label>' + " - " +
                    '<label>' + book.author + '</label>' + " - " +
                    '<label>' + book.genre + '</label>' + '\n' +
                    '</div>' + '\n'
                    );
                });
            }
        }
    });
};