
document.addEventListener('DOMContentLoaded', function () {

    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('id');

    if (!userId) {
        console.error('User ID is missing in the URL');
        return;
    }

    fetch(`/api/admin/users/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('User not found');
            }
            return response.json();
        })
        .then(user => {

            document.getElementById('user-email').textContent = user.email;
            document.getElementById('user-roles').textContent = user.roles.map(role => role.name.substring(5)).join(', ');

            // Заполняем таблицу
            const tbody = document.getElementById('user-table-body');
            tbody.innerHTML = `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.lastname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => role.name.substring(5)).join(', ')}</td>
                </tr>
            `;
        })
        .catch(error => {
            console.error('Ошибка при загрузке данных пользователя:', error);
            document.getElementById('user-table-body').innerHTML = `
                <tr>
                    <td colspan="6" style="color: red; text-align: center;">Ошибка загрузки данных.</td>
                </tr>
            `;
        });

    document.getElementById('logout-form').addEventListener('submit', function (event) {
        event.preventDefault();
        fetch('/logout', { method: 'POST' })
            .then(() => window.location.href = '/auth/login')
            .catch(error => console.error('Ошибка при выходе:', error));
    });
});