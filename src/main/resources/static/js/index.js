$(document).ready(function () {
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