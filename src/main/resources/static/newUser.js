document.addEventListener('DOMContentLoaded', function() {
    fetch('http://localhost:8080/api/admin/roles')
        .then(response => response.json())
        .then(roles => {
            const roleSelect = document.getElementById('role2');
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.role;
                roleSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching roles:', error));

    document.getElementById('newUser').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const user = Object.fromEntries(formData.entries());

        // Преобразование ролей в массив, так как они могут быть выбраны множественными
        user.roles = getSelectedRoles2();

        fetch('http://localhost:8080/api/admin/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }).then(r => r.json())
            .then(() => {
                const allUsersTab = document.getElementById('nav-users-tab');
                const allUsersPane = document.getElementById('nav-users');
                const newUserTab = document.getElementById('nav-new-user-tab');
                const newUserPane = document.getElementById('nav-new-user');
                allUsersTab.classList.remove('active');
                allUsersPane.classList.remove('show', 'active');
                newUserTab.classList.remove('active');
                newUserPane.classList.remove('show', 'active');
                allUsersTab.classList.add('active');
                allUsersPane.classList.add('show', 'active');
                getUsersInfo();
            });
    });
});

function getSelectedRoles2() {
    const rolesSelect = document.getElementById('role2');
    const selectedRoles = Array.from(rolesSelect.selectedOptions).map(option => {
        return {
            id: option.value,
            role: 'ROLE_' + option.text
        };
    });
    return selectedRoles;
}

