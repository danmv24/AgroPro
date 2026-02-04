document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('fieldWorkForm');
    const notificationArea = document.getElementById('notification-area');

    const employeeTrigger = document.getElementById('employee-dropdown-trigger');
    const employeeMenu = document.getElementById('employee-dropdown-menu');
    const employeeSearch = document.getElementById('employee-search');
    const employeeCheckboxList = document.getElementById('employee-checkbox-list');
    const confirmEmployeesBtn = document.getElementById('confirm-employees-btn');
    const selectedEmployeesDisplay = document.getElementById('selected-employees-display');
    const employeeErrorDiv = document.getElementById('employee-error');

    const machineryTrigger = document.getElementById('machinery-dropdown-trigger');
    const machineryMenu = document.getElementById('machinery-dropdown-menu');
    const machinerySearch = document.getElementById('machinery-search');
    const machineryCheckboxList = document.getElementById('machinery-checkbox-list');
    const confirmMachineryBtn = document.getElementById('confirm-machinery-btn');
    const selectedMachineriesDisplay = document.getElementById('selected-machineries-display');

    const equipmentTrigger = document.getElementById('equipment-dropdown-trigger');
    const equipmentMenu = document.getElementById('equipment-dropdown-menu');
    const equipmentSearch = document.getElementById('equipment-search');
    const equipmentCheckboxList = document.getElementById('equipment-checkbox-list');
    const confirmEquipmentBtn = document.getElementById('confirm-equipment-btn');
    const selectedEquipmentDisplay = document.getElementById('selected-equipment-display');

    let selectedEmployeeIds = new Set();
    let selectedMachineryIds = new Set();
    let selectedEquipmentIds = new Set();

    function toggleDropdown(menuElement, triggerElement, otherMenus) {
        otherMenus.forEach(menu => {
            if (menu !== menuElement) {
                menu.style.display = 'none';
            }
        });

        if (menuElement.style.display === 'block') {
            menuElement.style.display = 'none';
        } else {
            menuElement.style.display = 'block';

            const searchInput = menuElement.querySelector('.dropdown-search');
            if (searchInput) {
                searchInput.value = '';
                applyFilter(searchInput, menuElement.querySelector('.checkbox-list'));
            }
        }
    }

    function applyFilter(searchInput, checkboxList) {
        const searchTerm = searchInput.value.toLowerCase().trim();
        const items = checkboxList.querySelectorAll('.checkbox-item');

        items.forEach(item => {
            const label = item.querySelector('label');
            const labelText = label.textContent.toLowerCase();
            if (labelText.includes(searchTerm)) {
                item.style.display = '';
            } else {
                item.style.display = 'none';
            }
        });
    }

    function updateDisplay(container, selectedIds, allItems) {
        container.innerHTML = '';
        if (selectedIds.size === 0) {
            container.textContent = 'Ничего не выбрано';
            return;
        }

        selectedIds.forEach(id => {
            const itemLabel = allItems.find(item => item.id === id)?.label || `ID: ${id}`;
            const tag = document.createElement('span');
            tag.className = 'selected-item-tag';
            tag.innerHTML = `${itemLabel} <span class="remove-item-btn" data-id="${id}">&times;</span>`;
            container.appendChild(tag);
        });
    }

    function updateTriggerText(triggerElement, count, singular, plural, emptyText) {
        if (count === 0) {
            triggerElement.placeholder = emptyText;
        } else if (count === 1) {
            triggerElement.placeholder = `${count} ${singular} выбран`;
        } else {
            triggerElement.placeholder = `${count} ${plural} выбрано`;
        }
    }

    function validateEmployees() {
        if (selectedEmployeeIds.size === 0) {
            employeeErrorDiv.style.display = 'block';
            return false;
        } else {
            employeeErrorDiv.style.display = 'none';
            return true;
        }
    }

    employeeTrigger.addEventListener('click', () => toggleDropdown(employeeMenu, employeeTrigger, [machineryMenu, equipmentMenu]));
    employeeSearch.addEventListener('input', () => applyFilter(employeeSearch, employeeCheckboxList));

    confirmEmployeesBtn.addEventListener('click', () => {
        const checkedBoxes = employeeCheckboxList.querySelectorAll('.employee-checkbox:checked');
        checkedBoxes.forEach(cb => {
            selectedEmployeeIds.add(parseInt(cb.value));
        });
        employeeMenu.style.display = 'none';
        updateDisplay(selectedEmployeesDisplay, selectedEmployeeIds, Array.from(employeeCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
            id: parseInt(item.querySelector('input').value),
            label: item.querySelector('label').textContent
        })));
        updateTriggerText(employeeTrigger, selectedEmployeeIds.size, 'сотрудник', 'сотрудников', 'Выберите сотрудников');
        validateEmployees();
    });

    selectedEmployeesDisplay.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-item-btn')) {
            const id = parseInt(e.target.getAttribute('data-id'));
            selectedEmployeeIds.delete(id);
            updateDisplay(selectedEmployeesDisplay, selectedEmployeeIds, Array.from(employeeCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
                id: parseInt(item.querySelector('input').value),
                label: item.querySelector('label').textContent
            })));
            updateTriggerText(employeeTrigger, selectedEmployeeIds.size, 'сотрудник', 'сотрудников', 'Выберите сотрудников');
            validateEmployees();
        }
    });

    machineryTrigger.addEventListener('click', () => toggleDropdown(machineryMenu, machineryTrigger, [employeeMenu, equipmentMenu]));
    machinerySearch.addEventListener('input', () => applyFilter(machinerySearch, machineryCheckboxList));

    confirmMachineryBtn.addEventListener('click', () => {
        const checkedBoxes = machineryCheckboxList.querySelectorAll('.resource-checkbox:checked');
        checkedBoxes.forEach(cb => {
            selectedMachineryIds.add(parseInt(cb.value));
        });
        machineryMenu.style.display = 'none';
        updateDisplay(selectedMachineriesDisplay, selectedMachineryIds, Array.from(machineryCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
            id: parseInt(item.querySelector('input').value),
            label: item.querySelector('label').textContent
        })));
        updateTriggerText(machineryTrigger, selectedMachineryIds.size, 'машина', 'машины', 'Выберите технику');
    });

    selectedMachineriesDisplay.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-item-btn')) {
            const id = parseInt(e.target.getAttribute('data-id'));
            selectedMachineryIds.delete(id);
            updateDisplay(selectedMachineriesDisplay, selectedMachineryIds, Array.from(machineryCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
                id: parseInt(item.querySelector('input').value),
                label: item.querySelector('label').textContent
            })));
            updateTriggerText(machineryTrigger, selectedMachineryIds.size, 'машина', 'машины', 'Выберите технику');
        }
    });

    equipmentTrigger.addEventListener('click', () => toggleDropdown(equipmentMenu, equipmentTrigger, [employeeMenu, machineryMenu]));
    equipmentSearch.addEventListener('input', () => applyFilter(equipmentSearch, equipmentCheckboxList));

    confirmEquipmentBtn.addEventListener('click', () => {
        const checkedBoxes = equipmentCheckboxList.querySelectorAll('.resource-checkbox:checked');
        checkedBoxes.forEach(cb => {
            selectedEquipmentIds.add(parseInt(cb.value));
        });
        equipmentMenu.style.display = 'none';
        updateDisplay(selectedEquipmentDisplay, selectedEquipmentIds, Array.from(equipmentCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
            id: parseInt(item.querySelector('input').value),
            label: item.querySelector('label').textContent
        })));
        updateTriggerText(equipmentTrigger, selectedEquipmentIds.size, 'оборудование', 'оборудования', 'Выберите оборудование');
    });

    selectedEquipmentDisplay.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-item-btn')) {
            const id = parseInt(e.target.getAttribute('data-id'));
            selectedEquipmentIds.delete(id);
            updateDisplay(selectedEquipmentDisplay, selectedEquipmentIds, Array.from(equipmentCheckboxList.querySelectorAll('.checkbox-item')).map(item => ({
                id: parseInt(item.querySelector('input').value),
                label: item.querySelector('label').textContent
            })));
            updateTriggerText(equipmentTrigger, selectedEquipmentIds.size, 'оборудование', 'оборудования', 'Выберите оборудование');
        }
    });

    document.addEventListener('click', function(event) {
        if (!employeeMenu.contains(event.target) && !employeeTrigger.contains(event.target)) {
            employeeMenu.style.display = 'none';
        }
        if (!machineryMenu.contains(event.target) && !machineryTrigger.contains(event.target)) {
            machineryMenu.style.display = 'none';
        }
        if (!equipmentMenu.contains(event.target) && !equipmentTrigger.contains(event.target)) {
            equipmentMenu.style.display = 'none';
        }
    });

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        if (!validateEmployees()) {
            showNotification('Выберите хотя бы одного сотрудника', 'error');
            return;
        }

        const formDataObject = {
            workTypeId: parseInt(document.getElementById('workTypeId').value) || null,
            fieldId: parseInt(document.getElementById('fieldId').value) || null,
            startDate: document.getElementById('startDate').value,
            endDate: document.getElementById('endDate').value,
            description: document.getElementById('description').value.trim() || null,
            employeeIds: Array.from(selectedEmployeeIds),
            machineryIds: Array.from(selectedMachineryIds),
            equipmentIds: Array.from(selectedEquipmentIds)
        };

        fetch('/field-works/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify(formDataObject)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.status === 'success') {
                    showNotification(data.message, 'success');

                    form.reset();
                    selectedEmployeeIds.clear();
                    selectedMachineryIds.clear();
                    selectedEquipmentIds.clear();
                    updateDisplay(selectedEmployeesDisplay, selectedEmployeeIds, []);
                    updateDisplay(selectedMachineriesDisplay, selectedMachineryIds, []);
                    updateDisplay(selectedEquipmentDisplay, selectedEquipmentIds, []);
                    employeeTrigger.placeholder = 'Выберите сотрудников';
                    machineryTrigger.placeholder = 'Выберите технику';
                    equipmentTrigger.placeholder = 'Выберите оборудование';
                    employeeErrorDiv.style.display = 'none';
                } else if (data.status === 'error') {
                    showNotification(data.message, 'error');
                } else {
                    showNotification('Неизвестный ответ от сервера', 'error');
                }
            })
            .catch(error => {
                console.error('Ошибка при отправке формы:', error);
                showNotification('Произошла ошибка при отправке запроса', 'error');
            });
    });

    function showNotification(message, type) {
        notificationArea.innerHTML = '';
        const notificationDiv = document.createElement('div');
        notificationDiv.className = `notification ${type}`;
        notificationDiv.textContent = message;
        notificationArea.appendChild(notificationDiv);
        notificationDiv.style.display = 'block';

        setTimeout(() => {
            if (notificationDiv.parentNode) {
                notificationDiv.parentNode.removeChild(notificationDiv);
            }
        }, 5000);
    }
});