$(document).ready( function () {
    $('#create').submit(function (event) {
        event.preventDefault();

        let formData = $(this).serialize(); // Serializa los datos del formulario

        $.ajax({
            url: '/fixify/tickets', // URL del controlador
            type: 'POST',
            data: formData,
            beforeSend: function (xhr) {
                // Incluye el token CSRF en la cabecera de la solicitud
                xhr.setRequestHeader("X-CSRF-TOKEN", $("meta[name='_csrf']").attr("content"));
            },
            success: function (response) {
                console.log(response);
                // Manejar la respuesta exitosa
                alert(response.message +  " Ticket: " + response.id);
            },
            error: function (xhr, status, error) {
                // Manejar errores
                alert('Error al crear el ticket: ' + xhr.responseText);
            }
        });
    });
});