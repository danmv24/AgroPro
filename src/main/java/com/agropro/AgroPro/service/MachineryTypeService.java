package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.MachineryTypeView;

import java.util.List;

public interface MachineryTypeService {

    List<MachineryTypeView> getAllMachineryTypes();

    void validateExists(Long machineryTypeId);

}
