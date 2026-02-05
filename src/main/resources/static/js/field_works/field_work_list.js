document.addEventListener('DOMContentLoaded', function() {

    // Карта соответствия статусов enum -> displayName
    const STATUS_DISPLAY_NAMES = {
        "PLANNED": "Запланировано",
        "IN_PROGRESS": "В процессе",
        "COMPLETED": "Завершено",
        "CANCELLED": "Отменено"
    };

    function getStatusDisplayName(statusKey) {
        return STATUS_DISPLAY_NAMES[statusKey] || statusKey; // Если нет перевода, возвращаем ключ
    }

    const modal = document.getElementById('detailModal');
    const modalBody = document.getElementById('modal-body');
    const closeModalSpan = document.getElementsByClassName('close')[0];

    async function openDetailModal(event) {
        event.preventDefault();

        try {
            const button = event.target;
            const card = button.closest('.card');
            if (!card) {
                console.warn('Не найдена карточка');
                return;
            }

            const workId = card.getAttribute('data-work-id');
            if (!workId) {
                console.warn('ID работы не найден');
                return;
            }

            modal.style.display = 'block';
            modalBody.innerHTML = '<p>Загрузка...</p>';

            const response = await fetch(`/field-works/${workId}`);
            if (!response.ok) throw new Error(`HTTP ${response.status}`);

            const data = await response.json();

            const employees = data.employees.map(e =>
                `${e.surname || ''} ${e.name || ''} ${e.patronymic || ''}`.trim()
            ).filter(s => s).map(s => `<li>${s}</li>`).join('');

            const machineries = data.machineries.map(m =>
                `${m.machineryType || ''} ${m.machineryName || ''} ${m.licensePlate || ''}`.trim()
            ).filter(s => s).map(s => `<li>${s}</li>`).join('');

            const equipment = data.equipment.map(e =>
                `${e.equipmentType || ''} ${e.equipmentName || ''}`.trim()
            ).filter(s => s).map(s => `<li>${s}</li>`).join('');

            modalBody.innerHTML = `
                <h3>${data.workTypeName}</h3>
                <p><strong>ID:</strong> ${data.fieldWorkId}</p>
                <p><strong>Статус:</strong> ${getStatusDisplayName(data.status)}</p>
                <p><strong>Поле:</strong> ${data.fieldNumber}</p>
                <p><strong>Описание:</strong> ${data.description || '—'}</p>
                <p><strong>Начало:</strong> ${formatDateTime(data.startDate)}</p>
                <p><strong>Окончание:</strong> ${formatDateTime(data.endDate)}</p>

                <h4>Сотрудники:</h4>
                <ul>${employees}</ul>
                <h4>Техника:</h4>
                <ul>${machineries}</ul>
                <h4>Оборудование:</h4>
                <ul>${equipment}</ul>
            `;

        } catch (err) {
            console.error('Ошибка:', err);
            modalBody.innerHTML = `<p>Ошибка: ${err.message}</p>`;
        }
    }

    // Функция для отмены работы
    async function cancelFieldWork(event) {
        // event.target указывает на кнопку "Отменить" (с классом cancel-work-btn)
        const button = event.target;
        const card = button.closest('.card');
        if (!card) {
            console.warn('Не найдена карточка для отмены');
            return;
        }

        const workId = card.getAttribute('data-work-id');
        if (!workId) {
            console.warn('Работа не найдена для отмены');
            return;
        }

        // Подтверждение действия (опционально, но рекомендуется)
        if (!confirm(`Вы уверены, что хотите отменить работу?`)) {
            return; // Пользователь отменил действие
        }

        try {
            // Отправляем POST-запрос на /field-works/cancel/{workId}
            const response = await fetch(`/field-works/cancel/${workId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Если используется CSRF, добавьте токен
                    // 'X-CSRF-TOKEN': document.querySelector('[name="_csrf"]').value,
                },
            });

            if (!response.ok) {
                // Если сервер вернул ошибку (4xx, 5xx), получим сообщение об ошибке
                let errorMessage = `HTTP ${response.status}`;
                try {
                    const errorData = await response.json();
                    if (errorData && errorData.error) {
                        errorMessage = errorData.error;
                    } else if (errorData && errorData.message) {
                        errorMessage = errorData.message;
                    }
                } catch (e) {
                    // Если не удалось распарсить JSON ошибки, используем стандартное сообщение
                }
                throw new Error(errorMessage);
            }

            // Если запрос успешен (200 OK), получим сообщение
            const result = await response.json();
            alert(result.message); // Показываем сообщение "Работа успешно отменена"

            // Опционально: обновить страницу или удалить карточку из DOM
            location.reload(); // Простой способ обновить список работ

        } catch (err) {
            console.error('Ошибка при отмене работы:', err);
            alert(`Ошибка: ${err.message}`);
        }
    }

    function formatDateTime(dateStr) {
        if (!dateStr) return '—';
        const d = new Date(dateStr);
        return d.toLocaleString('ru-RU', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' });
    }

    // Делегируем клик по кнопке "Подробнее" (кнопка с классом open-detail-btn)
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('open-detail-btn')) {
            openDetailModal(e);
        }
        // Делегируем клик по кнопке "Отменить" (кнопка с классом cancel-work-btn)
        if (e.target.classList.contains('cancel-work-btn')) {
            cancelFieldWork(e);
        }
    });

    // Закрытие модального окна по кнопке X
    closeModalSpan.onclick = function() {
        modal.style.display = 'none';
    }

    // Закрытие модального окна при клике вне его содержимого
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }

});