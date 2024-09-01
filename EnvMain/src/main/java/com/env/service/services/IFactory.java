package com.env.service.services;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IFactory <T,K> {
    T factory(K decisionMaking);
}
