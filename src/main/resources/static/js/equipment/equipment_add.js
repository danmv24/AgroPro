document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('equipmentForm');
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notification-text');

    function clearErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(el => el.textContent = '');
    }

    function showValidationErrors(errors) {
        Object.keys(errors).forEach(fieldName => {
            const errorElement = document.getElementById(`${fieldName}-error`);
            if (errorElement) {
                errorElement.textContent = errors[fieldName];
            }
        });
    }

    function showNotification(message, isSuccess = true) {
        notificationText.textContent = message;
        notification.className = 'notification';
        notification.classList.add(isSuccess ? 'success' : 'error');
        notification.style.display = 'block';
        setTimeout(hideNotification, 5000);
    }

    function hideNotification() {
        notification.style.display = 'none';
    }

    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        clearErrors();

        const formData = {
            equipmentName: document.getElementById('equipmentName').value.trim(),
            equipmentTypeId: parseInt(document.getElementById('equipmentTypeId').value, 10),
            inventoryNumber: parseInt(document.getElementById('inventoryNumber').value, 10),
            purchaseDate: form.elements['purchaseDate']?.value || '',
        };

        if (isNaN(formData.equipmentTypeId) || isNaN(formData.inventoryNumber)) {
            showNotification('Ошибка: Некорректные числовые значения.', false);
            return;
        }

        try {
            const response = await fetch('/equipment/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            const result = await response.json();

            if (response.ok && result.status === 'success') {
                showNotification(result.message || 'Техника успешно добавлена!', true);
                form.reset();
                clearErrors();
            } else {
                if (result.errors) {
                    showValidationErrors(result.errors);
                } else {
                    showNotification(result.message || 'Произошла ошибка при добавлении техники.', false);
                }
            }
        } catch (error) {
            console.error('Ошибка при отправке формы:', error);
            showNotification('Произошла сетевая ошибка. Пожалуйста, попробуйте снова.', false);
        }
    });
});