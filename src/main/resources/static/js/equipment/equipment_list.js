document.addEventListener('DOMContentLoaded', function() {
    applyequipmentCardStyling();
    setEquipmentIcons();
});

function setEquipmentIcons() {
    const icons = document.querySelectorAll('.equipment-icon');
    icons.forEach(icon => {
        const card = icon.closest('.equipment-card');
        if (card) {
            const typeSpan = card.querySelector('.card-body p:nth-child(1) span');
            if (typeSpan) {
                const equipmentType = typeSpan.textContent.trim();
                const iconClass = getIconClassForEquipmentType(equipmentType);
                if (iconClass) {
                    icon.classList.add(iconClass);
                }
            }
        }
    });
}

function getIconClassForEquipmentType(equipmentTypeName) {
    const iconMap = {
        'Культиватор': 'icon-cultivator',
        'Дискатор': 'icon-diskator',
        'Прицеп': 'icon-trailer',
        'Сеялка': 'icon-seeder',
        'Косилка': 'icon-mower',
        'Распределитель удобрений': 'icon-fertilizer-spreader',
        'Плуг': 'icon-plow',
        'Опрыскиватель': 'icon-sprayer'
    };

    return iconMap[equipmentTypeName] || 'icon-default';
}

function applyequipmentCardStyling() {
    const statusClassMap = {
        'в-работе': 'status-bg-in-operation',
        'на-ремонте': 'status-bg-under-repair',
        'простой': 'status-bg-idle',
        'списана': 'status-bg-decommissioned',
    };

    const cards = document.querySelectorAll('.equipment-card');
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