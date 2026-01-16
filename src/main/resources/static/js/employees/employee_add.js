document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('employeeForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();


        const formData = {
            surname: form.elements['surname']?.value || '',
            name: form.elements['name']?.value || '',
            patronymic: form.elements['patronymic']?.value || '',
            position: form.elements['position']?.value || '',
            paymentType: form.elements['paymentType']?.value || '',
            salary: form.elements['salary']?.value ? parseFloat(form.elements['salary'].value) : null,
            hireDate: form.elements['hireDate']?.value || ''
        };


        if (!formData.surname.trim() || !formData.name.trim() ||
            !formData.salary || !formData.hireDate) {
            showNotification('Заполните все обязательные поля', 'error');
            return;
        }

        const submitBtn = form.querySelector('.submit-btn');
        const originalText = submitBtn.textContent;
        submitBtn.disabled = true;
        submitBtn.textContent = 'Отправка';

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            const result = await response.json();

            if (response.ok && result.status === 'success') {
                showNotification('Сотрудник успешно добавлен!', 'success');
                form.reset();
            } else {
                const message = result.message || 'Не удалось добавить сотрудника';
                showNotification(message, 'error');
            }
        } catch (error) {
            console.error('AJAX error:', error);
            showNotification('Ошибка сети. Проверьте подключение.', 'error');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
        }
    });
});

function showNotification(message, type) {
    let notif = document.getElementById('form-notification');
    if (!notif) {
        notif = document.createElement('div');
        notif.id = 'form-notification';
        notif.className = 'notification';
        const form = document.getElementById('employeeForm');
        form.parentNode.insertBefore(notif, form);
    }

    notif.textContent = message;
    notif.className = `notification ${type}`;

    setTimeout(() => notif.classList.add('show'), 10);

    setTimeout(() => {
        notif.classList.remove('show');
        setTimeout(() => {
            if (notif.parentNode) {
                notif.parentNode.removeChild(notif);
            }
        }, 300);
    }, 5000);
}