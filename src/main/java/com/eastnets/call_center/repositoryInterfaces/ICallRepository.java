package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.Call;
import java.util.List;

public interface ICallRepository {
    void save(Call call);
    Call findById(int callID);
    List<Call> findAll();
    void update(Call call);
}