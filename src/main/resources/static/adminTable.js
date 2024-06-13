const table = document.getElementById('adminTable');

async function getUsersInfo() {
    fetch('http://localhost:8080/api/admin/users')
        .then(res => res.json())
        .then(users => {
            table.innerHTML = '';
            getAdminTable(users)
        })
}

function getAdminTable(users) {
    users.forEach(user => {
        let rolesString = getRolesString(user.roles);
        let row = `<tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${rolesString}</td>
            <td><button class="btn btn-info" data-user-id="${user.id}" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button></td>
            <td><button class="btn btn-danger" data-user-id="${user.id}" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button></td>
        </tr>`;
        table.innerHTML += row;
    });
}

function getRolesString(roles) {
    return roles.map(role => role.role.replaceAll('ROLE_', '')).join(' ');
}

getUsersInfo()