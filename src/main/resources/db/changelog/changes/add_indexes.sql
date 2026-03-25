CREATE INDEX idx_employees_position ON employees(position);
CREATE INDEX idx_we_employee_id ON work_employees(employee_id);
CREATE INDEX idx_we_work_id ON work_employees(work_id);

CREATE INDEX idx_equipment_purchase_date ON equipment(purchase_date);
CREATE INDEX idx_esh_equipment_status_changed ON equipment_status_history(equipment_id, changed_at);
CREATE INDEX idx_weq_work_id ON work_equipment(work_id);
CREATE INDEX idx_we_equipment_id ON work_equipment(equipment_id);

CREATE INDEX idx_machineries_purchase_date ON machineries(purchase_date);
CREATE INDEX idx_msh_machinery_status_changed ON machinery_status_history(machinery_id, changed_at);
CREATE INDEX idx_wm_work_id ON work_machineries(work_id);
CREATE INDEX idx_wm_machinery_id ON work_machineries(machinery_id);

CREATE INDEX idx_fp_field_dates ON field_plantings(field_id, planting_date, harvest_date);
CREATE INDEX idx_fp_planting_crop ON field_plantings(planting_date, crop_type);

CREATE INDEX idx_works_status_start_end_dates ON works(status, start_date, end_date);
CREATE INDEX idx_wr_work_id ON work_results(work_id);

CREATE INDEX idx_ec_codes ON expense_categories(code);
CREATE INDEX idx_expenses_category_id_expense_date ON expenses(category_id, expense_date);

