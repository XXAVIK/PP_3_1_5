// async function messageBox() {
//     const tbody = document.getElementById('myTable').getElementsByTagName('tbody')[0];
//     const response = await fetch('api/users');
//     let json = await response.json();
//     alert(json);
// }



// Функция для выполнения запроса и заполнения таблицы
async function fetchAndFillTable() {
    try {
        const response = await fetch('/api/secure/users'); // Выполняем GET запрос
        const userList = await response.json(); // Преобразуем ответ в JSON

        const responseRoles = await fetch('/api/secure/roles'); // Выполняем GET запрос
        const roleList = await responseRoles.json();

        const tableBody = document.getElementById('userTableBody');
        const rolesBody = document.getElementById('new-roleSelect')

        // Очищаем таблицу перед добавлением новых данных
        // tableBody.innerHTML = '';

        // Перебираем полученный список пользователей и добавляем их в таблицу
        userList.forEach(user => {
            newRow(user);
        });

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Вызываем функцию для заполнения таблицы при загрузке страницы
fetchAndFillTable();
