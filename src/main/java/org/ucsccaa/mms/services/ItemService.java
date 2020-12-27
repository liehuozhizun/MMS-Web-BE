package org.ucsccaa.mms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.Item;
import org.ucsccaa.mms.repositories.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repo;

    public Long addItem(Item item) {
        if (item == null) {
            throw new RuntimeException("ITEM CANNOT BE NULL");
        }
        return repo.save(item).getId();
    }

    public Item updateItem(Item item) {
        if (item == null) {
            throw new RuntimeException("ITEM CANNOT BE NULL");
        }
        return repo.existsById(item.getId()) ? repo.save(item) : null;
    }

    public Item getItem(Long id) {
        if (id == null) 
            throw new RuntimeException("ID CANNOT BE NULL");
        Optional<Item> item = repo.findById(id);
        return item.isPresent() ? item.get() : null;
    }

    public List<Item> getAll() {
        return repo.findAll();
    }

    public Boolean deleteItemById(Long id) {
        if (id == null) 
            throw new RuntimeException("ID CANNOT BE NULL");
        Optional<Item> item = repo.findById(id);
        if (item.isPresent()) {
            repo.delete(item.get());
            return true;
        } else {
            return false;
        }
    }
}
