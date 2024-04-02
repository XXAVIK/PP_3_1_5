const userInfoTableBody = document.getElementById('userInfoTableBody');
async function showLoggedInfo() {
    try {
        const response = await fetch('/api/users/logged');
        const user = await response.json();

        userInfo(user);

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function userInfo(user) {

    const row = userInfoTableBody.insertRow();
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
      `;

}

showLoggedInfo();