$(document).ready( function () {
    $('#brandSelect').selectize({
        create: function (input, callback) {
            // Abre el modal
            $('#newBrandModal').modal('show');
            $('#brandName').val(input); // Puedes pre-rellenar el modal con el input del usuario
            $("#newBrandForm").off("submit").on("submit", function (event) {
                event.preventDefault(); // Previene el envío predeterminado

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/fixify/device/brands",
                    data:  JSON.stringify({
                        name: $("#brandName").val(),
                        // Agrega otros campos si es necesario
                    }),
                    dataType: 'json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
                    },
                    success: function (brand) {
                        callback({value: brand.id, text: brand.name});
                        // Cierra el modal
                        $('#newBrandModal').modal('toggle');

                    },
                    error: function (e) {
                        console.log("Error", e.responseJSON.message);
                            showError("Error al crear la marca: " + e.responseJSON.message);
                    }
                });
            });
        }
    });


    $('#categorySelect').selectize({
        create: function (input, callback) {
            $('#newCategoryModal').modal('show');
            $('#categoryName').val(input);
            $('#newCategoryForm').off('submit').on('submit', function (event) {
                event.preventDefault();

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/fixify/device/categories",
                    data: JSON.stringify({ name: $('#categoryName').val() }),
                    dataType: 'json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
                    },
                    success: function (category) {
                        callback({value: category.id, text: category.name});
                        $('#newCategoryModal').modal('toggle');
                    },
                    error: function (e) {
                        console.log("Error", e.responseJSON.message);
                        showError("Error al crear la categoría: " + e.responseJSON.message);
                    }
                });
            });
        }
    });

    // Configuración de Selectize para 'customerSelect'
    $('#customerSelect').selectize({
        create: function (input, callback) {
            $('#newCustomerModal').modal('show');
            $('#customerName').val(input);
            $('#newCustomerForm').off('submit').on('submit', function (event) {
                event.preventDefault();

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/fixify/customer",
                    data: JSON.stringify({ name: $('#customerName').val() }),
                    dataType: 'json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
                    },
                    success: function (customer) {
                        callback({value: customer.id, text: customer.name});
                        $('#newCustomerModal').modal('toggle');
                    },
                    error: function (e) {
                        console.log("Error", e.responseJSON.message);
                        showError("Error al crear el cliente: " + e.responseJSON.message);
                    }
                });
            });
        }
    });
});


function showError(message) {
    let alertHtml = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
        message +
        '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
        '</div>';
    $('#alert-container').html(alertHtml).removeClass('d-none');
}
