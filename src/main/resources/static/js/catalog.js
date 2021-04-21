function addProductToCart(id) {
    $.ajax({
        type: 'POST',
        url: '/cart/addToCart',
        data: {
            "id": id
        },
        datatype: "json",
        success: function (response) {
            $(".countProduct").text(response.countProduct);
            $(".sumProduct").text(response.sumProduct);
        },
        error: function () {
            alert("ERROR");
        }
    });
}
$(document).ready(function() {
    $.ajax({
        url: '/cart/totalCart',
        datatype: "json",
        success: function (response) {
            $(".countProduct").text(response.countProduct);
            $(".sumProduct").text(response.sumProduct);
        },
        error: function () {
            alert("ERROR");
        }
    });
})