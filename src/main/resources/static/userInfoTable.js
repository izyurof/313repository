async function getUserInfo(endpoint, tableId) {
    const table = document.getElementById(tableId);
    fetch(endpoint)
        .then(res => res.json())
        .then(user => getUserTable(user, table));
}

function getUserTable(user, table) {
    let rolesString = getRolesString(user.roles);
    let row = `<tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${rolesString}</td>
        </tr>`;
    table.innerHTML += row;
}

function getRolesString(roles) {
    return roles.map(role => role.role.replaceAll('ROLE_', '')).join(' ');
}

// Вызов функции для пользователей
getUserInfo('http://localhost:8080/api/user/info', 'userTable');

// Вызов функции для администраторов
getUserInfo('http://localhost:8080/api/admin/info', 'adminInfoTable');
