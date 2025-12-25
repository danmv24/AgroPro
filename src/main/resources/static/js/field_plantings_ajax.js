document.addEventListener('DOMContentLoaded', function() {
    loadFieldsForYear(document.getElementById('year').value);
});

function loadFieldsForYear(year) {
    const cardsContainer = document.getElementById('cards-container');
    const loadingIndicator = document.getElementById('loading');

    loadingIndicator.style.display = 'block';
    cardsContainer.innerHTML = '';

    fetch(`/fields/data-json?year=${year}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Сетевая ошибка: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            const html = generateCardsHTML(data);
            cardsContainer.innerHTML = html;
            applyFieldCardStyling();
        })
        .catch(error => {
            console.error('Ошибка при загрузке данных:', error);
            cardsContainer.innerHTML = '<p>Ошибка загрузки данных.</p>';
        })
        .finally(() => {
            loadingIndicator.style.display = 'none';
        });
}

function generateCardsHTML(fields) {
    if (!fields || fields.length === 0) {
        return '<p>Нет данных для отображения.</p>';
    }

    return fields.map(field => {
        const cropName = field.cropName || 'free';
        const cropDisplay = field.cropName || '-';
        const cropClass = cropName.toLowerCase().replace(/\s+/g, '-');
        const iconClass = getIconClassForCrop(cropName);

        return `
        <div class="card" data-crop="${cropClass}">
            <div class="card-header">
                <h3>Поле <span>${field.fieldNumber}</span></h3>
                <span class="crop-icon ${iconClass}"></span>
            </div>
            <div class="card-body">
                <p>Площадь: <span>${field.area} га</span></p>
                <p>Культура: <span>${cropDisplay}</span></p>
            </div>
            <div class="card-actions">
                <a href="#" onclick="openFieldPlantingsModal(${field.fieldId})" class="btn-secondary">Посевы</a>
                <a href="/field-works/by-field/${field.fieldId}" class="btn-primary">Работы</a>
            </div>
        </div>
        `;
    }).join('');
}

function getIconClassForCrop(cropName) {
    const iconMap = {
        'пшеница-озимая': 'icon-wheat-winter',
        'пшеница-яровая': 'icon-wheat-spring',
        'ячмень-яровой': 'icon-barley',
        'подсолнечник': 'icon-sunflower',
        'соя': 'icon-soya',
        'чистый-пар': 'icon-fallow',
        'яблоки': 'icon-apple'
    };

    const normalizedCropName = cropName.toLowerCase().replace(/\s+/g, '-');
    return iconMap[normalizedCropName] || 'icon-default';
}

function applyFieldCardStyling() {
    const cropClassMap = {
        'пшеница-озимая': 'crop-bg-wheat-winter',
        'пшеница-яровая': 'crop-bg-wheat-spring',
        'ячмень-яровой': 'crop-bg-barley',
        'подсолнечник': 'crop-bg-sunflower',
        'соя': 'crop-bg-soya',
        'чистый-пар': 'crop-bg-fallow',
        'яблоки': 'crop-bg-apple',
        'free': 'crop-bg-free'
    };

    const cards = document.querySelectorAll('.card');
    cards.forEach(card => {
        const cropName = card.getAttribute('data-crop');
        if (cropName) {
            const className = cropClassMap[cropName];
            if (className) {
                card.classList.add(className);
            }
        }
    });
}


function openFieldPlantingsModal(fieldId) {
    const modal = document.getElementById('fieldPlantingsModal');
    const modalTitle = document.getElementById('modalTitle');
    const modalContent = document.getElementById('modalContent');

    modal.style.display = 'block';

    modalTitle.textContent = `Посевы на поле ${fieldId}`;

    fetch(`/field-plantings/by-field/${fieldId}/data`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            // Генерируем HTML для посевов
            let html = '';
            if (data && data.length > 0) {
                html = data.map(planting => `
                    <div class="planting-item">
                        <span class="year">${planting.plantingYear}:</span>
                        <span class="crop">${planting.cropName}</span>
                    </div>
                `).join('');
            } else {
                html = '<p>На этом поле ещё не было посевов.</p>';
            }

            modalContent.innerHTML = html;
        })
        .catch(error => {
            console.error('Ошибка при загрузке посевов:', error);
            modalContent.innerHTML = '<p>Ошибка загрузки данных.</p>';
        });
}

function closeFieldPlantingsModal() {
    const modal = document.getElementById('fieldPlantingsModal');
    modal.style.display = 'none';
}

document.querySelector('.close').addEventListener('click', closeFieldPlantingsModal);

window.addEventListener('click', function(event) {
    const modal = document.getElementById('fieldPlantingsModal');
    if (event.target === modal) {
        closeFieldPlantingsModal();
    }
});