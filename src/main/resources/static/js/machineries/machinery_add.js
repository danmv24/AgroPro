// machinery_add.js
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('machineryForm');
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notification-text');

    // Функция для очистки сообщений об ошибках
    function clearErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(el => el.textContent = '');
    }

    // Функция для отображения ошибок валидации
    function showValidationErrors(errors) {
        Object.keys(errors).forEach(fieldName => {
            const errorElement = document.getElementById(`${fieldName}-error`);
            if (errorElement) {
                errorElement.textContent = errors[fieldName];
            }
        });
    }

    // Функция для отображения уведомления
    function showNotification(message, isSuccess = true) {
        notificationText.textContent = message;
        // Сбрасываем классы и добавляем нужные
        notification.className = 'notification'; // Сбрасывает все классы
        notification.classList.add(isSuccess ? 'success' : 'error');
        notification.style.display = 'block'; // Показываем уведомление
        // Скрываем через 5 секунд
        setTimeout(hideNotification, 5000);
    }

    // Функция для скрытия уведомления
    function hideNotification() {
        notification.style.display = 'none'; // Скрываем уведомление
    }

    form.addEventListener('submit', async function (event) {
        event.preventDefault(); // Предотвращаем стандартную отправку формы

        // Очищаем предыдущие ошибки
        clearErrors();

        // Собираем данные формы НАПРЯМУЮ из DOM, игнорируя th:field
        const formData = {
            machineryName: document.getElementById('machineryName').value.trim(),
            machineryTypeId: parseInt(document.getElementById('machineryTypeId').value, 10),
            inventoryNumber: parseInt(document.getElementById('inventoryNumber').value, 10),
            licensePlate: document.getElementById('licensePlate').value.trim() || null // Преобразуем пустую строку в null
        };

        // Проверяем, что числовые поля не NaN
        if (isNaN(formData.machineryTypeId) || isNaN(formData.inventoryNumber)) {
            showNotification('Ошибка: Некорректные числовые значения.', false);
            return; // Прерываем выполнение, если числа некорректны
        }

        try {
            const response = await fetch('/machineries/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            const result = await response.json();

            if (response.ok && result.status === 'success') {
                // Показываем уведомление об успехе
                showNotification(result.message || 'Техника успешно добавлена!', true);

                // Очищаем форму
                form.reset();
                // Очищаем возможные скрытые ошибки (необязательно, reset() очищает поля)
                clearErrors();
            } else {
                // Обработка ошибок валидации или других ошибок от сервера
                if (result.errors) {
                    // Если сервер возвращает структуру с ошибками валидации
                    showValidationErrors(result.errors);
                } else {
                    // Если сервер возвращает общее сообщение об ошибке
                    showNotification(result.message || 'Произошла ошибка при добавлении техники.', false);
                }
            }
        } catch (error) {
            console.error('Ошибка при отправке формы:', error);
            showNotification('Произошла сетевая ошибка. Пожалуйста, попробуйте снова.', false);
        }
    });
});