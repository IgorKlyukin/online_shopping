function deleteProductFromCart(id) {
    $.ajax({
        type: 'POST',
        url: '/cart/deleteFromCart',
        data: {
            "id": id
        },
        datatype: "json",
        success: function (response) {
            $(".cartEntity_" + id).remove();
            if (response.isEmpty === "true") {
                location.reload();
            }
        },
        error: function () {
            alert("ERROR");
        }
    });
}

function enlargeProductToCart(id) {
    $.ajax({
        type: 'POST',
        url: '/cart/enlargeToCart',
        data: {
            "id": id
        },
        success: function (response) {
            $(".cartEntityCount_" + id).text(response);
            $(".cartEntityCountPrice_" + id).text(
                (parseInt(response) * parseFloat($(".cartEntityPrice_" + id).text())).toFixed(2)
            );
        },
        error: function () {
            alert("ERROR");
        }
    });
}

function reduceProductFromCart(id) {
    $.ajax({
        type: 'POST',
        url: '/cart/reduceFromCart',
        data: {
            "id": id
        },
        datatype: "json",
        success: function (response) {
            if (response.isEmpty === "true") {
                location.reload();
            }
            if (response.reduceFromCart === "0") {
                $(".cartEntity_" + id).remove();
            } else {
                $(".cartEntityCount_" + id).text(response.reduceFromCart);
                $(".cartEntityCountPrice_" + id).text(
                    (parseInt(response.reduceFromCart) * parseFloat($(".cartEntityPrice_" + id).text())).toFixed(2)
                );
            }
        },
        error: function () {
            alert("ERROR");
        }
    });
}