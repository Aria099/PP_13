/*
document.addEventListener('DOMContentLoaded', function () {
    const usersTableBody = document.getElementById('users-table-body');
    const newUserForm = document.getElementById('new-user-form');

    loadUsers();

    newUserForm.addEventListener('submit', function (event) {
        event.preventDefault();
        createUser();
    });

    logoutForm.addEventListener('submit', function (event) {
        event.preventDefault();
        logout();
    });

    function loadUsers() {
        fetch('/api/admin/users')
            .then(response => response.json())
            .then(data => {
                renderUsersTable(data.allUsers);
                updateUserInfo(data.user);
            })
            .catch(error => console.error('Error loading users:', error));
    }

    function renderUsersTable(users) {
        usersTableBody.innerHTML = '';
        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(role => role.name).join(', ')}</td>
                <td><button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Edit</button></td>
                <td><button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Delete</button></td>
            `;
            usersTableBody.appendChild(row);
        });
    }

    function createUser() {
        const newUser = {
            firstName: document.getElementById('new-username').value,
            lastName: document.getElementById('new-lastname').value,
            age: document.getElementById('new-age').value,
            email: document.getElementById('new-email').value,
            password: document.getElementById('new-password').value,
            roles: Array.from(document.getElementById('new-roles').selectedOptions).map(option => option.value)
        };

        fetch('/api/admin/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        })
            .then(response => {
                if (response.ok) {
                    loadUsers();
                    newUserForm.reset();
                } else {
                    console.error('Error creating user');
                }
            })
            .catch(error => console.error('Error:', error));
    }

    window.editUser = function (userId) {
        // Реализуйте логику редактирования пользователя
        console.log('Edit user:', userId);
    };

    window.deleteUser = function (userId) {
        if (confirm('Are you sure you want to delete this user?')) {
            fetch(`/api/admin/users/delete?id=${userId}&action=delete`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        loadUsers();
                    } else {
                        console.error('Error deleting user');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    };

    function updateUserInfo(user) {
        document.getElementById('user-email').textContent = user.email;
        document.getElementById('user-roles').textContent = user.roles.map(role => role.name).join(', ');
    }

    function logout() {
        fetch('/logout', { method: 'POST' })
            .then(() => window.location.href = '/auth/login')
            .catch(error => console.error('Error logging out:', error));
    }
});

 */