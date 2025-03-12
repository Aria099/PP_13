document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/admin/users')
        .then(response => response.json())
        .then(users => {
            const userList = document.getElementById('user-list');
            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.lastname}</td>
                    <td>${user.age}</td>
                    <td>${user.roles.map(role => role.name).join(', ')}</td>
                    <td><button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Edit</button></td>
                    <td><button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Delete</button></td>
                `;
                userList.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка:', error));
});