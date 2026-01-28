package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

import java.util.List;

public interface MachineryService {

    void addMachinery(MachineryForm machineryForm);

    List<MachineryView> getMachineries();

    List<MachineryBasicInfoView> getIdleMachineries();


}
