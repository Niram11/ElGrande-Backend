package com.codecool.gastro.service;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DishCategoryService {
    private final DishCategoryMapper dishCategoryMapper;
    private final DishCategoryRepository dishCategoryRepository;


    public DishCategoryService(DishCategoryMapper dishCategoryMapper,
                               DishCategoryRepository dishCategoryRepository) {
        this.dishCategoryMapper = dishCategoryMapper;
        this.dishCategoryRepository = dishCategoryRepository;
    }


    public List<DishCategoryDto> getAllDishCategories() {
        return dishCategoryRepository.findAll()
                .stream().map(dishCategoryMapper::toDto).toList();
    }

    public DishCategoryDto getDishCategoryById(UUID id) {
        return dishCategoryRepository.findBy(id)
                .map(dishCategoryMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, DishCategory.class));
    }

    public DishCategoryDto saveDishCategory(NewDishCategoryDto newDishCategoryDto) {
        DishCategory savedDishCategory = dishCategoryRepository.save(dishCategoryMapper.
                dtoToDishCategory(newDishCategoryDto));
        return dishCategoryMapper.toDto(parseToLowerCase(savedDishCategory));
    }

    public DishCategoryDto updateDishCategory(UUID id, NewDishCategoryDto newDishCategoryDto) {
        DishCategory updatedDishCategory = dishCategoryRepository.save(dishCategoryMapper.
                dtoToDishCategory(newDishCategoryDto, id));
        return dishCategoryMapper.toDto(parseToLowerCase(updatedDishCategory));
    }

    public void deleteDishCategory(UUID id) {
        dishCategoryRepository.delete(dishCategoryMapper.dtoToDishCategory(id));
    }

    private DishCategory parseToLowerCase(DishCategory dishCategory) {
        dishCategory.setCategory(dishCategory.getCategory().toLowerCase());
        return dishCategory;
    }
}
