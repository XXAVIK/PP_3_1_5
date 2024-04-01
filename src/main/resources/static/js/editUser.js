const rolesEditBody = document.getElementById('edit-roleSelect')
const editForm = document.getElementById("edit-user")
const modal = new bootstrap.Modal(document.getElementById("edit-modal"))

function editRow(user) {
    const rowId = 'info-row-' + user.id;
    const row = document.getElementById(rowId);

    let rolesView = '';

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

async function editRoles(user) {
    const responseRoles = await fetch('/api/secure/roles'); // Выполняем GET запрос
    const roleList = await responseRoles.json();

    let selectList = document.getElementById("roleEdit");
    selectList.options.length = 0;

    roleList.forEach(role => {
        const option = document.createElement("option")
        option.value = role.id;
        option.text = role.name;
        selectList.appendChild(option);
        user.roles.forEach(userRole => {
            if (role.id === userRole.id) {
                option.selected = true;
            }
        })

    })
}

async function editUser(id) {
    const response = await fetch(`/api/secure/users/${id}`);
    let user = await response.json();

    editForm.elements["id"].value = user.id
    editForm.elements["username"].value = user.username;
    editForm.elements["email"].value = user.email;
    editForm.elements["age"].value = user.age;
    editForm.elements["lastname"].value = user.lastName;
    editForm.elements["password"].value = user.password;
    await editRoles(user);
    modal.show();

}


async function editSubmit() {
    const selectedOptions = Array.from(document.getElementById('roleEdit').selectedOptions);
    const selectedIds = selectedOptions.map(option => parseInt(option.value));

    const formData = {
        id: editForm.elements["id"].value,
        username: editForm.elements["username"].value,
        email: editForm.elements["email"].value,
        lastName: editForm.elements["lastname"].value,
        password: editForm.elements["password"].value,
        age: editForm.elements["age"].value
    };

    const userRequest = {
        user: formData,
        roleIds: selectedIds
    }

    let jsonResponse;
    try {
        const response = await fetch('api/secure/users', {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userRequest),
        });
        jsonResponse = await response.json();
        if (!jsonResponse) {
            alert("Такое имя пользователя уже сущетвует")
        } else {
            editRow(jsonResponse);
            modal.hide();

        }
    } catch (e) {
        console.log(userRequest)
        console.error(e);
    }
}


editForm.addEventListener("submit", (event) => {
    event.preventDefault();

    editSubmit();
});



