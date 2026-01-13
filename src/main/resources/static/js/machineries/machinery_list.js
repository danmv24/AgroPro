document.addEventListener('DOMContentLoaded', function() {
    applyMachineryCardStyling();
    setMachineryIcons();
});

function setMachineryIcons() {
    const iconMap = {
        'Трактор': 'icon-tractor',
        'Комбайн': 'icon-combine',
        'Грузовик': 'icon-truck',
        'Легковой транспорт': 'icon-transport',
        'Погрузчик': 'icon-forklift'
    };

    const icons = document.querySelectorAll('.machinery-icon');
    icons.forEach(icon => {
        const card = icon.closest('.machinery-card');
        if (card) {
            const typeSpan = card.querySelector('.card-body p:nth-child(1) span');
            if (typeSpan) {
                const machineryType = typeSpan.textContent.trim();
                const iconClass = getIconClassForMachineryType(machineryType);
                if (iconClass) {
                    icon.classList.add(iconClass);
                }
            }
        }
    });
}

function getIconClassForMachineryType(machineryTypeName) {
    const iconMap = {
        'Трактор': 'icon-tractor',
        'Комбайн': 'icon-combine',
        'Грузовик': 'icon-truck',
        'Легковой транспорт': 'icon-transport',
        'Погрузчик': 'icon-forklift'
    };

    return iconMap[machineryTypeName] || 'icon-default';
}

function applyMachineryCardStyling() {
    const statusClassMap = {
        'в-работе': 'status-bg-in-operation',
        'на-ремонте': 'status-bg-under-repair',
        'простой': 'status-bg-idle',
        'списана': 'status-bg-decommissioned',
    };

    const cards = document.querySelectorAll('.machinery-card');
    cards.forEach(card => {
        const statusName = card.getAttribute('data-status');
        if (statusName) {
            const className = statusClassMap[statusName];
            if (className) {
                card.classList.add(className);
            }
        }
    });
}