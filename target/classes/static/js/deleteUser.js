async function deleteUser(id) {
    // let response = await fetch('api/users/'),
    let response = await fetch('api/secure/users/' + id, {
        method: 'DELETE',
    });
    let row = document.getElementById('info-row-' + id)
    row.parentNode.removeChild(row);
    let delete_alert = await response.text();
    alert(delete_alert);

}
