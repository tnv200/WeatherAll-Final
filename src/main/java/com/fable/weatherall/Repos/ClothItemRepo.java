package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fable.weatherall.ClothEntites.ClothingItem;

public interface ClothItemRepo extends JpaRepository<ClothingItem, Integer>{

	 List<ClothingItem> findAll(); 
	 
	 void deleteByClothingItemId(int a);
	 
	 ClothingItem findByItemName(String item);
	 
	 ClothingItem findByClothingItemId(int id);
	 
	
}
