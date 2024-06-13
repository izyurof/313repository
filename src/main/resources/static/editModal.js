document.getElementById('editModal').addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const userId = button.getAttribute('data-user-id');
    const url = `http://localhost:8080/api/admin/users/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            document.getElementById('id').value = data.id;
            document.getElementById('username').value = data.name;
            document.getElementById('lastname').value = data.lastName;
            document.getElementById('age').value = data.age;
            document.getElementById('email').value = data.email;
            document.getElementById('password').value = null;
            populateRolesSelect()
        })

    document.getElementById('editForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);

        const data = Object.fromEntries(formData.entries());

        const roles = getSelectedRoles();
        data.roles = roles;

        const id = document.getElementById('id').value; // Используйте значение из скрытого поля
        data.id = id
        const url = `http://localhost:8080/api/admin/users/${id}`;

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(() => {
                document.getElementById('editModal')
                const modalElement = document.getElementById('editModal');
                const modalInstance = bootstrap.Modal.getInstance(modalElement);
                modalInstance.hide();
                getUsersInfo();
            })
    });
});


function populateRolesSelect() {
    const url = 'http://localhost:8080/api/admin/roles';
    fetch(url)
        .then(response => response.json())
        .then(roles => {
            const selectElement = document.getElementById('role');
            selectElement.innerHTML = '';
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.text = role.role.replace('ROLE_', '');
                selectElement.appendChild(option);
            });
        })
}

function getSelectedRoles() {
    const rolesSelect = document.getElementById('role');
    const selectedRoles = Array.from(rolesSelect.selectedOptions).map(option => {
        return {
            id: option.value,
            role: 'ROLE_' + option.text
        };
    });
    return selectedRoles;
}
