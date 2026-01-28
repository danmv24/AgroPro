package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

import java.util.List;

public interface MachineryRepository {

    Long save(Machinery machinery);

    List<MachineryView> findAll();

    List<MachineryBasicInfoView> findMachineriesWithIdleStatus();

}
