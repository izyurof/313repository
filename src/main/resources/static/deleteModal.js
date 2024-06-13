document.getElementById('deleteModal').addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const IdUser = button.getAttribute('data-user-id');
    const url = `http://localhost:8080/api/admin/users/${IdUser}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            document.getElementById('id1').value = data.id;
            document.getElementById('username1').value = data.name;
            document.getElementById('lastname1').value = data.lastName;
            document.getElementById('age1').value = data.age;
            document.getElementById('email1').value = data.email;
            populateRolesSelect1()
        })

    document.getElementById('deleteForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData.entries());
        const roles = getSelectedRoles1();
        data.roles = roles;

        const id = document.getElementById('id1').value; // Используйте значение из скрытого поля
        data.id = id
        const url = `http://localhost:8080/api/admin/users/${id}`;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(() => {
                document.getElementById('deleteModal')
                const modalElement = document.getElementById('deleteModal');
                const modalInstance = bootstrap.Modal.getInstance(modalElement);
                modalInstance.hide();
                getUsersInfo();
            })

    });
});

function populateRolesSelect1() {
    const url = 'http://localhost:8080/api/admin/roles';
    fetch(url)
        .then(response => response.json())
        .then(roles => {
            console.log(roles)
            const selectElement = document.getElementById('role1');
            selectElement.innerHTML = '';
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.text = role.role.replace('ROLE_', '');
                selectElement.appendChild(option);
            });
        })
}

function getSelectedRoles1() {
    const rolesSelect = document.getElementById('role1');
    const selectedRoles = Array.from(rolesSelect.selectedOptions).map(option => {
        return {
            id: option.value,
            role: 'ROLE_' + option.text
        };
    });
    return selectedRoles;
}