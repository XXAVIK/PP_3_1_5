// async function getUsers() {
//
//     const response = await fetch("api/users");
//     let json = await response.json();
//     return json;
//     // if (response.ok) {
//     //     let json = await response.json()
//     //         .then(data => replaceTable(data));
//     // } else {
//     //     alert("Ошибка HTTP: " + response.status);
//     // }
//     //
//     // function replaceTable(data) {
//     //
//     //     const placement = document.getElementById("testjs")
//     //     placement.innerHTML = "";
//     //     data.forEach(({id, name, surname, username, email, roles}) => {
//     //         let userRoles = "";
//     //         roles.forEach((role) => {
//     //             userRoles = userRoles + role.role.replace("ROLE_", "") + " ";
//     //         })
//     //         const element = document.createElement("tr");
//     //
//     //         element.innerHTML = `
//     //         <th scope="row">${id}</th>
//     //         <td>${name}</td>
//     //         <td>${surname}</td>
//     //         <td>${username}</td>
//     //         <td>${email}</td>
//     //         <td>${userRoles}</td>
//     //
//     //         `
//     //         placement.append(element);
//     //     })
//     // }
// }
// // async function messageBox(){
// //     const response = await fetch("api/users/1");
// //     let data = await response.text();
// //     alert(data);
// //     // alert(users)
// //
// // }
// // Получаем элемент tbody
// const tbody = document.getElementById("myTable").getElementsByTagName("tbody")[0];
// const jsonString = `[
//   {"id":1,"name":"Alice","lastName":"Johnson","email":"alice@example.com","age":30,"roles":["User"]},
//   {"id":2,"name":"Bob","lastName":"Smith","email":"bob@example.com","age":24,"roles":["Admin","User"]},
//   {"id":3,"name":"Charlie","lastName":"Brown","email":"charlie@example.com","age":28,"roles":["Editor","User"]}
// ]`;
//
// const json = getUsers();
// const users = JSON.parse(json);
//
// // Функция для добавления строки в таблицу
// function addRow(user) {
//     const newRow = tbody.insertRow();
//     // json.forEach({id, name, lastName, email, age, roles}){
//     //
//     // }
//     // Создаем и заполняем ячейки
//     newRow.insertCell(0).textContent = user.id;
//     newRow.insertCell(1).textContent = user.name;
//     newRow.insertCell(2).textContent = user.lastName;
//     newRow.insertCell(3).textContent = user.email;
//     newRow.insertCell(4).textContent = user.age;
//     newRow.insertCell(5).textContent = user.roles.join(", "); // Предполагаем, что roles - это массив
//
//     // Добавляем кнопки для редактирования и удаления
//     const editCell = newRow.insertCell(6);
//     const deleteCell = newRow.insertCell(7);
//
//     // Пример кнопки редактирования
//     const editButton = document.createElement("button");
//     editButton.textContent = "Edit";
//     // Добавьте событие onclick для editButton здесь, если нужно
//     editCell.appendChild(editButton);
//
//     // Пример кнопки удаления
//     const deleteButton = document.createElement("button");
//     deleteButton.textContent = "Delete";
//     // Добавьте событие onclick для deleteButton здесь, если нужно
//     deleteCell.appendChild(deleteButton);
// }
//
// // Перебираем пользователей и добавляем их в таблицу
// users.forEach(addRow);