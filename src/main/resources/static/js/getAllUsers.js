
async function fetchAndFillTable() {
    try {
        const response = await fetch('/api/users'); // Выполняем GET запрос
        const userList = await response.json(); // Преобразуем ответ в JSON

        userList.forEach(user => {
            newRow(user);
        });

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

fetchAndFillTable();
