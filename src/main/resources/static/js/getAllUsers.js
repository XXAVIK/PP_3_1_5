
// Функция для выполнения запроса и заполнения таблицы
async function fetchAndFillTable() {
    try {
        const response = await fetch('/api/secure/users'); // Выполняем GET запрос
        const userList = await response.json(); // Преобразуем ответ в JSON

        userList.forEach(user => {
            newRow(user);
        });

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Вызываем функцию для заполнения таблицы при загрузке страницы
fetchAndFillTable();
