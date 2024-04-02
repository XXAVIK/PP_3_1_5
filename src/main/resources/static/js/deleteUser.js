async function deleteUser(id) {
    const response = await fetch('api/users/' + id, {
        method: 'DELETE',
    });
    const row = document.getElementById('info-row-' + id)
    row.parentNode.removeChild(row);
    const delete_alert = await response.text();
    alert(delete_alert);

}
