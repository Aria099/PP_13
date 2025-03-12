/*
document.addEventListener('DOMContentLoaded', function () {
    const addUserForm = document.getElementById('add-user-form');
    const rolesSelect = document.getElementById('roles');
    const errorDiv = document.getElementById('form-error');

    // Загрузка ролей при загрузке страницы
    fetch('/api/admin/roles')
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.name.substring(5); // Убираем "ROLE_" из названия
                rolesSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке ролей:', error);
            errorDiv.textContent = 'Ошибка при загрузке ролей. Попробуйте снова.';
        });

    // Обработка отправки формы
    addUserForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Отменяем стандартную отправку формы

        const user = {
            username: document.getElementById('username').value,
            lastname: document.getElementById('lastname').value,
            age: document.getElementById('age').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            roles: Array.from(document.getElementById('roles').selectedOptions).map(option => ({
                id: option.value
            }))
        };

        fetch('/api/admin/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/admin'; // Перенаправляем на страницу администратора
                } else {
                    return response.json().then(data => {
                        errorDiv.textContent = data.message || 'Ошибка при добавлении пользователя';
                    });
                }
            })
            .catch(error => {
                console.error('Ошибка при добавлении пользователя:', error);
                errorDiv.textContent = 'Ошибка при добавлении пользователя. Попробуйте снова.';
            });
    });
});

 */