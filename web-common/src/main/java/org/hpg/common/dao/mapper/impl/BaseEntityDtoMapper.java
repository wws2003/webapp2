/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.model.mapping.EntityDtoMap;
import org.hpg.libcommon.CH;
import org.hpg.libcommon.CV;
import org.hpg.libcommon.Tuple;
import org.hpg.libcommon.Tuple2;

/**
 * Base implementation for mapper, based on the mapping annotation
 *
 * @author wws2003
 * @param <EC>
 * @param <DC>
 */
public abstract class BaseEntityDtoMapper<EC, DC> implements IEntityDtoMapper<EC, DC> {

    @Override
    public final DC getDtoFromEntity(EC entity) {
        try {
            return getDtoFromEntity(entity, this.getClass().getAnnotation(EntityDtoMap.class));
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new MendelRuntimeException(ex);
        }
    }

    @Override
    public final EC getEntityFromDto(DC dto) {
        try {
            return getEntityFromDto(dto, this.getClass().getAnnotation(EntityDtoMap.class));
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new MendelRuntimeException(ex);
        }
    }

    /**
     * Processes other than getter and setter
     *
     * @param entity
     * @param dto
     */
    protected void finalizeDtoFromEntity(EC entity, DC dto) {
        // Do nothing for default
    }

    /**
     * Processes other than getter and setter
     *
     * @param dto
     * @param entity
     */
    protected void finalizeEntityFromDto(DC dto, EC entity) {
        // Do nothing for default
    }

    /**
     * Get DTO from entity
     *
     * @param entity
     * @param mappingAnn
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private DC getDtoFromEntity(EC entity, EntityDtoMap mappingAnn) throws InstantiationException, IllegalAccessException {
        // Parse method map
        List<Tuple2<Method, Method>> getterSetterTuples = parseGetterSetterMethodMapping(
                mappingAnn.entityToDtoMappings(),
                true,
                mappingAnn.entityClass(),
                mappingAnn.dtoClass()
        );

        // New instance
        final DC dto = (DC) (mappingAnn.dtoClass().newInstance());

        // Set values
        setAttributesToInstance(getterSetterTuples, dto, entity);

        // Other process
        finalizeDtoFromEntity(entity, dto);

        return dto;
    }

    /**
     * Create entity from DTO
     *
     * @param dto
     * @param mappingAnn
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private EC getEntityFromDto(DC dto, EntityDtoMap mappingAnn) throws InstantiationException, IllegalAccessException {
        // Parse method map
        boolean isInGetterSetterOrder = true;
        String[] methodMappings = mappingAnn.dtoToEntityMappings();

        if (CH.isEmpty(methodMappings)) {
            methodMappings = mappingAnn.entityToDtoMappings();
            isInGetterSetterOrder = false;
        }
        List<Tuple2<Method, Method>> getterSetterTuples = parseGetterSetterMethodMapping(
                methodMappings,
                isInGetterSetterOrder,
                mappingAnn.dtoClass(),
                mappingAnn.entityClass()
        );

        // New instance
        final EC entity = (EC) (mappingAnn.entityClass().newInstance());

        // Set values
        setAttributesToInstance(getterSetterTuples, entity, dto);

        // Other process
        finalizeEntityFromDto(dto, entity);

        return entity;
    }

    /**
     * Parse method names for mapping to map
     *
     * @param methodMappings
     * @return
     */
    private List<Tuple2<Method, Method>> parseGetterSetterMethodMapping(String[] methodMappings,
            boolean isInGetterSetterOrder,
            Class<?> classToGet,
            Class<?> classToSet) {

        return Stream.of(methodMappings)
                .map(CV::csvToList)
                .map(names -> {
                    if (CH.isEmpty(names) || names.size() > 2) {
                        return null;
                    }
                    String name1 = names.get(0);
                    String name2 = names.size() == 2 ? names.get(1) : names.get(0);
                    return isInGetterSetterOrder ? Tuple.newTuple(name1, name2) : Tuple.newTuple(name2, name1);
                })
                .filter(tuple -> tuple != null)
                .map(tuple -> {
                    try {
                        PropertyDescriptor propertyDescriptorForGetter = new PropertyDescriptor(tuple.getItem1(), classToGet);
                        PropertyDescriptor propertyDescriptorForSetter = new PropertyDescriptor(tuple.getItem2(), classToSet);
                        return Tuple.newTuple(propertyDescriptorForGetter.getReadMethod(),
                                propertyDescriptorForSetter.getWriteMethod());
                    } catch (IntrospectionException ex) {
                        throw new MendelRuntimeException(ex);
                    }
                })
                .filter(tuple -> tuple != null)
                .collect(Collectors.toList());
    }

    /**
     * Set attributes from instanceToGet to instanceToSet
     *
     * @param <T1>
     * @param <T2>
     * @param getterSetterTuples
     * @param instanceToSet
     * @param instanceToGet
     */
    private <T1, T2> void setAttributesToInstance(List<Tuple2<Method, Method>> getterSetterTuples, T1 instanceToSet, T2 instanceToGet) {
        // Set values
        getterSetterTuples.stream()
                .forEach(tuple -> {
                    Method getter = tuple.getItem1();
                    Method setter = tuple.getItem2();
                    try {
                        setter.invoke(instanceToSet, getter.invoke(instanceToGet));
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        // Just throw
                        throw new MendelRuntimeException(ex);
                    }
                });
    }
}
