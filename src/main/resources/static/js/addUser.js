const form = document.querySelector("#new-user");
const rolesBody = document.getElementById('new-roleSelect')

async function selectExistingRoles() {
    const responseRoles = await fetch('/api/users/roles'); // Выполняем GET запрос
    const roleList = await responseRoles.json();
    const selectList = document.getElementById("roleSelect");

    roleList.forEach(role => {
        const option = document.createElement("option")
        option.value = role.id;
        option.text = role.name;
        selectList.appendChild(option);

    })
}

selectExistingRoles();

async function newUser() {

    const selectedOptions = Array.from(document.getElementById('roleSelect').selectedOptions);
    const selectedIds = selectedOptions.map(option => parseInt(option.value));

    const formData = {
        username: document.querySelector('#username').value,
        lastName: document.querySelector('#lastnameInput').value,
        age: document.querySelector('#age').value,
        email: document.querySelector('#email').value,
        password: document.querySelector('#password').value,
    };

    const userRequest = {
        user: formData,
        roleIds: selectedIds
    }

    let jsonResponse;

    try {
        const response = await fetch('api/users', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userRequest),
        });
        jsonResponse = await response.json();
        if (!jsonResponse) {
            alert("Такое имя пользователя уже сущетвует")
        } else {
            newRow(jsonResponse);
            form.reset()
        }
    } catch (e) {
        console.log(userRequest)
        console.error(e);
    }
}


form.addEventListener("submit", (event) => {
    event.preventDefault();

    newUser();
});