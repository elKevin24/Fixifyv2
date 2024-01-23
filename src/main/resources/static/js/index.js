$(document).ready(function () {

    $(".delete-ticket").click(function (e) {
        e.preventDefault(); // Evita que el enlace redireccione

        let ticketId = $(this).data("id"); // Obtiene el ticketId del atributo data-id
        let row = $(this).closest('tr'); // Obtiene la fila de la tabla correspondiente al botón clickeado

        if (confirm("¿Estás seguro de que deseas eliminar este ticket?")) {
            onDelete(ticketId, row); // Pasa el ticketId y la fila a la función onDelete
        }
    })


    $('#tickets').DataTable({
        "order": [[ 0, 'desc' ]],
        dom: 'Bfrtip',
        buttons: [
            'colvis',
            'excel',
            'pdf',
            'print'
        ]
    });
});

function onDelete(ticketId, row) {
    // Agrega el token CSRF a la solicitud AJAX
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/fixify/tickets/' + ticketId, // URL del controlador para eliminar el ticket
        type: 'DELETE', // Usa el método DELETE
        beforeSend: function (xhr) {
            // Incluye el token CSRF en la cabecera de la solicitud
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            row.remove(); // Elimina la fila de la tabla
            // Manejar la respuesta exitosa
            alert('Ticket eliminado con éxito.');
            // Aquí puedes realizar cualquier otra acción, como actualizar la tabla de tickets.
        },
        error: function (xhr, status, error) {
            // Manejar errores
            alert('Error al eliminar el ticket: ' + xhr.responseText);
        }
    });

}
function filterTable(buttonElement) {
    // Construir el ID del elemento <p>
    const index = buttonElement.getAttribute('data-index');
    // Obtener la instancia de DataTables de la tabla
    let table = $('#tickets').DataTable();
    // Filtrar la tabla
    table.search(index).draw();
}

function fetchTicketById(ticketId) {
    $.ajax({
        url: '/tickets/' + ticketId, // Asegúrate de que la URL coincida con tu configuración de servidor
        type: 'GET',
        success: function(ticket) {
            console.log('Ticket encontrado:', ticket);
            // Aquí puedes actualizar la UI con los detalles del ticket
        },
        error: function(error) {
            console.error('Error al buscar el ticket:', error);
            // Manejo de errores (por ejemplo, mostrar un mensaje al usuario)
        }
    });
}
