package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.Call;

import java.util.List;

public interface ICallRepository {
    Call findById(Long id);
    List<Call> findAll();
    void save(Call call);
    void delete(Long id);
}
