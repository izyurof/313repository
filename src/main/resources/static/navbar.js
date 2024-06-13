async function getNavInfo() {
    fetch('http://localhost:8080/api/user/info')
        .then(res => res.json())
        .then(user => getInfo(user))
}

function getInfo(user) {
    document.getElementById('userEmail').textContent = user.email;
    document.getElementById('userRoles').textContent = getRolesString(user.roles);
}

getNavInfo()

