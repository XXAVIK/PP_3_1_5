const tableBody = document.getElementById('userTableBody');

function newRow(user) {
    const row = tableBody.insertRow();
    let rolesView = '';

    row.id = 'info-row-' + user.id;
    user.roles.forEach(role => {
        rolesView = rolesView + role.name + ';';
    })
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.lastName}</td>
        <td>${user.email}</td>
        <td>${user.age}</td>
        <td>${rolesView}</td>
        <td>
            <button type='submit' onclick=' editUser(${user.id})' class="btn btn-primary">Edit</button>
        </td>
        <td>
            <button type='submit' onclick=' deleteUser(${user.id})' class='btn btn-danger'>Delete</button>
        </td>
      `;

}